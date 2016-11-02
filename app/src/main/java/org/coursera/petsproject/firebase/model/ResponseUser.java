package org.coursera.petsproject.firebase.model;

/**
 * Created by Victor Daniel Cortés Restrepo on 31/10/16.
 */

public class ResponseUser {

    private String id;
    private String id_dispositivo;

    public ResponseUser() {
    }

    public ResponseUser(String id, String id_dispositivo) {
        this.id = id;
        this.id_dispositivo = id_dispositivo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdDispositivo() {
        return id_dispositivo;
    }

    public void setIdDispositivo(String id_dispositivo) {
        this.id_dispositivo = id_dispositivo;
    }
}

