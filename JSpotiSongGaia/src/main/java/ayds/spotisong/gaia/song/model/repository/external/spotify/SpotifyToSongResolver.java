package ayds.spotisong.gaia.song.model.repository.external.spotify;

import ayds.spotisong.gaia.song.model.Song;

public interface SpotifyToSongResolver {

  Song getSongFromExternalData(String serviceData) throws Exception;
}

