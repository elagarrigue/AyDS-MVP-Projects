package ayds.spotisong.aurora.otherdetails.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {ArtistEntity.class}, version = 1)
public abstract class ArtistDataBase extends RoomDatabase {
  public abstract ArtistDao artistDao();
}
