package humazed.github.com.smartbaking_java.model;


import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import java.util.List;

import humazed.github.com.smartbaking_java.utils.AutoGson;

@AutoValue
@AutoGson
public abstract class Recipe implements Parcelable {
    public abstract int id();

    public abstract String name();

    public abstract List<Ingredient> ingredients();

    public abstract List<Step> steps();

    public abstract int servings();

    public abstract String image();

    public static Recipe create(int id, String name, List ingredients, List steps, int servings, String image) {return new AutoValue_Recipe(id, name, ingredients, steps, servings, image);}

}