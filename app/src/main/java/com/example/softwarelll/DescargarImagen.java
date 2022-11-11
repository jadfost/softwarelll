package com.example.softwarelll;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


public class DescargarImagen implements ImageLoadingListener {
    Context context;
    private final ImageLoader imageLoader;
    private ProgressBar spinningWheel;

    public DescargarImagen(Context context) {
        this.imageLoader = ImageLoader.getInstance();
        this.context = context;
        this.imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }

    public void download(ImageView view, String imageUrl) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()//
                .showImageOnFail(R.drawable.shopping_basket_green)
                .showImageForEmptyUri(R.drawable.shopping_basket_green)
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .build();
        this.imageLoader.displayImage(imageUrl, view, options);
    }

    public void download(ImageView view, ProgressBar spinningWheel, String imageUrl) {

        this.spinningWheel = spinningWheel;
        setWheelColor();

        DisplayImageOptions options = new DisplayImageOptions.Builder()//
                .showImageOnFail(R.drawable.shopping_basket_greyed_out)
                .showImageForEmptyUri(R.drawable.shopping_basket_greyed_out)
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .build();
        this.imageLoader.displayImage(imageUrl, view, options, this);
    }

    private void setWheelColor() {
        int color = R.color.secondary;
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_ATOP;
        spinningWheel.getIndeterminateDrawable().setColorFilter(context.getResources().getColor(color), mode);
    }

    @Override
    public void onLoadingStarted(String imageUri, View view) {
        spinningWheel.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
        spinningWheel.setVisibility(View.GONE);
    }

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        spinningWheel.setVisibility(View.GONE);
    }

    @Override
    public void onLoadingCancelled(String imageUri, View view) {
        spinningWheel.setVisibility(View.GONE);
    }
}

