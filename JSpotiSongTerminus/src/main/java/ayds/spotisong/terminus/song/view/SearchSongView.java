package ayds.spotisong.terminus.song.view;

public interface SearchSongView {

  String getQuery();

  void setSongDescription(String description);

  void enableActionButtons();

  void disableActionButtons();
}
