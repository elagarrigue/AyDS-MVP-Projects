package ayds.spotisong.gaia.song.model.repository;

import ayds.spotisong.gaia.song.model.repository.external.ExternalModule;
import ayds.spotisong.gaia.song.model.repository.local.LocalModule;

public class RepositoryModule {

  private static RepositoryModule instance;

  private final SongRepository songRepository;

  private RepositoryModule() {
    songRepository = new SongRepositoryImp(
            ExternalModule.getInstance().getExternalService(),
            LocalModule.getInstance().getLocalDB()
    );
  }

  public static RepositoryModule getInstance() {
    if (instance == null) {
      instance = new RepositoryModule();
    }
    return instance;
  }

  public SongRepository getSongRepository() {
    return songRepository;
  }
}
