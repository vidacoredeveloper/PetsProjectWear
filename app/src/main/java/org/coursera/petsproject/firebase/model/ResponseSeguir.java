package org.coursera.petsproject.firebase.model;

/**
 * Created by Victor Daniel Cortés Restrepo on 7/11/16.
 */

public class ResponseSeguir {

    private String key;
    private String valor;

    public ResponseSeguir() {
    }

    public ResponseSeguir(String key, String valor) {
        this.key = key;
        this.valor = valor;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return valor;
    }

    public void setValue(String valor) {
        this.valor = valor;
    }
}
