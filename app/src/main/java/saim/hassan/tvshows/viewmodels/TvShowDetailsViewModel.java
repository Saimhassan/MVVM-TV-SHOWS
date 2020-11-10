package saim.hassan.tvshows.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import io.reactivex.Completable;
import saim.hassan.tvshows.database.TVShowDatabase;
import saim.hassan.tvshows.models.TVShow;
import saim.hassan.tvshows.repositories.TVShowDetailRepository;
import saim.hassan.tvshows.responses.TVShowDetailResponse;

public class TvShowDetailsViewModel extends AndroidViewModel {
    private TVShowDetailRepository tvShowDetailRepository;
    private TVShowDatabase tvShowDatabase;

    public TvShowDetailsViewModel(@NonNull Application application){
        super(application);
        tvShowDetailRepository = new TVShowDetailRepository();
        tvShowDatabase = TVShowDatabase.getTvShowDatabase(application);
    }

    public LiveData<TVShowDetailResponse> getTVShowDetails(String tvShowId){
        return tvShowDetailRepository.getTvShowDetails(tvShowId);
    }
    public Completable addToWatchList(TVShow tvShow){
        return TVShowDatabase.tvShowDao().addToWatchList(tvShow);
    }
}
