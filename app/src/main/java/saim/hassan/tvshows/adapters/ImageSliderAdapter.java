package saim.hassan.tvshows.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import saim.hassan.tvshows.databinding.ItemConatinerSliderImageBinding;

public class ImageSliderAdapter {
    static class ImageSliderViewHolder extends RecyclerView.ViewHolder {

        private ItemConatinerSliderImageBinding itemConatinerSliderImageBinding;

        public ImageSliderViewHolder(ItemConatinerSliderImageBinding itemConatinerSliderImageBinding){
            super(itemConatinerSliderImageBinding.getRoot());
            this.itemConatinerSliderImageBinding = itemConatinerSliderImageBinding;
        }

        public void bindSliderImage(String imageURL){
            itemConatinerSliderImageBinding.setImageURL(imageURL);
        }
    }
}
