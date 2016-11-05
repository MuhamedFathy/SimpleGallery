package net.mEmoZz.gallery.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import net.mEmoZz.gallery.Constants;
import net.mEmoZz.gallery.R;
import net.mEmoZz.gallery.models.ImageModel;
import net.mEmoZz.gallery.views.activities.GalleryPagerActivity;
import net.mEmoZz.gallery.views.FlexibleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mEmoZz on 11/4/16.
 * muhamed.gendy@gmail.com
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ImagesHolder> {

    private static final int ANIM_DURATION = 800;

    private List<ImageModel> modelList;
    private Activity activity;

    public GalleryAdapter(Activity activity, List<ImageModel> modelList) {
        this.activity = activity;
        this.modelList = modelList;
    }

    @Override
    public ImagesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ImagesHolder(inflater.inflate(R.layout.row_gallery_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ImagesHolder holder, int position) {
        ImageModel item = modelList.get(holder.getAdapterPosition());
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) holder.pic.getLayoutParams();
        float ratio = item.getHeight() / item.getWidth();
        params.height = (int) (params.width * ratio);
        holder.pic.setLayoutParams(params);
        holder.pic.setRatio(item.getRatio());
        loadPic(holder, item.getUrl(), holder.pic);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public void clear() {
        if (modelList != null) {
            int size = modelList.size();
            modelList.clear();
            notifyItemRangeRemoved(0, size);
            notifyDataSetChanged();
        }
    }

    private void loadPic(final ImagesHolder holder, String link, ImageView pic) {
        Glide.with(pic.getContext())
                .load(link)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model,
                                               Target<GlideDrawable> target, boolean isFirstResource) {
                        holder.loader.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model,
                                                   Target<GlideDrawable> target, boolean isFromMemoryCache,
                                                   boolean isFirstResource) {
                        holder.loader.setVisibility(View.GONE);
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.default_image)
                .crossFade(ANIM_DURATION)
                .into(pic);
    }

    class ImagesHolder extends RecyclerView.ViewHolder {
        FlexibleImageView pic;
        ProgressBar loader;

        ImagesHolder(View itemView) {
            super(itemView);
            pic = (FlexibleImageView) itemView.findViewById(R.id.pic);

            loader = (ProgressBar) itemView.findViewById(R.id.loader);

            pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        v.setTransitionName(Constants.TRANSITION_NAME);
                    Pair<View, String> pair = Pair.create(v, Constants.TRANSITION_NAME);
                    activity.startActivityForResult(new Intent(v.getContext(), GalleryPagerActivity.class)
                                    .putParcelableArrayListExtra(Constants.KEY_LIST,
                                            (ArrayList<? extends Parcelable>) modelList)
                                    .putExtra(Constants.KEY_CLICKED_POSITION, getAdapterPosition()), Constants.CURRENT_POSITION_REQ_CODE,
                            ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair).toBundle());
                }
            });
        }
    }
}
