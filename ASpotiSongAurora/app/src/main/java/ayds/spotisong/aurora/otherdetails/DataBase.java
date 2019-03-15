package ayds.spotisong.aurora.otherdetails;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import java.util.List;

import ayds.spotisong.aurora.otherdetails.room.*;

public class DataBase {

  private static ArtistDataBase db;

  public static void createNewDatabase(Context context) {
    db = Room.databaseBuilder(context,
                              ArtistDataBase.class, "extra_info.db").build();
  }

  public static void testDB() {

    List<ArtistEntity> artists = db.artistDao().getAll();

    for (ArtistEntity artist :
        artists) {
      Log.e("**", "id =" + artist.getId());
      Log.e("**", "artist =" + artist.getArtist());
      Log.e("**", "bio =" + artist.getBio());
      Log.e("**", "source =" + artist.getSource());

    }
  }

  public static void saveArtistInfo(String name, String bio, String image) {
    ArtistEntity artistEntity =  new ArtistEntity();
    artistEntity.setArtist(name);
    artistEntity.setBio(bio);
    artistEntity.setImage_url(image);
    artistEntity.setSource(1);
    db.artistDao().insert(artistEntity);
  }

  public static String getBio(String artist) {

    ArtistEntity artistEntity = db.artistDao().findByName(artist);

    if (artistEntity != null) {
      return artistEntity.getBio();
    }
    return null;
  }


  public static String getImageUrl(String artist) {

    ArtistEntity artistEntity = db.artistDao().findByName(artist);

    if (artistEntity != null) {
      return artistEntity.getImage_url();
    }
    return null;
  }
}
