package ayds.spotisong.terminus.otherdetails.fulllogic;

import ayds.spotisong.terminus.song.model.Song;
import com.google.gson.Gson;
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
  private JPanel contentPane;
  private JTextPane textPane1;
  private JPanel imagePanel;

  public void getArtistBio(Song song) {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://orion.apiseeds.com/api/music/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    LyricsAPI lyricsAPI = retrofit.create(LyricsAPI.class);


    textPane1.setContentType("text/html");

    new Thread(new Runnable() {
      @Override
      public void run() {

        String text = DataBase.getLyrics(song.getSongName());

        String path = DataBase.getImageUrl(song.getSongName());

        if (text != null && path != null) { // exists in db

          text = "[*]" + text;
        } else { // get from service
          Response<String> callResponse;
          try {
            callResponse = lyricsAPI.getLyrics(song.getArtistName(), song.getSongName()).execute();

            System.out.println("JSON " + callResponse.body());

            Gson gson = new Gson();
            JsonObject jobj = gson.fromJson(callResponse.body(), JsonObject.class);
            JsonObject result = jobj.get("result").getAsJsonObject();
            JsonObject track = result.get("track").getAsJsonObject();
            JsonElement extract = track.get("text");



            if (extract == null) {
              text = "No Results";
            } else {
              text =formatText( extract.getAsString());



              path = "https://source.unsplash.com/random/300x300/?" + song.getArtistName().replace(" ", "%20") +",music";

              System.out.println("image " + path);

              // save to DB  <o/

              DataBase.saveLyrics(song.getSongName(), text, path);
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
    DataBase.saveLyrics("test", "sarasa", "");


    System.out.println(DataBase.getLyrics("test"));
    System.out.println(DataBase.getLyrics("nada"));


    win.getArtistBio(song);
  }

  public static String formatText(String text) {

    StringBuilder builder = new StringBuilder();

    builder.append("<font face=\"arial\">");

    builder.append(text.replace("\n", "<br>"));

    builder.append("</font>");

    return builder.toString();
  }


}
