package com.vargasjuanj.inventory.controller;

import com.vargasjuanj.inventory.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@CrossOrigin  (origins = "*", methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class BaseController<E, S extends IBaseService<E>> {
	@Autowired	
	protected S service;
	
	@GetMapping("")
	@Transactional
	public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value =  "size", defaultValue = "10") int size){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.getAll(page, size));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("{\"Mi mensaje get todos\": \"" + e.getMessage() + "\"}");
		}
	}


	@GetMapping("/{id}") // Este metodo no puede ser llamada si es sobreescrito, ya que deberia tener parametros distintos
	@Transactional
	public ResponseEntity<?> getOne(@PathVariable Long id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.getOne(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \""+e.getMessage()+"\"}");
		}
	}


	@PostMapping("/")
	@Transactional
	public ResponseEntity<?> post(@RequestBody E entidadForm) {		
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(service.save(entidadForm));			
		} catch (Exception e) {			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \""+e.getMessage()+"\"}");						
		}		
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<?> put(@PathVariable Long id, @RequestBody E entidadForm) {		
		try {			
			return ResponseEntity.status(HttpStatus.OK).body(service.update(id, entidadForm));			
		} catch (Exception e) {			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \""+e.getMessage()+"\"}");
		}		
	}


	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("{\"Mi mensaje put\": \"" + e.getMessage() + "\"}");		}

	}	
	
	
}
