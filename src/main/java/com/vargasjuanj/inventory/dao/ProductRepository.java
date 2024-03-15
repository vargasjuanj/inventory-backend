package com.vargasjuanj.inventory.dao;

import com.vargasjuanj.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
