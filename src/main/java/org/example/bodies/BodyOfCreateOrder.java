package org.example.bodies;

import java.util.List;

public class BodyOfCreateOrder {

    private List<String> ingredients;

    public BodyOfCreateOrder(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}

