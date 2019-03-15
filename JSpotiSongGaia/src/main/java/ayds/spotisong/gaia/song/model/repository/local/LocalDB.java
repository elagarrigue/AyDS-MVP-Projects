package ayds.spotisong.gaia.song.model.repository.local;

import ayds.spotisong.gaia.song.model.Song;

public interface LocalDB {

  void saveSong(Song song);

  Song getSong(String name);
}
