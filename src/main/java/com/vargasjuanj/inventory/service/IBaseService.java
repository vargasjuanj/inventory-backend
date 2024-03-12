package com.vargasjuanj.inventory.service;

import org.springframework.http.ResponseEntity;

public interface IBaseService<E> {
	public  ResponseEntity<?> getAll();
	public  ResponseEntity<?> getAll(int page, int size);
	public ResponseEntity<?> getOne(Long id);
	public ResponseEntity<?> save(E entityForm);
	public  ResponseEntity<?> update(Long id, E entityForm);
	public  ResponseEntity<?> delete(Long id);
	public  ResponseEntity<?>  countPages(int size);
}

