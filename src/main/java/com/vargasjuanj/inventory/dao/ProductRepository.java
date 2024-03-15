package com.vargasjuanj.inventory.dao;

import com.vargasjuanj.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    //para consultas mas complejas se usa query
  //  @Query("select p from Product p where p.name like %?1%")
   // List<Product> findByNameLike(String name);

    List<Product> findByNameContainingIgnoreCase(String name);

}
