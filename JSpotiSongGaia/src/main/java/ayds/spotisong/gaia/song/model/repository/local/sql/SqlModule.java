package ayds.spotisong.gaia.song.model.repository.local.sql;

import ayds.spotisong.gaia.song.model.repository.local.LocalDB;

public class SqlModule {

  private static SqlModule instance;

  private final LocalDB localDB;

  private SqlModule() {
    localDB = new SqlDB();
  }

  public static SqlModule getInstance() {
    if (instance == null) {
      instance = new SqlModule();
    }
    return instance;
  }

  public LocalDB getLocalDB() {
    return localDB;
  }
}
