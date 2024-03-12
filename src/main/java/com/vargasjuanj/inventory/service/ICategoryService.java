package com.vargasjuanj.inventory.service;

import org.springframework.http.ResponseEntity;

public interface ICategoryService {
    /*
    Response entity es una interfaz que permite dar una estructura de respuesta http con un codigo respectivo
    Y englobar una respuesta custom o personalizada, en este caso esa respuesta Custom esta englobada en CAtegoryResponseRest
     */
    public ResponseEntity<?> getAll();
    public ResponseEntity<?> getOne(Long id);


}
