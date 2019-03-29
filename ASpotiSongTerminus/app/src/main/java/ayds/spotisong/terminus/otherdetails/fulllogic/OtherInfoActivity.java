package ayds.spotisong.terminus.otherdetails.fulllogic;

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

import ayds.spotisong.terminus.R;
import ayds.spotisong.terminus.song.model.Song;
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
        .baseUrl("https://orion.apiseeds.com/api/music/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build();

    LyricsAPI lyricsAPI = retrofit.create(LyricsAPI.class);

    new Thread(new Runnable() {
      public void run() {

        String text = DataBase.getLyrics(song.getSongName());

        String path = DataBase.getImageUrl(song.getSongName());

        if (text != null && path != null) { // exists in db

          text = "[*]" + text;
        } else
        {
          Response<String> callResponse;
        try {


          callResponse = lyricsAPI.getLyrics(song.getArtistName(), song.getSongName()).execute();

          Log.e("**","JSON " + callResponse.body());

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
        DataBase.saveLyrics("test", "sarasa", "");

        Log.e("**", "" + DataBase.getLyrics("test"));
        Log.e("**", "" + DataBase.getLyrics("nada"));
      }
    }).start();

  }

  public static String formatText(String text) {

    StringBuilder builder = new StringBuilder();

    builder.append("<font face=\"arial\">");

    builder.append(text.replace("\n", "<br>"));

    builder.append("</font>");

    return builder.toString();
  }
}
