package saim.hassan.tvshows.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import saim.hassan.tvshows.R;
import saim.hassan.tvshows.adapters.TVShowsAdapter;
import saim.hassan.tvshows.databinding.ActivityMainBinding;
import saim.hassan.tvshows.models.TVShow;
import saim.hassan.tvshows.viewmodels.MostPopularTVShowsViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private MostPopularTVShowsViewModel viewModel;
    private List<TVShow> tvShows = new ArrayList<>();
    private TVShowsAdapter tvShowsAdapter;
    private int currentPage = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        doInitialization();
    }

    private void doInitialization(){
        activityMainBinding.tvShowsRecyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);
        tvShowsAdapter = new TVShowsAdapter(tvShows);
        activityMainBinding.tvShowsRecyclerView.setAdapter(tvShowsAdapter);
        getMostPopularTVShows();
    }

    private void getMostPopularTVShows(){
        toggleLoading();
        viewModel.getMostPopularTVShows(currentPage).observe(this,mostPopularTVShowsResponse->{
                     toggleLoading();
                     if (mostPopularTVShowsResponse != null){
                         if (mostPopularTVShowsResponse.getTvShows() != null){
                             tvShows.addAll(mostPopularTVShowsResponse.getTvShows());
                             tvShowsAdapter.notifyDataSetChanged();
                         }
                     }
                });
    }

    private void toggleLoading(){
        if (currentPage == 1){
          if (activityMainBinding.getIsLoading() != null && activityMainBinding.getIsLoading()){
              activityMainBinding.setIsLoading(false);
          }else {
              activityMainBinding.setIsLoading(true);
          }
        }else {
           if (activityMainBinding.getIsLoadingMore() != null && activityMainBinding.getIsLoadingMore()){
              activityMainBinding.setIsLoadingMore(false);
           }else {
               activityMainBinding.setIsLoadingMore(true);
           }
        }
    }
}