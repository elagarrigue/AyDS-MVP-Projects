package ayds.spotisong.gaia.otherdetails.fulllogic;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface DiscoGSAPI {

  @GET("search?type=master&country=us&per_page=100&page=1&token=oIMtIsUWyDBuxxzNSTxObtOHBYENSMJgTIKcRhYp")
  Call<String> getArtistInfo(@Query("q") String album, @Query("artist") String artist);

}
