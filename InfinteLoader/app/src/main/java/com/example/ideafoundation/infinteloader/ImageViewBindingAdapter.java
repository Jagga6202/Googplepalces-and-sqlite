package com.example.ideafoundation.infinteloader;

/**
 * Created by ideafoundation on 15/02/17.
 */

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageViewBindingAdapter {
    @BindingAdapter("imageSrc")
    public static void setImageSource(ImageView imageView, String imageSrc){
        Glide.with(imageView.getContext()).load(imageSrc).placeholder(R.drawable.mtg_card_back).crossFade().into(imageView);
    }
}
