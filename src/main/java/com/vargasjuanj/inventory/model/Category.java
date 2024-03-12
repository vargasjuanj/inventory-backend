package com.vargasjuanj.inventory.model;

import jakarta.persistence.Entity;

@Entity
//@Table(name ="category")
public class Category extends BaseEntity { //clase POJO

    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
