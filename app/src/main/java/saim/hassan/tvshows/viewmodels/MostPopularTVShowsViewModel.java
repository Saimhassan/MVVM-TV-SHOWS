package saim.hassan.tvshows.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import saim.hassan.tvshows.repositories.MostPopularTVShowsRepository;
import saim.hassan.tvshows.responses.TVShowsResponse;

public class MostPopularTVShowsViewModel extends ViewModel {
    private MostPopularTVShowsRepository mostPopularTVShowsRepository;
    public MostPopularTVShowsViewModel(){
        mostPopularTVShowsRepository = new MostPopularTVShowsRepository();

    }
    public LiveData<TVShowsResponse> getMostPopularTVShows(int page){
         return mostPopularTVShowsRepository.getMostPopularTVShows(page);
    }

}
