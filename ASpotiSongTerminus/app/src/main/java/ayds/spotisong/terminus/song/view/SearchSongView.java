package ayds.spotisong.terminus.song.view;

import ayds.spotisong.terminus.song.model.Song;
import ayds.spotisong.terminus.song.presenter.SearchSongPresenter;

public interface SearchSongView {

  String getQuery();

  void setSongDescription(String description);

  void enableActionButtons();

  void disableActionButtons();

  void setSearchSongPresenter(SearchSongPresenter presenter);

  void openExternalUrl(String url);

  void goToOtherDetails(Song song);
}
