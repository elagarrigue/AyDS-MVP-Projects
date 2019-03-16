package ayds.spotisong.gaia.otherdetails.fulllogic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import ayds.spotisong.gaia.R;
import ayds.spotisong.gaia.song.model.Song;
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
        .baseUrl("https://api.discogs.com/database/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build();

    DiscoGSAPI discoGSAPI = retrofit.create(DiscoGSAPI.class);

    new Thread(new Runnable() {
      public void run() {

        String text = DataBase.getText(song.getSongName());

        String path = DataBase.getImageUrl(song.getSongName());

        if (text != null && path != null) { // exists in db

          text = "[*]" + text;
        } else
        {
          Response<String> callResponse;
        try {


          callResponse = discoGSAPI.getAlbumInfo(song.getAlbumName(), song.getArtistName()).execute();

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


            text = formatText(text, yearE.getAsString());

            path = image.getAsString();

            // save to DB  <o/

            DataBase.saveAlbumInfo(song.getSongName(), text, path);
          }


        } catch (IOException e1) {
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
        DataBase.saveAlbumInfo("test", "sarasa", "");

        Log.e("**", "" + DataBase.getText("test"));
        Log.e("**", "" + DataBase.getText("nada"));
      }
    }).start();

  }

  public static String formatText(String title, String year) {

    StringBuilder builder = new StringBuilder();

    builder.append("<font face=\"arial\">");

    String textWithBold = "<b>" + title.toUpperCase() + "</b>";

    builder.append(textWithBold);
    builder.append(", year: " + year);

    builder.append("</font>");

    return builder.toString();
  }
}
