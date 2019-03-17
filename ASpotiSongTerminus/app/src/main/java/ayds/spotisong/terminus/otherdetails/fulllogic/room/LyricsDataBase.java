package ayds.spotisong.terminus.otherdetails.fulllogic.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {LyricsEntity.class}, version = 1)
public abstract class LyricsDataBase extends RoomDatabase {
  public abstract LyricsDao albumDao();
}
