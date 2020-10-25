package saim.hassan.tvshows.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import saim.hassan.tvshows.R;
import saim.hassan.tvshows.databinding.ActivityTVShowsDetailBinding;
import saim.hassan.tvshows.viewmodels.TvShowDetailsViewModel;

public class TVShowsDetailActivity extends AppCompatActivity {
    private ActivityTVShowsDetailBinding activityTVShowsDetailBinding;
    private TvShowDetailsViewModel tvShowDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTVShowsDetailBinding = DataBindingUtil.setContentView(this,R.layout.activity_t_v_shows_detail);
        doInitaialization();
    }

    private void doInitaialization(){
        tvShowDetailsViewModel = new ViewModelProvider(this).get(TvShowDetailsViewModel.class);
        getTVShowDetails();
    }
    private void getTVShowDetails(){
        activityTVShowsDetailBinding.setIsLoading(true);
        String tvShowId = String.valueOf(getIntent().getIntExtra("id",-1));
        tvShowDetailsViewModel.getTVShowDetails(tvShowId).observe(
                this,tvShowDetailResponse -> {
                    activityTVShowsDetailBinding.setIsLoading(false);
                    Toast.makeText(getApplicationContext(),tvShowDetailResponse.getTvShowDetail().getUrl(),Toast.LENGTH_SHORT).show();
                }
        );
    }
}