package com.vargasjuanj.inventory.service;

import com.vargasjuanj.inventory.response.CategoryResponseRest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ICategoryService {
    /*
    Response entity es una interfaz que permite dar una estructura de respuesta http con un codigo respectivo
    Y englobar una respuesta custom o personalizada, en este caso esa respuesta Custom esta englobada en CAtegoryResponseRest
     */
    public ResponseEntity<CategoryResponseRest> findAll();
    public ResponseEntity<CategoryResponseRest> findById(Long id);

}
