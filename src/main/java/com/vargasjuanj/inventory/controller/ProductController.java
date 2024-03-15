package com.vargasjuanj.inventory.controller;

import com.vargasjuanj.inventory.model.Product;
import com.vargasjuanj.inventory.service.implementation.ProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/v1/product")
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class ProductController extends BaseController<Product, ProductServiceImpl> {
    public ProductController(ProductServiceImpl service) {
        super(service);
    }

    @PostMapping("/")
    ResponseEntity<?> save (
            @RequestParam("picture") MultipartFile picture, // asi envia la foto o archivo
           // esto es enviado de un form data desde angular
            @RequestParam("name") String name,
            @RequestParam("price") BigDecimal price,
            @RequestParam("account") int account,
            @RequestParam("categoryID") Long categoryID
            ) {

       return service.save(new Product(name,price,account), categoryID, picture);
    }
    @GetMapping("filter/{name}")
    public ResponseEntity<?> findByNameContainingIgnoreCase(@PathVariable String name){
        return service.findByNameContainingIgnoreCase(name);
    }

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        return service.getAll();
    }
}
