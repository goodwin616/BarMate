package edu.virginia.cs.httpscs4720.barmate;

import java.util.ArrayList;

/**
 * Created by Goodwin on 9/30/15.
 */
public class Recipe {

    private String name;
    private ArrayList<Ingredient> ingredients;
    private boolean partial;

    public Recipe(String name, ArrayList<Ingredient> ingredients, boolean partial) {
        this.name = name;
        this.ingredients = ingredients;
        this.partial = partial;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public boolean isPartial() {
        return partial;
    }
}
