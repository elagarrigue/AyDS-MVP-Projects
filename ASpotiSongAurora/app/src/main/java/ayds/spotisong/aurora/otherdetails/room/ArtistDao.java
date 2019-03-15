package ayds.spotisong.aurora.otherdetails.room;

import android.arch.persistence.room.*;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ArtistDao {

  @Query("SELECT * FROM ArtistEntity")
  List<ArtistEntity> getAll();

  @Query("SELECT * FROM ArtistEntity WHERE artist LIKE :artist LIMIT 1")
  ArtistEntity findByName(String artist);

  @Insert(onConflict = REPLACE)
  void insert(ArtistEntity artist);
}
