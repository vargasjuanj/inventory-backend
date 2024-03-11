package com.vargasjuanj.inventory.response;


import com.vargasjuanj.inventory.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryResponseRest extends ResponseRest {
    //se dejó directamente la clase Category
   // private CategoryResponse categoryResponse = new CategoryResponse();
    private List<Category> category = new ArrayList<>();
   // pongo el get y set porque no esta tomando lombok

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }
}
