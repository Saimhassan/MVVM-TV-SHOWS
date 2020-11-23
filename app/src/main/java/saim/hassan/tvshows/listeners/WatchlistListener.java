package saim.hassan.tvshows.listeners;

import saim.hassan.tvshows.models.TVShow;

public interface WatchlistListener {
    void onTVShowClicked(TVShow tvShow);
    void removeTVShowFromWatchList(TVShow tvShow,int position);
}
