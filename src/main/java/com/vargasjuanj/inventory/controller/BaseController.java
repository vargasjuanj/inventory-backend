package com.vargasjuanj.inventory.controller;

import com.vargasjuanj.inventory.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin  (origins = "*", methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class BaseController<E, S extends IBaseService<E>> {
	@Autowired	
	protected S service;
	@GetMapping("/")
	public ResponseEntity<?> getAll(){
		return service.getAll();
	}
	@GetMapping("")
	public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value =  "size", defaultValue = "10") int size){
			return service.getAll(page, size);
	}

	@GetMapping("/{id}") // Este metodo no puede ser llamada si es sobreescrito, ya que deberia tener parametros distintos
	public ResponseEntity<?> getOne(@PathVariable Long id) {
			return service.getOne(id);
	}


	@PostMapping("/")
	public ResponseEntity<?> post(@RequestBody E entidadForm) {
			return service.save(entidadForm);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> put(@PathVariable Long id, @RequestBody E entidadForm) {
			return service.update(id, entidadForm);
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
			return service.delete(id);
	}	
	
	
}
