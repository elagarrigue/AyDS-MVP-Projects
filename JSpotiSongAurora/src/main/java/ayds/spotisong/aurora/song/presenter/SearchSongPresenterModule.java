package ayds.spotisong.aurora.song.presenter;

import ayds.spotisong.aurora.song.model.SearchSongModelModule;
import ayds.spotisong.aurora.song.view.SearchSongViewModule;

public class SearchSongPresenterModule {

  private static SearchSongPresenterModule instance;

  private SearchSongPresenterImp searchSongPresenter;

  private SearchSongPresenterModule() {
  }

  public static SearchSongPresenterModule getInstance() {
    if (instance == null) {
      instance = new SearchSongPresenterModule();
    }
    return instance;
  }

  public SearchSongPresenter getSearchSongPresenter() {
    return searchSongPresenter;
  }

  public void startApp() {
    searchSongPresenter = new SearchSongPresenterImp();

    searchSongPresenter.setView(SearchSongViewModule.getInstance().getSearchSongView());
    searchSongPresenter.setModel(SearchSongModelModule.getInstance().getSearchSongModel());

    SearchSongViewModule.getInstance().openView();
  }
}
