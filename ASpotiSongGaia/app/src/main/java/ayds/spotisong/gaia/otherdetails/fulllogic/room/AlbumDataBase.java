package ayds.spotisong.gaia.otherdetails.fulllogic.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {AlbumEntity.class}, version = 1)
public abstract class AlbumDataBase extends RoomDatabase {
  public abstract AlbumDao albumDao();
}
