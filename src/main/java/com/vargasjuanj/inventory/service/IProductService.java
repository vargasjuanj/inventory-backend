package com.vargasjuanj.inventory.service;

import com.vargasjuanj.inventory.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService {
    ResponseEntity<?> save(Product product, Long idCategoria, MultipartFile picture);
    ResponseEntity<?> findByNameContainingIgnoreCase(String name);

    ResponseEntity<?> getAll();

    ResponseEntity<?> update(Product product, Long idCategoria, Long id);



}
