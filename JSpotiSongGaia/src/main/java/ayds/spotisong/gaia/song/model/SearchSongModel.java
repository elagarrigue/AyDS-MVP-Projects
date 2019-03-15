package ayds.spotisong.gaia.song.model;

public interface SearchSongModel {

  void searchSong(String query);

  Song lastSongFound();

  String getSongDescription(Song song);
}
