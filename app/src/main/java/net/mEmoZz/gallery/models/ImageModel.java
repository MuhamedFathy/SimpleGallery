package net.mEmoZz.gallery.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mEmoZz on 11/4/16.
 * muhamed.gendy@gmail.com
 */

public class ImageModel implements Parcelable {

    private int width;
    private int height;
    private float ratio;
    private String url;

    public ImageModel() {

    }

    protected ImageModel(Parcel in) {
        width = in.readInt();
        height = in.readInt();
        ratio = in.readFloat();
        url = in.readString();
    }

    public static final Creator<ImageModel> CREATOR = new Creator<ImageModel>() {
        @Override
        public ImageModel createFromParcel(Parcel in) {
            return new ImageModel(in);
        }

        @Override
        public ImageModel[] newArray(int size) {
            return new ImageModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeFloat(ratio);
        dest.writeString(url);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
