package saim.hassan.tvshows.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saim.hassan.tvshows.network.ApiClient;
import saim.hassan.tvshows.network.ApiService;
import saim.hassan.tvshows.responses.TVShowDetailResponse;

public class TVShowDetailRepository {
    private ApiService apiService;
    public TVShowDetailRepository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TVShowDetailResponse> getTvShowDetails(String tvShowId){
        MutableLiveData<TVShowDetailResponse> data = new MutableLiveData<>();
        apiService.getTVShowDetails(tvShowId).enqueue(new Callback<TVShowDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVShowDetailResponse> call,@NonNull Response<TVShowDetailResponse> response) {
                 data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TVShowDetailResponse> call,@NonNull Throwable t) {
                   data.setValue(null);
            }
        });
        return data;
    }
}
