package saim.hassan.tvshows.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import saim.hassan.tvshows.databinding.ItemConatinerEpisodesBinding;
import saim.hassan.tvshows.databinding.ItemContainerTvShowBinding;
import saim.hassan.tvshows.models.Episode;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.EpisodeViewHolder>{

    private List<Episode> episodes;

    public EpisodesAdapter(List<Episode> episodes) {
        this.episodes = episodes;
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class EpisodeViewHolder extends RecyclerView.ViewHolder{
        private ItemConatinerEpisodesBinding itemConatinerEpisodesBinding;
        public EpisodeViewHolder(ItemConatinerEpisodesBinding itemConatinerEpisodesBinding){
            super(itemConatinerEpisodesBinding.getRoot());
            this.itemConatinerEpisodesBinding = itemConatinerEpisodesBinding;
        }
        public void bindEpisode(Episode episode){
            String title = "S";
            String season = episode.getSeason();
            if (season.length() == 1){
                season = "0".concat(season);
            }
            String episodeNumber = episode.getEpisode();
            if (episodeNumber.length() == 1){
                episodeNumber = "0".concat(episodeNumber);
            }
            episodeNumber = "E".concat(episodeNumber);
            title = title.concat(season).concat(episodeNumber);
            itemConatinerEpisodesBinding.setTitle(title);
            itemConatinerEpisodesBinding.setName(episode.getName());
            itemConatinerEpisodesBinding.setAirDate(episode.getAirDate());

        }
    }
}
