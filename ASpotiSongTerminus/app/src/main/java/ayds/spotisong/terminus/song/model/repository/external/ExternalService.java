package ayds.spotisong.terminus.song.model.repository.external;

import ayds.spotisong.terminus.song.model.Song;

public interface ExternalService {

  Song getSong(String query) throws Exception;
}
