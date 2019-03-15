package ayds.spotisong.aurora.main;

import ayds.spotisong.aurora.song.presenter.SearchSongPresenterModule;

public class Main {

  public static void main(String[] args) {
    SearchSongPresenterModule.getInstance().startApp();
  }
}
