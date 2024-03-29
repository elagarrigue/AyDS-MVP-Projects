package ayds.spotisong.terminus.song.model.repository;

import ayds.spotisong.terminus.song.model.Song;

public interface SongRepository {

  void searchSong(String query, SearchSongListener listener, SearchSongErrorListener errorListener);

  Song lastSongFound();

  interface SearchSongListener {
    void onSongFound(Song song);
  }

  interface SearchSongErrorListener {
    void onError(Exception e);
  }
}
