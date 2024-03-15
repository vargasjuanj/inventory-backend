package com.vargasjuanj.inventory.service.implementation;

import com.vargasjuanj.inventory.dao.CategoryRepository;
import com.vargasjuanj.inventory.model.Category;
import com.vargasjuanj.inventory.service.BaseService;
import com.vargasjuanj.inventory.service.ICategoryService;
import org.springframework.stereotype.Service;



@Service
//@Qualifier("uno")
public class CategoryServiceImpl extends BaseService<Category,CategoryRepository> implements ICategoryService {

  //Primero se inyecta aca el categoryrepo, y luego se pasa a la clase base, al constructor
    public CategoryServiceImpl(CategoryRepository repository) {
        super(repository);
    }

    @Override
    public void otroMetodoEspecial() {

    }
}