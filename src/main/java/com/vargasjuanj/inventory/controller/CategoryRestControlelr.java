package com.vargasjuanj.inventory.controller;

import com.vargasjuanj.inventory.model.Category;
import com.vargasjuanj.inventory.service.ICategoryService;
import com.vargasjuanj.inventory.service.implementation.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//voy a exponer mis servicios de tipo rest
@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
        RequestMethod.PUT })
@RequestMapping("/api/v1") //maping general
public class CategoryRestControlelr extends BaseController<Category, CategoryServiceImpl> {


    @Autowired
    @Qualifier("uno")
    private ICategoryService service;

    // esto se hace con una barra y dos asteriscos y enter. Se colocan automaticamente los parametros.Usa el javadocs
    /**
     * get all categories
     * @return
     */

    // Este metodo esta sobrecargando al metodo getAll de base controller porque tiene diferentes parametros, no lo esta sobrescribiendo
    @GetMapping("/categories")
    public ResponseEntity<?> getAll(){
     // ResponseEntity<CategoryResponseRest> response = service.findAll();
        //para debuggear bien es necesario que aunque sea en el servicio se retorne un objeto con nombre como response y no el resultado del metodo directamente
      return service.getAll();
    }

    /**
     *
     * @param id
     * @return
     */
   // @Override  // este metodo esta sobrescribiendo el de base controller
    @GetMapping("/category/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        return service.getOne(id);
    }
}
