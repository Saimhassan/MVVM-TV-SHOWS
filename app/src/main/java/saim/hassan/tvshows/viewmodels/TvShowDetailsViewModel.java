package saim.hassan.tvshows.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import saim.hassan.tvshows.repositories.TVShowDetailRepository;
import saim.hassan.tvshows.responses.TVShowDetailResponse;

public class TvShowDetailsViewModel extends ViewModel {
    private TVShowDetailRepository tvShowDetailRepository;

    public TvShowDetailsViewModel(){
        tvShowDetailRepository = new TVShowDetailRepository();
    }

    public LiveData<TVShowDetailResponse> getTVShowDetails(String tvShowId){
        return tvShowDetailRepository.getTvShowDetails(tvShowId);
    }
}
