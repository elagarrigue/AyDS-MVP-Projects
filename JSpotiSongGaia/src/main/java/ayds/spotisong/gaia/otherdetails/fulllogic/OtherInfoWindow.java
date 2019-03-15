package ayds.spotisong.gaia.otherdetails.fulllogic;

import ayds.spotisong.gaia.song.model.Song;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class OtherInfoWindow {
  private JTextField textField1;
  private JPanel contentPane;
  private JTextPane textPane1;
  private JPanel imagePanel;

  public void getArtistBio(Song song) {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.discogs.com/database/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    DiscoGSAPI discoGSAPI = retrofit.create(DiscoGSAPI.class);


    textPane1.setContentType("text/html");

    new Thread(new Runnable() {
      @Override
      public void run() {

        String text = DataBase.getText(song.getSongName());

        String path = DataBase.getImageUrl(song.getSongName());

        if (text != null && path != null) { // exists in db

          text = "[*]" + text;
        } else { // get from service
          Response<String> callResponse;
          try {


            callResponse = discoGSAPI.getArtistInfo(song.getAlbumName(), song.getArtistName()).execute();

            System.out.println("JSON " + callResponse.body());

            Gson gson = new Gson();
            JsonObject jobj = gson.fromJson(callResponse.body(), JsonObject.class);
            JsonArray results = jobj.get("results").getAsJsonArray();
            JsonObject first = results.get(0).getAsJsonObject();

            JsonElement extract = first.get("title");
            JsonElement yearE = first.get("year");

            JsonElement image = first.get("cover_image");


            if (extract == null && yearE == null && image == null) {
              text = "No Results";
            } else {
              text = extract.getAsString();


              text = formateText(text, yearE.getAsString());

              path = image.getAsString();

              // save to DB  <o/

              DataBase.saveAlbumInfo(song.getSongName(), text, path);
            }


          } catch (IOException e1) {
            e1.printStackTrace();
          }
        }

        textPane1.setText(text);


        // set image
        try {
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }

        try {
          System.out.println("Get Image from " + path);
          URL url = new URL(path);
          BufferedImage image = ImageIO.read(url);
          Image dimg = image.getScaledInstance(300, image.getHeight() * 300 /image.getWidth(),
                  Image.SCALE_SMOOTH);

          System.out.println("Load image into frame...");
          JLabel label = new JLabel(new ImageIcon(dimg));
          imagePanel.add(label);

         // Refresh panel
          contentPane.validate();
          contentPane.repaint();

        } catch (Exception exp) {
          exp.printStackTrace();
        }

      }
    }).start();
  }

  public static void open(Song song) {

    OtherInfoWindow win = new OtherInfoWindow();

    JFrame frame = new JFrame("Artist Info");
    frame.setContentPane(win.contentPane);
    frame.pack();
    frame.setVisible(true);

    DataBase.createNewDatabase();
    DataBase.saveAlbumInfo("test", "sarasa", "");


    System.out.println(DataBase.getText("test"));
    System.out.println(DataBase.getText("nada"));


    win.getArtistBio(song);
  }

  public static String formateText(String title, String year) {

    StringBuilder builder = new StringBuilder();

    builder.append("<font face=\"arial\">");

    String textWithBold = "<b>" + title.toUpperCase() + "</b>";

    builder.append(textWithBold);
    builder.append(", year: " + year);

    builder.append("</font>");

    return builder.toString();
  }

}
