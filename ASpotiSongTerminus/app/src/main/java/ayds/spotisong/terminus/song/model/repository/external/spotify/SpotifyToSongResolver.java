package ayds.spotisong.terminus.song.model.repository.external.spotify;

import ayds.spotisong.terminus.song.model.Song;

public interface SpotifyToSongResolver {

  Song getSongFromExternalData(String serviceData) throws Exception;
}

