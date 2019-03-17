package ayds.spotisong.terminus.song.view;

import android.content.Context;

import ayds.spotisong.terminus.song.presenter.SearchSongPresenter;
import ayds.spotisong.terminus.song.presenter.SearchSongPresenterModule;

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
