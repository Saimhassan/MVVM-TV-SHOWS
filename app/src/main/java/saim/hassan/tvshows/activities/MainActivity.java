package saim.hassan.tvshows.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import saim.hassan.tvshows.R;
import saim.hassan.tvshows.adapters.TVShowsAdapter;
import saim.hassan.tvshows.databinding.ActivityMainBinding;
import saim.hassan.tvshows.listeners.TVShowsListener;
import saim.hassan.tvshows.models.TVShow;
import saim.hassan.tvshows.viewmodels.MostPopularTVShowsViewModel;

public class MainActivity extends AppCompatActivity implements TVShowsListener {
    private ActivityMainBinding activityMainBinding;
    private MostPopularTVShowsViewModel viewModel;
    private List<TVShow> tvShows = new ArrayList<>();
    private TVShowsAdapter tvShowsAdapter;
    private int currentPage = 1;
    private int totalAvailablePages = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        doInitialization();
    }

    private void doInitialization(){
        activityMainBinding.tvShowsRecyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);
        tvShowsAdapter = new TVShowsAdapter(tvShows,this);
        activityMainBinding.tvShowsRecyclerView.setAdapter(tvShowsAdapter);
        activityMainBinding.tvShowsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activityMainBinding.tvShowsRecyclerView.canScrollVertically(1)){
                    if (currentPage <= totalAvailablePages){
                        currentPage += 1;
                        getMostPopularTVShows();
                    }
                }
            }
        });
        getMostPopularTVShows();
    }

    private void getMostPopularTVShows(){
        toggleLoading();
        viewModel.getMostPopularTVShows(currentPage).observe(this,mostPopularTVShowsResponse->{
                     toggleLoading();
                     if (mostPopularTVShowsResponse != null){
                         totalAvailablePages = mostPopularTVShowsResponse.getTotalPages();
                         if (mostPopularTVShowsResponse.getTvShows() != null){
                             int oldCount = tvShows.size();
                             tvShows.addAll(mostPopularTVShowsResponse.getTvShows());
                             tvShowsAdapter.notifyItemRangeInserted(oldCount,tvShows.size());
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

    @Override
    public void onTVShowClicked(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext(),TVShowsDetailActivity.class);
        intent.putExtra("tvShow",tvShow);

        startActivity(intent);
    }
}