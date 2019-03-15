package ayds.spotisong.gaia.song.model.repository.external.spotify;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

class JsonToTokenResolver implements SpotifyToTokenResolver {

  private final Gson gson = new Gson();

  @Override
  public String getTokenFromExternalData(String serviceData) {
    JsonObject tokenResp = gson.fromJson(serviceData, JsonObject.class);
    JsonElement accessToken = tokenResp.get("access_token");

    return accessToken.getAsString();
  }
}
