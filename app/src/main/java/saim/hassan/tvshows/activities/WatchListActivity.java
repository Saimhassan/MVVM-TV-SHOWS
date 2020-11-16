package saim.hassan.tvshows.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import saim.hassan.tvshows.R;
import saim.hassan.tvshows.databinding.ActivityWatchListBinding;
import saim.hassan.tvshows.viewmodels.WatchListViewModel;

public class WatchListActivity extends AppCompatActivity {

    private ActivityWatchListBinding activityWatchListBinding;
    private WatchListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWatchListBinding = DataBindingUtil.setContentView(this,R.layout.activity_watch_list);
        doInitalization();
    }
    private void doInitalization(){
      viewModel = new ViewModelProvider(this).get(WatchListViewModel.class);
      activityWatchListBinding.imageBack.setOnClickListener(v -> onBackPressed());
    }
}