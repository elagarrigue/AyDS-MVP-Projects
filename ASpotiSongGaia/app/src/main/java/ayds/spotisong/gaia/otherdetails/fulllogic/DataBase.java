package ayds.spotisong.gaia.otherdetails.fulllogic;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import java.util.List;

import ayds.spotisong.gaia.otherdetails.fulllogic.room.*;

public class DataBase {

  private static AlbumDataBase db;

  public static void createNewDatabase(Context context) {
    db = Room.databaseBuilder(context,
                              AlbumDataBase.class, "extra_info.db").build();
  }

  public static void testDB() {

    List<AlbumEntity> artists = db.albumDao().getAll();

    for (AlbumEntity artist :
        artists) {
      Log.e("**", "id =" + artist.getId());
      Log.e("**", "song =" + artist.getSong());
      Log.e("**", "text =" + artist.getText());
      Log.e("**", "source =" + artist.getSource());

    }
  }

  public static void saveAlbumInfo(String song, String text, String image) {
    AlbumEntity albumEntity =  new AlbumEntity();
    albumEntity.setSong(song);
    albumEntity.setText(text);
    albumEntity.setImage_url(image);
    albumEntity.setSource(1);
    db.albumDao().insert(albumEntity);
  }

  public static String getText(String artist) {

    AlbumEntity albumEntity = db.albumDao().findByName(artist);

    if (albumEntity != null) {
      return albumEntity.getText();
    }
    return null;
  }


  public static String getImageUrl(String artist) {

    AlbumEntity albumEntity = db.albumDao().findByName(artist);

    if (albumEntity != null) {
      return albumEntity.getImage_url();
    }
    return null;
  }
}
