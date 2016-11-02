package org.coursera.petsproject.firebase.model;

/**
 * Created by Victor Daniel Cortés Restrepo on 1/11/16.
 */

public class ResponseNotificacion {

    private String id_usuario_instagram;
    private String id_dispositivo;

    public ResponseNotificacion() {
    }

    public ResponseNotificacion(String id_usuario_instagram, String id_dispositivo) {
        this.id_usuario_instagram = id_usuario_instagram;
        this.id_dispositivo = id_dispositivo;
    }

    public String getId_usuario_instagram() {
        return id_usuario_instagram;
    }

    public void setId_usuario_instagram(String id_usuario_instagram) {
        this.id_usuario_instagram = id_usuario_instagram;
    }

    public String getId_dispositivo() {
        return id_dispositivo;
    }

    public void setId_dispositivo(String id_dispositivo) {
        this.id_dispositivo = id_dispositivo;
    }
}
