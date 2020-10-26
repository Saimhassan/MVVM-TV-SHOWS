package saim.hassan.tvshows.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import saim.hassan.tvshows.R;
import saim.hassan.tvshows.databinding.ItemConatinerSliderImageBinding;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>{

    private String[] sliderImages;
    private LayoutInflater layoutInflater;

    public ImageSliderAdapter(String[] sliderImages) {
        this.sliderImages = sliderImages;
    }

    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
        {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemConatinerSliderImageBinding sliderImageBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_conatiner_slider_image,parent,false
        );
        return new ImageSliderViewHolder(sliderImageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position) {
        holder.bindSliderImage(sliderImages[position]);
    }

    @Override
    public int getItemCount() {
        return sliderImages.length;
    }

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
