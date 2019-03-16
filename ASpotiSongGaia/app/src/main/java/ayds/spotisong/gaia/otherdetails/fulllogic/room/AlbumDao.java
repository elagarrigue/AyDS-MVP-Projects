package ayds.spotisong.gaia.otherdetails.fulllogic.room;

import android.arch.persistence.room.*;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface AlbumDao {

  @Query("SELECT * FROM AlbumEntity")
  List<AlbumEntity> getAll();

  @Query("SELECT * FROM AlbumEntity WHERE song LIKE :song LIMIT 1")
  AlbumEntity findByName(String song);

  @Insert(onConflict = REPLACE)
  void insert(AlbumEntity album);
}
