package ayds.spotisong.gaia.song.presenter;

import ayds.spotisong.gaia.song.model.SearchSongModel;
import ayds.spotisong.gaia.song.model.Song;
import ayds.spotisong.gaia.song.view.SearchSongView;

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
    view.openExternalUrl(model.lastSongFound().getUrl());
  }

  @Override
  public void onOtherInfo() {
    view.goToOtherDetails(model.lastSongFound());
  }

  @Override
  public void onSongFound(Song song) {
    view.enableActionButtons();
    view.setSongDescription(model.getSongDescription(song));
  }

  @Override public void onDestroy() {
    view = null;
  }
}
