package ayds.spotisong.aurora.song.model.repository.local.sql;

import ayds.spotisong.aurora.song.model.Song;
import ayds.spotisong.aurora.song.model.repository.local.LocalDB;

import java.sql.*;

class SqlDB implements LocalDB {
  private final String dbUrl = "jdbc:sqlite:./songs.db";

  SqlDB() {
    createTableIfDoesNotExists();
  }

  @Override
  public void saveSong(Song song) {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(dbUrl);
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);

      statement.executeUpdate("insert into songs values(null, '" + song.getSongName() + "', '" +
              song.getArtistName() + "', '" + song.getAlbumName() + "', '" + song.getUrl() + "')");
    } catch (SQLException e) {
      System.err.println("Error saving " + e.getMessage());
    } finally {
      try {
        if (connection != null)
          connection.close();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  @Override
  public Song getSong(String name) {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(dbUrl);
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);

      ResultSet rs = statement.executeQuery("select * from songs WHERE upper(name) = '" + name.toUpperCase() + "'");
      if (rs.next())
        return new Song(rs.getString("name"),
                rs.getString("artist"),
                rs.getString("album"),
                rs.getString("url"));
    } catch (SQLException e) {
      System.err.println("Get song error " + e.getMessage());
    } finally {
      try {
        if (connection != null)
          connection.close();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    }
    return null;
  }

  private void createTableIfDoesNotExists() {
    try (Connection connection = DriverManager.getConnection(dbUrl)) {
      if (connection != null) {
        DatabaseMetaData dbm = connection.getMetaData();
        ResultSet tables = dbm.getTables(null, null, "songs", null);
        if (!tables.next()) {
          Statement statement = connection.createStatement();
          statement.executeUpdate("create table songs (id INTEGER PRIMARY KEY AUTOINCREMENT, name string, artist string, album string, url string)");
        }
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}