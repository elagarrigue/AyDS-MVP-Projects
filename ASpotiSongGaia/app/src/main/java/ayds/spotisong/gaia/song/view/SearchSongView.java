package ayds.spotisong.gaia.song.view;

import ayds.spotisong.gaia.song.model.Song;
import ayds.spotisong.gaia.song.presenter.SearchSongPresenter;

public interface SearchSongView {

  String getQuery();

  void setSongDescription(String description);

  void enableActionButtons();

  void disableActionButtons();

  void setSearchSongPresenter(SearchSongPresenter presenter);

  void openExternalUrl(String url);

  void goToOtherDetails(Song song);
}
