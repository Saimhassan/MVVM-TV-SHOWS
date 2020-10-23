package saim.hassan.tvshows.responses;

import com.google.gson.annotations.SerializedName;

import saim.hassan.tvshows.models.TVShowDetails;

public class TVShowDetailResponse {
    @SerializedName("tvShow")
    private TVShowDetails tvShowDetail;

    public TVShowDetails getTvShowDetail() {
        return tvShowDetail;
    }
}
