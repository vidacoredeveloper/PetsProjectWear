package org.coursera.petsproject.activities.Interfaces;

import android.support.v4.app.Fragment;

import org.coursera.petsproject.firebase.model.PetConfiguration;
import org.coursera.petsproject.rest.model.PetGram;

import java.util.ArrayList;

/**
 * Created by Victor Daniel Cortés Restrepo on 17/10/16.
 */

public interface IMainActivity {

    public ArrayList<Fragment> addFragments();

    public void setUPViewPager();

    public void getParametersPetagram(PetGram pet);

    public void getParametersUser(PetGram pet);

    public void getPetConfiguration(PetConfiguration pet);

    public void goFavoritePets();

    public void goAbout();

    public void goContact();

    public void goConfiguration();

    public void goBuscarPerfil();

    public void recibirNotificaciones();

    public void crearSharedPreferences(String key, String dispositivo);
}
