package com.vargasjuanj.inventory.service;

import com.vargasjuanj.inventory.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IProductService {
    public ResponseEntity<?> save(Product product, Long idCategoria, MultipartFile picture);
}
