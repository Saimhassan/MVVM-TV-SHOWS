package saim.hassan.tvshows.adapters;

import androidx.recyclerview.widget.RecyclerView;

import saim.hassan.tvshows.databinding.ItemConatinerEpisodesBinding;
import saim.hassan.tvshows.databinding.ItemContainerTvShowBinding;
import saim.hassan.tvshows.models.Episode;

public class EpisodesAdapter {
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
