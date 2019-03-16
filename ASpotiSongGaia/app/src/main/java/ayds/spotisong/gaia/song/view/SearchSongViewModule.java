package ayds.spotisong.gaia.song.view;

import android.content.Context;

import ayds.spotisong.gaia.song.presenter.SearchSongPresenter;
import ayds.spotisong.gaia.song.presenter.SearchSongPresenterModule;

public class SearchSongViewModule {

  private static SearchSongViewModule instance;

  private SearchSongViewModule() { }

  public static SearchSongViewModule getInstance() {
    if (instance == null) {
      instance = new SearchSongViewModule();
    }
    return instance;
  }

  void setSearchSongView(SearchSongView searchSongView, Context context) {
    SearchSongPresenter presenter = SearchSongPresenterModule.getInstance()
        .getSearchSongPresenter(searchSongView, context);
    searchSongView.setSearchSongPresenter(presenter);
  }
}
