package com.vargasjuanj.inventory.dao;

import com.vargasjuanj.inventory.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
