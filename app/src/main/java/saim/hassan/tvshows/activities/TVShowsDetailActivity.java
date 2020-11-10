package saim.hassan.tvshows.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import saim.hassan.tvshows.R;
import saim.hassan.tvshows.adapters.EpisodesAdapter;
import saim.hassan.tvshows.adapters.ImageSliderAdapter;
import saim.hassan.tvshows.databinding.ActivityTVShowsDetailBinding;
import saim.hassan.tvshows.databinding.LayoutEpisodeBottomSheetBinding;
import saim.hassan.tvshows.models.TVShow;
import saim.hassan.tvshows.viewmodels.TvShowDetailsViewModel;

public class TVShowsDetailActivity extends AppCompatActivity {
    private ActivityTVShowsDetailBinding activityTVShowsDetailBinding;
    private TvShowDetailsViewModel tvShowDetailsViewModel;
    private BottomSheetDialog episodesBottomSheetDialog;
    private LayoutEpisodeBottomSheetBinding layoutEpisodeBottomSheetBinding;
    private TVShow tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTVShowsDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_t_v_shows_detail);
        doInitaialization();
    }

    private void doInitaialization() {
        tvShowDetailsViewModel = new ViewModelProvider(this).get(TvShowDetailsViewModel.class);
        activityTVShowsDetailBinding.imageBack.setOnClickListener(v -> onBackPressed());
        tvShow = (TVShow) getIntent().getSerializableExtra("tvShow");
        getTVShowDetails();
    }

    private void getTVShowDetails() {
        activityTVShowsDetailBinding.setIsLoading(true);
        String tvShowId = String.valueOf(tvShow.getId());
        tvShowDetailsViewModel.getTVShowDetails(tvShowId).observe(
                this, tvShowDetailResponse -> {
                    activityTVShowsDetailBinding.setIsLoading(false);
                    if (tvShowDetailResponse.getTvShowDetail() != null) {
                        if (tvShowDetailResponse.getTvShowDetail().getPictures() != null) {
                            loadImageSlider(tvShowDetailResponse.getTvShowDetail().getPictures());
                        }
                        activityTVShowsDetailBinding.setTvShowImageURL(
                                tvShowDetailResponse.getTvShowDetail().getImagePath()
                        );
                        activityTVShowsDetailBinding.imageTVShow.setVisibility(View.VISIBLE);
                        activityTVShowsDetailBinding.setDescription(
                                String.valueOf(
                                        HtmlCompat.fromHtml(
                                                tvShowDetailResponse.getTvShowDetail().getDescription(),
                                                HtmlCompat.FROM_HTML_MODE_LEGACY
                                        )
                                )
                        );
                        activityTVShowsDetailBinding.textDescription.setVisibility(View.VISIBLE);
                        activityTVShowsDetailBinding.textReadMore.setVisibility(View.VISIBLE);
                        activityTVShowsDetailBinding.textReadMore.setOnClickListener(v -> {
                            if (activityTVShowsDetailBinding.textReadMore.getText().toString().equals("Read More")) {
                                activityTVShowsDetailBinding.textDescription.setMaxLines(Integer.MAX_VALUE);
                                activityTVShowsDetailBinding.textDescription.setEllipsize(null);
                                activityTVShowsDetailBinding.textReadMore.setText(R.string.read_less);
                            } else {
                                activityTVShowsDetailBinding.textDescription.setMaxLines(4);
                                activityTVShowsDetailBinding.textDescription.setEllipsize(TextUtils.TruncateAt.END);
                                activityTVShowsDetailBinding.textReadMore.setText(R.string.read_more);
                            }
                        });
                        activityTVShowsDetailBinding.setRating(
                                String.format(
                                        Locale.getDefault(),
                                        "%.2f",
                                        Double.parseDouble(tvShowDetailResponse.getTvShowDetail().getRating())

                                )
                        );
                        if (tvShowDetailResponse.getTvShowDetail().getGenres() != null) {
                            activityTVShowsDetailBinding.setGenre(tvShowDetailResponse.getTvShowDetail().getGenres()[0]);
                        } else {
                            activityTVShowsDetailBinding.setGenre("N/A");
                        }
                        activityTVShowsDetailBinding.setRuntime(tvShowDetailResponse.getTvShowDetail().getRuntime() + " Min");
                        activityTVShowsDetailBinding.viewDivider1.setVisibility(View.VISIBLE);
                        activityTVShowsDetailBinding.layoutMisc.setVisibility(View.VISIBLE);
                        activityTVShowsDetailBinding.viewDivider2.setVisibility(View.VISIBLE);
                        activityTVShowsDetailBinding.buttonWebsite.setOnClickListener(v -> {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(tvShowDetailResponse.getTvShowDetail().getUrl()));
                            startActivity(intent);
                        });
                        activityTVShowsDetailBinding.buttonWebsite.setVisibility(View.VISIBLE);
                        activityTVShowsDetailBinding.buttonEpisodes.setVisibility(View.VISIBLE);
                        activityTVShowsDetailBinding.buttonEpisodes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (episodesBottomSheetDialog == null) {
                                    episodesBottomSheetDialog = new BottomSheetDialog(TVShowsDetailActivity.this);
                                    layoutEpisodeBottomSheetBinding = DataBindingUtil.inflate(
                                            LayoutInflater.from(TVShowsDetailActivity.this),
                                            R.layout.layout_episode_bottom_sheet,
                                            findViewById(R.id.episodesContainer),
                                            false
                                    );
                                    episodesBottomSheetDialog.setContentView(layoutEpisodeBottomSheetBinding.getRoot());
                                    layoutEpisodeBottomSheetBinding.episodesRecyclerView.setAdapter(
                                            new EpisodesAdapter(tvShowDetailResponse.getTvShowDetail().getEpisodes())
                                    );
                                    layoutEpisodeBottomSheetBinding.textTitle.setText(
                                            String.format("Episodes | %s", tvShow.getName())
                                    );
                                    layoutEpisodeBottomSheetBinding.imageClose.setOnClickListener(v1 -> episodesBottomSheetDialog.dismiss());
                                }

                                FrameLayout frameLayout = episodesBottomSheetDialog.findViewById(
                                        com.google.android.material.R.id.design_bottom_sheet
                                );
                                if (frameLayout != null) {
                                    BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
                                    bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                }
                                episodesBottomSheetDialog.show();
                            }
                        });
                        activityTVShowsDetailBinding.imageWatchList.setOnClickListener(v ->
                                new CompositeDisposable().add(tvShowDetailsViewModel.addToWatchList(tvShow)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                    activityTVShowsDetailBinding.imageWatchList.setImageResource(R.drawable.ic_round_check_24);
                                    Toast.makeText(getApplicationContext(), "Added to watchlist", Toast.LENGTH_SHORT).show();
                                })));
                        activityTVShowsDetailBinding.imageWatchList.setVisibility(View.VISIBLE);
                        loadBasicTVShowsDetail();
                    }
                }
        );
    }

    private void loadImageSlider(String[] sliderImages) {
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

    private void setupSliderIndicator(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
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

    private void setCurrentSliderIndicator(int position) {
        int childCount = activityTVShowsDetailBinding.layoutSliderIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) activityTVShowsDetailBinding.layoutSliderIndicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_active)
                );
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_inactive));
            }
        }
    }

    private void loadBasicTVShowsDetail() {
        activityTVShowsDetailBinding.setTvShowName(tvShow.getName());
        activityTVShowsDetailBinding.setNetworkCountry(
                tvShow.getNetwork() + " (" + tvShow.getCountry() + ")"
        );
        activityTVShowsDetailBinding.setStatus(tvShow.getStatus());
        activityTVShowsDetailBinding.setStartedDate(tvShow.getStartDate());
        activityTVShowsDetailBinding.textName.setVisibility(View.VISIBLE);
        activityTVShowsDetailBinding.textNetworkCountry.setVisibility(View.VISIBLE);
        activityTVShowsDetailBinding.textStatus.setVisibility(View.VISIBLE);
        activityTVShowsDetailBinding.textStarted.setVisibility(View.VISIBLE);
    }
}