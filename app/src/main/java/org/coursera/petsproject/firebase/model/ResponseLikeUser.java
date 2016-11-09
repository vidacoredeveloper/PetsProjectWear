package org.coursera.petsproject.firebase.model;

/**
 * Created by Victor Daniel Cortés Restrepo on 31/10/16.
 */

public class ResponseLikeUser {

    private String id_usuario_local;
    private String id_usuario_instagram;
    private String id_foto_instagram;

    public ResponseLikeUser() {
    }

    public ResponseLikeUser(String id_usuario_local, String id_usuario_instagram, String id_foto_instagram) {
        this.id_usuario_local = id_usuario_local;
        this.id_usuario_instagram = id_usuario_instagram;
        this.id_foto_instagram = id_foto_instagram;
    }

    public String getId_usuario_local() {
        return id_usuario_local;
    }

    public void setId_usuario_local(String id_usuario_local) {
        this.id_usuario_local = id_usuario_local;
    }

    public String getId_usuario_instagram() {
        return id_usuario_instagram;
    }

    public void setId_usuario_instagram(String id_usuario_instagram) {
        this.id_usuario_instagram = id_usuario_instagram;
    }

    public String getId_foto_instagram() {
        return id_foto_instagram;
    }

    public void setId_foto_instagram(String id_foto_instagram) {
        this.id_foto_instagram = id_foto_instagram;
    }
}
