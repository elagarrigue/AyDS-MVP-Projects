package ayds.spotisong.terminus.song.model.repository.local;

import ayds.spotisong.terminus.song.model.Song;

public interface LocalDB {

  void saveSong(Song song);

  Song getSong(String name);
}
