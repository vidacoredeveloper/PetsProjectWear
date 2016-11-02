package org.coursera.petsproject.rest.model;

/**
 * Created by Victor Daniel Cortés Restrepo on 1/11/16.
 */

public class LikeResponse {

    private String code;

    public LikeResponse() {
    }

    public LikeResponse(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
