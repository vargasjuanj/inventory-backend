package com.vargasjuanj.inventory.service.implementation;

import com.vargasjuanj.inventory.dao.CategoryRepository;
import com.vargasjuanj.inventory.model.Category;
import com.vargasjuanj.inventory.response.CategoryResponseRest;
import com.vargasjuanj.inventory.service.ICategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Qualifier("uno")
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Override
    /*
    Este metodo se maneja como una transaccion, si algo falla en la bd vuelve para atras. Quedando la integridad como corresponde
    En este caso permite operaciones de solo lectura a la bd, mejorando el rendimiento y evitando bloqueos de concurrencia
    al no ser operaciones de escritura
     */

    @Transactional(readOnly = true) // tiene que importarse de spring
    public ResponseEntity<CategoryResponseRest> findAll() {
        CategoryResponseRest response = new CategoryResponseRest();
        try {
            List<Category> category = categoryRepository.findAll();
            response.setCategory(category);
            response.setMetadata("Respuesta Ok","00","Respuesta exitosa");
        } catch (Exception e) {
            response.setMetadata("Respuesta nok","-1","Error al consultar");
            e.getStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.OK);
    }

}
