package com.vargasjuanj.inventory.controller;


import com.vargasjuanj.inventory.model.Category;
import com.vargasjuanj.inventory.service.implementation.CategoryServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class CategoryController extends BaseController<Category, CategoryServiceImpl> {

}
