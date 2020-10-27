package saim.hassan.tvshows.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import saim.hassan.tvshows.R;
import saim.hassan.tvshows.adapters.ImageSliderAdapter;
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
        activityTVShowsDetailBinding.imageBack.setOnClickListener(v -> onBackPressed());
        getTVShowDetails();
    }
    private void getTVShowDetails(){
        activityTVShowsDetailBinding.setIsLoading(true);
        String tvShowId = String.valueOf(getIntent().getIntExtra("id",-1));
        tvShowDetailsViewModel.getTVShowDetails(tvShowId).observe(
                this,tvShowDetailResponse -> {
                    activityTVShowsDetailBinding.setIsLoading(false);
                    if (tvShowDetailResponse.getTvShowDetail() != null){
                        if (tvShowDetailResponse.getTvShowDetail().getPictures() != null){
                             loadImageSlider(tvShowDetailResponse.getTvShowDetail().getPictures());
                        }
                        activityTVShowsDetailBinding.setTvShowImageURL(
                                tvShowDetailResponse.getTvShowDetail().getImagePath()
                        );
                        activityTVShowsDetailBinding.imageTVShow.setVisibility(View.VISIBLE);
                    }
                }
        );
    }
    private void loadImageSlider(String[] sliderImages){
        activityTVShowsDetailBinding.sliderViewPager.setOffscreenPageLimit(1);
        activityTVShowsDetailBinding.sliderViewPager.setAdapter(new ImageSliderAdapter(sliderImages));
        activityTVShowsDetailBinding.sliderViewPager.setVisibility(View.VISIBLE);
        activityTVShowsDetailBinding.viewFadingEdge.setVisibility(View.VISIBLE);
        setupSliderIndicator(sliderImages.length);
        activityTVShowsDetailBinding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicator(position);
            }
        });
    }

    private void setupSliderIndicator(int count){
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for (int i = 0; i<indicators.length;i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.background_slider_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            activityTVShowsDetailBinding.layoutSliderIndicator.addView(indicators[i]);
        }
        activityTVShowsDetailBinding.layoutSliderIndicator.setVisibility(View.VISIBLE);
        setCurrentSliderIndicator(0);

    }

    private void setCurrentSliderIndicator(int position){
        int childCount = activityTVShowsDetailBinding.layoutSliderIndicator.getChildCount();
        for (int i=0;i<childCount;i++){
            ImageView imageView = (ImageView)activityTVShowsDetailBinding.layoutSliderIndicator.getChildAt(i);
            if (i == position){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.background_slider_indicator_active)
                );
            }else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.background_slider_indicator_inactive));
            }
        }
    }
}