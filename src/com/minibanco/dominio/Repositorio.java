package com.minibanco.dominio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Repositorio<T, ID> {
    private final Map<ID, T> datos = new HashMap<>();
    private final Function<T, ID> extractorId;

    public Repositorio(Function<T, ID> extractorId) {
        this.extractorId = extractorId;
    }

    public void guardar(T t) {
        ID id = extractorId.apply(t);
        datos.put(id, t);
    }

    public T buscarPorId(ID id) {
        return datos.get(id);
    }

    public List<T> todos(){
        return new ArrayList<>(datos.values());
    }
}
