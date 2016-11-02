package org.coursera.petsproject.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.coursera.petsproject.activities.Interfaces.IMainActivity;
import org.coursera.petsproject.R;
import org.coursera.petsproject.adapters.ViewPagerAdapter;
import org.coursera.petsproject.database.Interactor;
import org.coursera.petsproject.firebase.adapters.AdapterDataUser;
import org.coursera.petsproject.firebase.interfaces.EndPoint;
import org.coursera.petsproject.firebase.model.PetConfiguration;
import org.coursera.petsproject.firebase.model.ResponseUser;
import org.coursera.petsproject.fragments.PetFragment;
import org.coursera.petsproject.fragments.ProfilePetFragment;
import org.coursera.petsproject.model.Pet;
import org.coursera.petsproject.model.User;
import org.coursera.petsproject.rest.model.PetGram;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements IMainActivity{

    //Atibutos de la actividad.
    public static ArrayList<Pet> petsList;
    public static Interactor interactor;
    public static PetGram petGram;
    public static PetGram user;
    private PetConfiguration petConfiguration;
    private Toolbar toolbar;
    private TabLayout tabBarAM;
    private ViewPager vpPetAM;
    private SharedPreferences usuarioConfigurado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
    }

    /**
     * Método que permite inicializar los componentes correspondientes a la actividad.
     */
    public void initializeComponents () {
        usuarioConfigurado = getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE);
        petGram = new PetGram();
        user = new PetGram();
        petConfiguration = new PetConfiguration();
        getParametersPetagram(petGram);
        getParametersUser(user);
        getPetConfiguration(petConfiguration);
        interactor = new Interactor(getBaseContext());
        establishToolbar();
        setUPViewPager();
    }

    @Override
    public void getParametersPetagram(PetGram pet){
        if(getIntent().getExtras() != null && getIntent().getExtras().getString("num").equals("2")){
            String idPet = getIntent().getExtras().getString("id");
            String namePet = getIntent().getExtras().getString("name");
            String photoPet = getIntent().getExtras().getString("photo");
            if((!idPet.isEmpty()) && (!namePet.isEmpty()) && (!photoPet.isEmpty())) {
                pet.setIdPet(idPet);
                pet.setNamePet(namePet);
                pet.setURLPhotoPet(photoPet);
            }
        }
    }

    @Override
    public void getParametersUser(PetGram pet){
        if(getIntent().getExtras() != null && getIntent().getExtras().getString("num").equals("1")){
            String idPet = getIntent().getExtras().getString("id_u");
            String namePet = getIntent().getExtras().getString("name_u");
            String photoPet = getIntent().getExtras().getString("photo_u");

            if((!idPet.isEmpty()) && (!namePet.isEmpty()) && (!photoPet.isEmpty())) {
                pet.setIdPet(idPet);
                pet.setNamePet(namePet);
                pet.setURLPhotoPet(photoPet);
            }
        }
    }

    @Override
    public void getPetConfiguration(PetConfiguration pet) {
        if(usuarioConfigurado.contains("key")) {
            String k = usuarioConfigurado.getString("key", "no existe la variable");
            String d = usuarioConfigurado.getString("dispositivo", "no existe la variable");
            pet.setKeyPet(k);
            pet.setDevicePet(d);
        }
    }

    /**
     * Método que asigna la barra de herramientas a la vista.
     */
    public void establishToolbar() {
        toolbar = (Toolbar) findViewById(R.id.tbPetAM);
        tabBarAM = (TabLayout) findViewById(R.id.tabBarAM);
        vpPetAM = (ViewPager) findViewById(R.id.vpPetAM);

        if(toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    /**
     * Método que permite agregar los fragmentos a la lista.
     * @return fragments, lista de fragmentos.
     */
    @Override
    public ArrayList<Fragment> addFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new PetFragment());
        fragments.add(new ProfilePetFragment());

        return fragments;
    }

    /**
     * Método que permite inicializar el viewPager para el control de los fragmentos.
     */
    @Override
    public void setUPViewPager() {
        vpPetAM.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), addFragments()));
        tabBarAM.setupWithViewPager(vpPetAM);

        tabBarAM.getTabAt(0).setIcon(R.drawable.icon_dog_house);
        tabBarAM.getTabAt(1).setIcon(R.drawable.icon_dog);
    }

    /**
     * Método que genera un menu de opciones.
     * @param menu, que se va a mostrar en la vista.
     * @return true, si se genero correctamente.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    /**
     * Método que controla las accione de los diferentes elementos que hacen parte del menu de opciones.
     * @param item, elemento del menu seleccionado por el usuario.
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mContacto:

                goContact();

                break;

            case R.id.mAcercaDe:

                goAbout();

                break;

            case R.id.mFavorites:

                goFavoritePets();

                break;

            case R.id.mConfigurarCuenta:

                goConfiguration();

                break;

            case R.id.mBuscarPerfil:

                goBuscarPerfil();

                break;

            case R.id.mRecibirNotificaciones:

                recibirNotificaciones();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Método que nos permite ingresar a la vista de mascotas favoritas.
     */
    @Override
    public void goFavoritePets() {
        Intent intent = new Intent(this, PetLikeActivity.class);
        startActivity(intent);
    }

    /**
     * Método que nos permite ingresar a la vista de mascotas favoritas.
     */
    @Override
    public void goAbout() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    /**
     * Método que nos permite ingresar a la vista de mascotas favoritas.
     */
    @Override
    public void goContact() {
        Intent intent = new Intent(this, UserEmailActivity.class);
        startActivity(intent);
    }

    /**
     * Método que nos permite ingresar a la vista de configuración de cuenta.
     */
    @Override
    public void goConfiguration() {
        if(usuarioConfigurado.contains("key")) {
            Intent intent = new Intent(this, CurrentConfigurationActivity.class);
            intent.putExtra("key_u", petConfiguration.getKeyPet());
            intent.putExtra("dis_u", petConfiguration.getDevicePet());
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(this, UserProfileActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Método que nos permite ingresar a la vista para buscar un nuevo perfil.
     */
    @Override
    public void goBuscarPerfil() {
        Intent intent = new Intent(this, ConfigurationActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Método que nos permite guadar los datos en la base de datos firebase.
     */
    @Override
    public void recibirNotificaciones() {
        if(!user.getIdPet().isEmpty() || user != null) {
            String token = FirebaseInstanceId.getInstance().getToken();
            String userNot = user.getIdPet();
            AdapterDataUser adapterDataUser = new AdapterDataUser();
            EndPoint endPoint = adapterDataUser.establecerConexionRest();
            final Call<ResponseUser> responseUserCall = endPoint.registrarUsuario(token, userNot);
            responseUserCall.enqueue(new Callback<ResponseUser>() {
                @Override
                public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                    ResponseUser responseUser = response.body();
                    crearSharedPreferences(responseUser.getId() + "", responseUser.getIdDispositivo()
                            + "");
                }

                @Override
                public void onFailure(Call<ResponseUser> call, Throwable t) {
                    Snackbar.make(null, "No hay un usuario configurado.", Snackbar.LENGTH_SHORT)
                            .show();
                }
            });
        }
        else {
            Snackbar.make(null, "Configura primero tu cuenta de usuario.", Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void crearSharedPreferences(String key, String dispositivo) {

        SharedPreferences.Editor editor = usuarioConfigurado.edit();

        editor.putString("key", key);
        editor.putString("dispositivo", dispositivo);

        editor.commit();
    }
}
