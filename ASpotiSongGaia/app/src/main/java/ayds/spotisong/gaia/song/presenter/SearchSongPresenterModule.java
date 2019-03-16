package ayds.spotisong.gaia.song.presenter;

import android.content.Context;

import ayds.spotisong.gaia.song.model.SearchSongModel;
import ayds.spotisong.gaia.song.model.SearchSongModelModule;
import ayds.spotisong.gaia.song.view.SearchSongView;
import ayds.spotisong.gaia.song.view.SearchSongViewModule;

public class SearchSongPresenterModule {

  private static SearchSongPresenterModule instance;

  private SearchSongPresenterModule() { }

  public static SearchSongPresenterModule getInstance() {
    if (instance == null) {
      instance = new SearchSongPresenterModule();
    }
    return instance;
  }

  public SearchSongPresenter getSearchSongPresenter(SearchSongView view, Context context) {
    SearchSongPresenterImp presenter = new SearchSongPresenterImp();
    SearchSongModel model = SearchSongModelModule.getInstance().getSearchSongModel(presenter, context);
    presenter.setView(view);
    presenter.setModel(model);
    return presenter;
  }
}
