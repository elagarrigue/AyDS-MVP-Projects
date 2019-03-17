package ayds.spotisong.terminus.otherdetails.fulllogic;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LyricsAPI {

  @GET("lyric/{artist}/{track}?apikey=dTRPy7BE2FooWBPRz1NG9hMhoPLYJs57wAIQyDFZv3of8beE9ljOfpdqb4oeNap7")
  Call<String> getLyrics(@Path("artist") String artist, @Path("track") String track);


  //https://loremflickr.com/640/480/artist,music/all
}
