package ayds.spotisong.terminus.song.model;

import ayds.spotisong.terminus.song.model.repository.RepositoryModule;
import ayds.spotisong.terminus.song.presenter.SearchSongPresenterModule;

public class SearchSongModelModule {

  private static SearchSongModelModule instance;

  private final SearchSongModel searchSongModel;

  private SearchSongModelModule() {

    searchSongModel = new SearchSongModelImp(
            RepositoryModule.getInstance().getSongRepository(),
            SearchSongPresenterModule.getInstance().getSearchSongPresenter(),
            new SongDescriptionFormatterImp());
  }

  public static SearchSongModelModule getInstance() {
    if (instance == null) {
      instance = new SearchSongModelModule();
    }
    return instance;
  }

  public SearchSongModel getSearchSongModel() {
    return searchSongModel;
  }
}
