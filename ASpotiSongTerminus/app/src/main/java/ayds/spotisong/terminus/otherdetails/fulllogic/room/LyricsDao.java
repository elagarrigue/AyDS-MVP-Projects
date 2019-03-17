package ayds.spotisong.terminus.otherdetails.fulllogic.room;

import android.arch.persistence.room.*;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface LyricsDao {

  @Query("SELECT * FROM LyricsEntity")
  List<LyricsEntity> getAll();

  @Query("SELECT * FROM LyricsEntity WHERE song LIKE :song LIMIT 1")
  LyricsEntity findByName(String song);

  @Insert(onConflict = REPLACE)
  void insert(LyricsEntity album);
}
