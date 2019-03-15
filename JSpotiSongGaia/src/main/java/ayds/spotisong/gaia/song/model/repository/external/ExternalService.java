package ayds.spotisong.gaia.song.model.repository.external;

import ayds.spotisong.gaia.song.model.Song;

public interface ExternalService {

  Song getSong(String query) throws Exception;
}
