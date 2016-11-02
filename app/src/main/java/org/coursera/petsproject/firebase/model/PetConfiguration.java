package org.coursera.petsproject.firebase.model;

/**
 * Created by Victor Daniel Cortés Restrepo on 1/11/16.
 */

public class PetConfiguration {

    private String keyPet;
    private String devicePet;

    public PetConfiguration() {
    }

    public PetConfiguration(String keyPet, String devicePet) {
        this.keyPet = keyPet;
        this.devicePet = devicePet;
    }

    public String getKeyPet() {
        return keyPet;
    }

    public void setKeyPet(String keyPet) {
        this.keyPet = keyPet;
    }

    public String getDevicePet() {
        return devicePet;
    }

    public void setDevicePet(String devicePet) {
        this.devicePet = devicePet;
    }
}
