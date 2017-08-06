package humazed.github.com.smartbaking_java.model;


import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import humazed.github.com.smartbaking_java.utils.auto_gson.AutoGson;

@AutoValue
@AutoGson
public abstract class Ingredient implements Parcelable {

    public abstract String quantity();

    public abstract String measure();

    public abstract String ingredient();


    public static Ingredient create(String quantity, String measure, String ingredient) {return new AutoValue_Ingredient(quantity, measure, ingredient);}


    @Override
    public String toString() {
        return String.format("* %s of %s %s \n", quantity(), measure(), ingredient());
    }

}