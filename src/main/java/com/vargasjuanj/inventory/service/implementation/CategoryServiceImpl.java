package com.vargasjuanj.inventory.service.implementation;

import com.vargasjuanj.inventory.dao.CategoryRepository;
import com.vargasjuanj.inventory.model.Category;
import com.vargasjuanj.inventory.response.CategoryResponseRest;
import com.vargasjuanj.inventory.service.BaseService;
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
import java.util.Optional;

@Service
@Qualifier("uno")
public class CategoryServiceImpl extends BaseService<Category,CategoryRepository> implements ICategoryService {

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
    public ResponseEntity<?> getAll() {
        CategoryResponseRest response = new CategoryResponseRest();
        try {
            List<Category> categories = categoryRepository.findAll();
            response.setCategoryList(categories);
            response.setMetadata("Respuesta Ok", "00", "Respuesta exitosa");
        } catch (Exception e) {
            response.setMetadata("Respuesta nok", "-1", "Error al consultar");
            e.getStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getOne(Long id) {
        CategoryResponseRest response = new CategoryResponseRest();
        try {
            Optional<Category> categoryOptional = categoryRepository.findById(id);
            Category category = categoryOptional.orElseGet(Category::new);
            if (categoryOptional.isPresent()){
                response.setMetadata("Respuesta Ok", "00", "Respuesta exitosa");
                logger.info("Categoria: ", category);
            }else{
                response.setMetadata("Respuesta nok", "-1", "Categoria no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.getCategoryList().add(category);
        } catch (Exception e) {
            response.setMetadata("Respuesta nok", "-1", "Error al consultar por id");
            logger.error(e.getMessage(), e.getStackTrace(), "Error al consultar por el id " +id);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}