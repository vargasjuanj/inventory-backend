package com.vargasjuanj.inventory.response;


import java.util.ArrayList;
import java.util.List;

public class Respuesta<E> extends ResponseRest {

    private List<E> resultados = new ArrayList<>();
   // pongo el get y set porque no esta tomando lombok

    public List<E> getResultados() {
        return resultados;
    }

    public void setResultados (List<E> resultados) {
        this.resultados = resultados;
    }
}
