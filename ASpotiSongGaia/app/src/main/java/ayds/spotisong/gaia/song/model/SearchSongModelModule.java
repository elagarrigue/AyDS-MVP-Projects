package ayds.spotisong.gaia.song.model;

import android.content.Context;

import ayds.spotisong.gaia.song.model.repository.RepositoryModule;
import ayds.spotisong.gaia.song.model.repository.SongRepository;
import ayds.spotisong.gaia.song.presenter.SearchSongPresenter;

public class SearchSongModelModule {

  private static SearchSongModelModule instance;

  private SearchSongModelModule() { }

  public static SearchSongModelModule getInstance() {
    if (instance == null) {
      instance = new SearchSongModelModule();
    }
    return instance;
  }

  public SearchSongModel getSearchSongModel(SearchSongPresenter presenter, Context context) {
    SongDescriptionFormatter songDescriptionFormatter = new SongDescriptionFormatterImp();
    SongRepository repository = RepositoryModule.getInstance().getSongRepository(context);

    return new SearchSongModelImp(
        repository,
        presenter,
        songDescriptionFormatter);
  }
}
