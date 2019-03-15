package ayds.spotisong.gaia.main;

import ayds.spotisong.gaia.song.presenter.SearchSongPresenterModule;

public class Main {

  public static void main(String[] args) {
    SearchSongPresenterModule.getInstance().startApp();
  }
}
