package ayds.spotisong.gaia.song.model.repository.local;


import ayds.spotisong.gaia.song.model.repository.local.sql.SqlModule;

public class LocalModule {

  private static LocalModule instance;

  private LocalModule() {
  }

  public static LocalModule getInstance() {
    if (instance == null) {
      instance = new LocalModule();
    }
    return instance;
  }

  public LocalDB getLocalDB() {
    return SqlModule.getInstance().getLocalDB();
  }
}
