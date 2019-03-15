package ayds.spotisong.terminus.song.presenter;

import ayds.spotisong.terminus.otherdetails.fulllogic.OtherInfoWindow;
import ayds.spotisong.terminus.song.model.SearchSongModel;
import ayds.spotisong.terminus.song.model.Song;
import ayds.spotisong.terminus.song.view.SearchSongView;

class SearchSongPresenterImp implements SearchSongPresenter {

  private SearchSongView view;
  private SearchSongModel model;

  SearchSongPresenterImp() {
  }

  void setView(SearchSongView view) {
    this.view = view;
  }

  void setModel(SearchSongModel model) {
    this.model = model;
  }

  @Override
  public void onSongSearch() {
    view.disableActionButtons();
    view.setSongDescription("Loading...");
    model.searchSong(view.getQuery());
  }

  @Override
  public void onError(Exception e) {
    view.disableActionButtons();
    view.setSongDescription("Error: " + e.getMessage());
  }

  @Override
  public void onOpenUrl() {
    try {
      UrlUtils.openExternalUrl(model.lastSongFound().getUrl());
    } catch (Exception e) {
      onError(new Exception("Could not open url..."));
    }
  }

  @Override
  public void onOtherInfo() {
    OtherInfoWindow.open(model.lastSongFound());
  }

  @Override
  public void onSongFound(Song song) {
    view.enableActionButtons();
    view.setSongDescription(model.getSongDescription(song));
  }
}
