package ayds.spotisong.aurora.song.view;

import ayds.spotisong.aurora.song.presenter.SearchSongPresenterModule;

import javax.swing.*;

public class SearchSongViewModule {

  private static SearchSongViewModule instance;

  private final SearchSongWindow searchSongView;

  private SearchSongViewModule() {

    searchSongView = new SearchSongWindow(SearchSongPresenterModule.getInstance().getSearchSongPresenter());
  }

  public static SearchSongViewModule getInstance() {
    if (instance == null) {
      instance = new SearchSongViewModule();
    }
    return instance;
  }

  public SearchSongView getSearchSongView() {
    return searchSongView;
  }

  public void openView() {
    JFrame frame = new JFrame("Artist Info");
    frame.setContentPane(searchSongView.contentPane);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }
}
