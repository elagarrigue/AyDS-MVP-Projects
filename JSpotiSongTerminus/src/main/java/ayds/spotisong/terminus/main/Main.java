package ayds.spotisong.terminus.main;

import ayds.spotisong.terminus.song.presenter.SearchSongPresenterModule;

public class Main {

  public static void main(String[] args) {
    SearchSongPresenterModule.getInstance().startApp();
  }
}
