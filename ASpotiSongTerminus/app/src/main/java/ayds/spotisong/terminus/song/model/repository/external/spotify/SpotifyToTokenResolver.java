package ayds.spotisong.terminus.song.model.repository.external.spotify;

public interface SpotifyToTokenResolver {

  String getTokenFromExternalData(String serviceData) throws Exception;
}

