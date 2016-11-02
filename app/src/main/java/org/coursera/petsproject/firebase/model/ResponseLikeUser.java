package org.coursera.petsproject.firebase.model;

/**
 * Created by Victor Daniel Cortés Restrepo on 31/10/16.
 */

public class ResponseLikeUser {

    private String id;
    private String id_dispositivo;
    private String id_usuario_instagram;
    private String id_foto_instagram;

    public ResponseLikeUser() {
    }

    public ResponseLikeUser(String id, String id_dispositivo, String id_usuario_instagram, String id_foto_instagram) {
        this.id = id;
        this.id_dispositivo = id_dispositivo;
        this.id_usuario_instagram = id_usuario_instagram;
        this.id_foto_instagram = id_foto_instagram;
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

    public String getIdUsuarioInstagram() {
        return id_usuario_instagram;
    }

    public void setIdUsuarioInstagram(String id_usuario_instagram) {
        this.id_usuario_instagram = id_usuario_instagram;
    }

    public String getIdFotoInstagram() {
        return id_foto_instagram;
    }

    public void setIdFotoInstagram(String id_foto_instagram) {
        this.id_foto_instagram = id_foto_instagram;
    }
}
