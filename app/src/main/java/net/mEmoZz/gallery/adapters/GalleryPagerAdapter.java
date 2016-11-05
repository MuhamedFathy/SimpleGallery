package net.mEmoZz.gallery.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.mEmoZz.gallery.R;
import net.mEmoZz.gallery.models.ImageModel;

import java.util.List;

/**
 * Created by mEmoZz on 11/5/16.
 * muhamed.gendy@gmail.com
 */

public class GalleryPagerAdapter extends PagerAdapter {

    private List<ImageModel> modelList;

    public GalleryPagerAdapter(List<ImageModel> modelList) {
        this.modelList = modelList;
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final ImageView pic = new ImageView(container.getContext());
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        pic.setLayoutParams(params);
        ImageModel item = modelList.get(position);
        loadImage(item.getUrl(), pic);
        container.addView(pic);
        return pic;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private void loadImage(String url, ImageView image) {
        Glide.with(image.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.default_image)
                .crossFade(0)
                .into(image);
    }
}
