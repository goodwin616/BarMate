package edu.virginia.cs.httpscs4720.barmate;

/**
 * Created by Goodwin on 9/21/15.
 */
public class Ingredient {

    private String name = null;
    private boolean selected = false;

    public Ingredient(String name) {
        super();
        this.name = name;
        this.selected = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
