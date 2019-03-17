package ayds.spotisong.terminus.otherdetails.fulllogic;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import java.util.List;

import ayds.spotisong.terminus.otherdetails.fulllogic.room.*;

public class DataBase {

  private static LyricsDataBase db;

  public static void createNewDatabase(Context context) {
    db = Room.databaseBuilder(context,
                              LyricsDataBase.class, "extra_info.db").build();
  }

  public static void testDB() {

    List<LyricsEntity> artists = db.albumDao().getAll();

    for (LyricsEntity artist :
        artists) {
      Log.e("**", "id =" + artist.getId());
      Log.e("**", "song =" + artist.getSong());
      Log.e("**", "text =" + artist.getText());
      Log.e("**", "source =" + artist.getSource());

    }
  }

  public static void saveLyrics(String song, String text, String image) {
    LyricsEntity lyricsEntity =  new LyricsEntity();
    lyricsEntity.setSong(song);
    lyricsEntity.setText(text);
    lyricsEntity.setImage_url(image);
    lyricsEntity.setSource(1);
    db.albumDao().insert(lyricsEntity);
  }

  public static String getLyrics(String artist) {

    LyricsEntity lyricsEntity = db.albumDao().findByName(artist);

    if (lyricsEntity != null) {
      return lyricsEntity.getText();
    }
    return null;
  }


  public static String getImageUrl(String artist) {

    LyricsEntity lyricsEntity = db.albumDao().findByName(artist);

    if (lyricsEntity != null) {
      return lyricsEntity.getImage_url();
    }
    return null;
  }
}
