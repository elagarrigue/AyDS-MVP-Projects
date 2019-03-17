package ayds.spotisong.terminus.song.presenter;

import ayds.spotisong.terminus.song.model.Song;

public interface SearchSongPresenter {

  void onSongSearch();

  void onOpenUrl();

  void onOtherInfo();

  void onSongFound(Song song);

  void onError(Exception e);

  void onDestroy();
}
