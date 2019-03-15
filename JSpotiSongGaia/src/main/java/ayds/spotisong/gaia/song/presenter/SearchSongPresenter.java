package ayds.spotisong.gaia.song.presenter;

import ayds.spotisong.gaia.song.model.Song;

public interface SearchSongPresenter {

  void onSongSearch();

  void onOpenUrl();

  void onOtherInfo();

  void onSongFound(Song song);

  void onError(Exception e);
}
