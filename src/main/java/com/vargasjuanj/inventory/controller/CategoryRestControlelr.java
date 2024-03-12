package com.vargasjuanj.inventory.controller;

import com.vargasjuanj.inventory.response.CategoryResponseRest;
import com.vargasjuanj.inventory.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//voy a exponer mis servicios de tipo rest
@RestController
@RequestMapping("/api/v1") //maping general
public class CategoryRestControlelr {

    @Autowired
    @Qualifier("uno")
    private ICategoryService service;

    // esto se hace con una barra y dos asteriscos y enter. Se colocan automaticamente los parametros.Usa el javadocs
    /**
     * get all categories
     * @return
     */
    @GetMapping("/categories")
    public ResponseEntity<CategoryResponseRest> findAll(){
     // ResponseEntity<CategoryResponseRest> response = service.findAll();
        //para debuggear bien es necesario que aunque sea en el servicio se retorne un objeto con nombre como response y no el resultado del metodo directamente
      return service.findAll();
    }

    /**
     * get category by id
     * @param id
     * @return
     */
    @GetMapping("/category/{id}")
    public ResponseEntity<CategoryResponseRest> findById(@PathVariable Long id){
        return service.findById(id);
    }
}
