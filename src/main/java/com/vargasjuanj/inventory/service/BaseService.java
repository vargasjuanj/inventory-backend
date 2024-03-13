package com.vargasjuanj.inventory.service;

import com.vargasjuanj.inventory.response.Respuesta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

public abstract class BaseService<E, R extends JpaRepository<E, Long>> implements IBaseService<E> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected R repository;

	@Transactional(readOnly = true)
	@Override
	public ResponseEntity<?> getAll() {
		Respuesta<E> response = new Respuesta<>();
		try {
			List<E> entities = repository.findAll();
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
	public ResponseEntity<?> getAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<>(repository.findAll(pageable).getContent(), HttpStatus.OK);
	}

	@Transactional(readOnly = true)
	@Override
	public ResponseEntity<?> getOne(Long id) {
		Respuesta<E> response = new Respuesta<>();
		try {
			Optional<E> entityOptional = repository.findById(id);
			E entity = entityOptional.get();
            response.setMetadata("Respuesta Ok", "00", "Respuesta exitosa");
            response.getResultados().add(entity);
            logger.info("Entity: ", entity);
        } catch (Exception e) {
			response.setMetadata("Respuesta nok", "-1", "Error al consultar por id");
			logger.error("Error al consultar por el id " +id, e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public  ResponseEntity<?>  save(E entityForm) {
		Respuesta<E> response = new Respuesta<>();
		try {
			E entity = repository.save(entityForm);
			response.setMetadata("Respuesta Ok", "00", "Respuesta exitosa");
			logger.info("Entity: ", entity);
			response.getResultados().add(entity);
		} catch (Exception e) {
			response.setMetadata("Respuesta nok", "-1", "Error al guardar por id");
			logger.error("Error al guardar " + entityForm, e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> update(Long id, E entityForm) {
		Respuesta<E> response = new Respuesta<>();
		try {
			Optional<E> entityOptional = repository.findById(id);
			E entity = entityOptional.get();
			// Utilizar reflexión para acceder y setear el campo id con el metodo setter, porque si usamos field no va a encontrar el campo id al venir en ull y da excepcion
			Method setIdMethod = entityForm.getClass().getMethod("setId", Long.class);
			setIdMethod.invoke(entityForm, id);
			entity = repository.save(entityForm);
			response.setMetadata("Respuesta Ok", "00", "Respuesta exitosa");
			logger.info("Entity: ", entity);
			response.getResultados().add(entity);
		} catch (Exception e) {
			response.setMetadata("Respuesta nok", "-1", "Error al guardar por id");
			logger.error("Error al guardar " + entityForm, e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public ResponseEntity<?> delete(Long id){
		Respuesta<E> response = new Respuesta<>();
		try {
			if(repository.existsById(id)) {
				repository.deleteById(id);
			}
			response.setMetadata("Respuesta Ok", "00", "Respuesta exitosa");
			logger.info("id: ", id);
			//!repository.existsById(id)
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setMetadata("Respuesta nok", "-1", "Error al eliminar por id");
			logger.error("Error al eliminar por el id " +id, e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@Override
	public ResponseEntity<?> countPages(int size) {
		Pageable pageable = PageRequest.of(0, size);
		return new ResponseEntity<>(repository.findAll(pageable).getTotalPages(),HttpStatus.OK);
	}


}
