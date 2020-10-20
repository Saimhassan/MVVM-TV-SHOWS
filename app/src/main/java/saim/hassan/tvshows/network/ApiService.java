package saim.hassan.tvshows.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import saim.hassan.tvshows.responses.TVShowsResponse;

public interface ApiService {
    @GET("most-popular")
    Call<TVShowsResponse> getMostPopularTVShows(@Query("page") int page);



}
