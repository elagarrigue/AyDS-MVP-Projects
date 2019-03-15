package ayds.spotisong.aurora.otherdetails.fulllogic;

import ayds.spotisong.aurora.song.model.Song;

import java.awt.image.BufferedImage;
import java.io.StringReader;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
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
            .baseUrl("http://ws.audioscrobbler.com/2.0/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    LastFMAPI lastFMAPI = retrofit.create(LastFMAPI.class);

    textPane1.setContentType("text/html");

    new Thread(new Runnable() {
      @Override
      public void run() {

        String text = DataBase.getBio(song.getArtistName());

        String path = DataBase.getImageUrl(song.getArtistName());

        if (text != null && path != null) { // exists in db

          text = "[*]" + text;
        } else { // get from service
          Response<String> callResponse;
          try {
            callResponse = lastFMAPI.getArtistInfo(song.getArtistName()).execute();

            System.out.println("XML " + callResponse.body());


            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(callResponse.body())));


            NodeList nodes = doc.getDocumentElement().getElementsByTagName("content");

            Node extract = nodes.item(0);

            NodeList images = doc.getDocumentElement().getElementsByTagName("image");
            for (int i = 0; i < images.getLength(); i++) {
              Node image = images.item(i);
              NamedNodeMap atts = image.getAttributes();
              Node size =  atts.getNamedItem("size");

              if(size.getTextContent().equals("large")) {
                path = image.getTextContent();
                break;
              }
            }

            if (extract == null) {
              text = "No Results";
            } else {
              text = extract.getTextContent().replace("\\n", "\n");
              text = textToHtml(text, song.getArtistName());

              // save to DB  <o/

              DataBase.saveArtistInfo(song.getArtistName(), text, path);
            }


          } catch (Exception e1) {
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
          System.out.println("Load image into frame...");
          JLabel label = new JLabel(new ImageIcon(image));
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
    DataBase.saveArtistInfo("test", "sarasa", "");


    System.out.println(DataBase.getBio("test"));
    System.out.println(DataBase.getBio("nada"));


    win.getArtistBio(song);
  }

  public static String textToHtml(String text, String term) {

    StringBuilder builder = new StringBuilder();

    builder.append("<font face=\"arial\">");

    String textWithBold = text
            .replace("'", "`")
            .replaceAll("(?i)" + term, "<b>" + term.toUpperCase() + "</b>");

    builder.append(textWithBold);

    builder.append("</font>");

    return builder.toString();
  }

}
