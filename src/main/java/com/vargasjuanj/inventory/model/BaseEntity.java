package com.vargasjuanj.inventory.model;


import jakarta.persistence.*;

import java.io.Serializable;

@MappedSuperclass
public abstract class BaseEntity implements Serializable { //Se hereda tmb serializable. La interfaz Serializable en Java permite que los objetos de una clase sean convertidos en una secuencia de bytes para que puedan ser almacenados, transmitidos o clonados., sino se produce una excepcion

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
