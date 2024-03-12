package com.vargasjuanj.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<E, R extends JpaRepository<E, Long>> implements IBaseService<E> {
	
	@Autowired
	protected R repository;

	@Override
	public ResponseEntity<?> getOne(Long id) throws Exception {
		try {
			// se usa para atrapar un null
			Optional<E> varOptional = repository.findById(id);
			E entity = varOptional.get();
			return new ResponseEntity<>(entity, HttpStatus.OK);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}		
	}

	@Override
	public  ResponseEntity<?>  save(E entityForm) throws Exception {
		try {
			entityForm = repository.save(entityForm);
			return new ResponseEntity<>(entityForm, HttpStatus.OK);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> update(Long id, E entityForm) throws Exception {
		try {
			Optional<E> entityOptional = repository.findById(id);
			E entity = entityOptional.get();
			entity = repository.save(entityForm);
			return new ResponseEntity<>(entity, HttpStatus.OK);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> countPages(int size) throws Exception {
		try {
			Pageable pageable = PageRequest.of(0, size);
			return new ResponseEntity<>(repository.findAll(pageable).getTotalPages(),HttpStatus.OK);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	@Override
	public ResponseEntity<?> getAll(int page, int size) throws Exception {
		
		try {
			Pageable pageable = PageRequest.of(page, size);
			return new ResponseEntity<>(repository.findAll(pageable).getContent(), HttpStatus.OK);

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}
	
	
	public ResponseEntity<?> delete(Long id) throws Exception{
		try {
			if(repository.existsById(id)) {
				repository.deleteById(id);
			}			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}		
		return new ResponseEntity<>(!repository.existsById(id), HttpStatus.OK);
	}

}
