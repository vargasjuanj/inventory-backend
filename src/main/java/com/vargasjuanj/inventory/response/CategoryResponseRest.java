package com.vargasjuanj.inventory.response;


import com.vargasjuanj.inventory.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryResponseRest extends ResponseRest {
    //se dejó directamente la clase Category
   // private CategoryResponse categoryResponse = new CategoryResponse();
    private List<Category> categoryList = new ArrayList<>();
   // pongo el get y set porque no esta tomando lombok

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> category) {
        this.categoryList = category;
    }
}
