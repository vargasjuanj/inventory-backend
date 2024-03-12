package com.vargasjuanj.inventory.service;

import org.springframework.http.ResponseEntity;

public interface IBaseService<E> {
	// Se modifica el retorno de E a ResponseEntity<?> para que al implementar iCategoryService coincidan los tipos de retornos de los metodos y se pueda sobreescribir tranquilamente.
	public ResponseEntity<?> getOne(Long id) throws Exception;
	
	public ResponseEntity<?> save(E entityForm) throws Exception;
	
	public  ResponseEntity<?> update(Long id, E entityForm) throws Exception;
	
	public  ResponseEntity<?>  countPages(int size) throws Exception;
	
	public  ResponseEntity<?> getAll(int page, int size) throws Exception;
	
	public  ResponseEntity<?> delete(Long id) throws Exception;
}

