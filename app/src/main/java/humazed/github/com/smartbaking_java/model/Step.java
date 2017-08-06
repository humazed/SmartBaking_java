package humazed.github.com.smartbaking_java.model;


import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import humazed.github.com.smartbaking_java.utils.auto_gson.AutoGson;

@AutoValue
@AutoGson
public abstract class Step implements Parcelable {
    public abstract int id();

    public abstract String shortDescription();

    public abstract String description();

    public abstract String videoURL();

    public abstract String thumbnailURL();

    public static Step create(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {return new AutoValue_Step(id, shortDescription, description, videoURL, thumbnailURL);}
}