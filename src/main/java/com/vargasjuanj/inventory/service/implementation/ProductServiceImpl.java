package com.vargasjuanj.inventory.service.implementation;

import com.vargasjuanj.inventory.dao.CategoryRepository;
import com.vargasjuanj.inventory.dao.ProductRepository;
import com.vargasjuanj.inventory.model.Category;
import com.vargasjuanj.inventory.model.Product;
import com.vargasjuanj.inventory.response.Respuesta;
import com.vargasjuanj.inventory.service.BaseService;
import com.vargasjuanj.inventory.service.IProductService;
import com.vargasjuanj.inventory.util.ImagenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


/*
Sí, exactamente. En el código que has proporcionado, al anotar la clase `ProductServiceImpl` con `@Service`, le estás diciendo a Spring que la gestione como un bean de servicio y que la incluya en el contexto de la aplicación para que pueda manejar la inyección de dependencias.

Cuando tienes un constructor en `BaseService` que espera un parámetro de tipo `R`, estás definiendo un punto de inyección de dependencias. Esto significa que cualquier clase que herede de `BaseService` necesitará proporcionar una implementación concreta de `JpaRepository` (o una de sus subclases) para que `BaseService` pueda realizar operaciones de persistencia en la base de datos.

Al hacer `super(repository)` en el constructor de `ProductServiceImpl`, estás pasando la implementación concreta de `JpaRepository` al constructor de la clase base `BaseService`. Spring se encargará de proporcionar esta implementación automáticamente debido a la anotación `@Repository` que seguramente tendrás en alguna clase que implementa `JpaRepository`.

Esto es parte del mecanismo de inyección de dependencias de Spring, que permite que los componentes de tu aplicación interactúen sin la necesidad de crear instancias manualmente o tener que preocuparte por la gestión de dependencias.
*/
@Service
public  class ProductServiceImpl extends BaseService<Product, ProductRepository> implements IProductService {
    private CategoryRepository categoryRepository;

    // Para uso general, esto es obligatorio para poder usar en BaseService la inyección de dependencias por constructor
    public ProductServiceImpl(ProductRepository repository, CategoryRepository categoryRepository) {
        super(repository);
        this.categoryRepository = categoryRepository; // se inyecta
    }

    @Override
    public ResponseEntity<?> save(Product product, Long categoryID, MultipartFile picture) {
        Respuesta<Product> respuesta = new Respuesta<>();
        try {
            product.setPicture(ImagenUtil.compressZLib(picture.getBytes()));
            Optional<Category> categoryOptional = categoryRepository.findById(categoryID);
            if (categoryOptional.isPresent()) {
                product.setCategory(categoryOptional.get());
                Product entity = repository.save(product);
                respuesta.setMetadata("Respuesta Ok", "00", "Respuesta exitosa");
                respuesta.getResultados().add(entity);
                logger.info("Entity: ", entity);
                return new ResponseEntity<>(respuesta, HttpStatus.OK);
            } else {
                respuesta.setMetadata("Respuesta nok", "-1", "Categoria asociada no encontrada, idCategoria: " + categoryID);
                return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            respuesta.setMetadata("Respuesta nok", "-1", "Error al guardar ");
            logger.error("Error al guardar " + product, e);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findByNameContainingIgnoreCase(String name) {
        Respuesta<Product> respuesta = new Respuesta<>();
        List<Product> products = this.repository.findByNameContainingIgnoreCase(name);
        try {
            products.stream().forEach(p -> p.setPicture(ImagenUtil.decompressZLib(p.getPicture())));
            respuesta.setMetadata("Respuesta Ok", "00", "Respuesta exitosa");
            respuesta.setResultados(products);
            logger.info("List: ", products);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } catch (Exception e) {
            respuesta.setMetadata("Respuesta nok", "-1", "Error al guardar ");
            logger.error("Error al guardar " + products, e);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<?> getAll() {
        Respuesta<Product> response = new Respuesta<>();
        try {
            List<Product> entities = repository.findAll();
            entities.stream().forEach(e -> e.setPicture(ImagenUtil.decompressZLib(e.getPicture())));
            response.setResultados(entities);
            response.setMetadata("Respuesta Ok", "00", "Respuesta exitosa");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMetadata("Respuesta nok", "-1", "Error al consultar");
            logger.error("Error al traer todas las entities", e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> update(Product product, Long idCategoria, Long id) {
        return null;
    }
}