package ayds.spotisong.aurora.otherdetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import ayds.spotisong.aurora.R;
import ayds.spotisong.aurora.song.model.Song;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class OtherInfoActivity extends AppCompatActivity {

  public static final String EXTRA_NAME = "extra_name";
  public static final String EXTRA_ARTIST = "extra_artist";
  public static final String EXTRA_ALBUM = "extra_album";
  public static final String EXTRA_URL = "extra_url";

  private TextView textPane1;
  private ImageView imageView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    init();

    setContentView(R.layout.activity_other_info);

    textPane1 = findViewById(R.id.textPane1);
    imageView = findViewById(R.id.image);

    String name = getIntent().getStringExtra(EXTRA_NAME);
    String artist = getIntent().getStringExtra(EXTRA_ARTIST);
    String album = getIntent().getStringExtra(EXTRA_ALBUM);
    String url = getIntent().getStringExtra(EXTRA_URL);
    Song song = new Song(name, artist, album, url);

    getArtistBio(song);
  }

  public void getArtistBio(Song song) {

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://ws.audioscrobbler.com/2.0/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build();

    LastFMAPI lastFMAPI = retrofit.create(LastFMAPI.class);

    new Thread(new Runnable() {
      public void run() {

        String text = DataBase.getBio(song.getArtistName());

        String path = DataBase.getImageUrl(song.getArtistName());

        if (text != null && path != null) { // exists in db

          text = "[*]" + text;
        } else { // get from service
          Response<String> callResponse;
          try {
            callResponse = lastFMAPI.getArtistInfo(song.getArtistName()).execute();

            Log.e("**","XML " + callResponse.body());

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(callResponse.body())));

            NodeList nodes = doc.getDocumentElement().getElementsByTagName("content");

            Node extract = nodes.item(0);

            NodeList images = doc.getDocumentElement().getElementsByTagName("image");
            for (int i = 0; i < images.getLength(); i++) {
              Node image = images.item(i);
              NamedNodeMap atts = image.getAttributes();
              Node size = atts.getNamedItem("size");

              if (size.getTextContent().equals("extralarge")) {
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

        final String textToSet = text;
        String finalPath = path;
        textPane1.post(new Runnable() {
          public void run() {
            textPane1.setText(Html.fromHtml(textToSet));
            Picasso.get().load(finalPath).into(imageView);
          }
        });
      }
    }).start();

  }

  private void init() {

    new Thread(new Runnable() {
      @Override public void run() {
        DataBase.createNewDatabase(getApplicationContext());
        DataBase.saveArtistInfo("test", "sarasa", "");

        Log.e("**", "" + DataBase.getBio("test"));
        Log.e("**", "" + DataBase.getBio("nada"));
      }
    }).start();

  }

  public static String textToHtml(String text, String term) {

    StringBuilder builder = new StringBuilder();

    String textWithBold = text.replaceAll("(?i)" + term, "<b>" + term + "</b>");

    builder.append(textWithBold);

    return builder.toString();
  }
}
