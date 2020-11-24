package saim.hassan.tvshows.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import saim.hassan.tvshows.database.TVShowDatabase;
import saim.hassan.tvshows.models.TVShow;

public class WatchListViewModel extends AndroidViewModel {

    private TVShowDatabase tvShowDatabase;
    public WatchListViewModel(@NonNull Application application){
        super(application);
        tvShowDatabase = TVShowDatabase.getTvShowDatabase(application);
    }

    public Flowable<List<TVShow>> loadWatchlist(){
        return tvShowDatabase.tvShowDao().getWatchList();
    }
    public Completable removeTVShowFromWatchList(TVShow tvShow){
        return tvShowDatabase.tvShowDao().removeFromWatchList(tvShow);
    }

}
