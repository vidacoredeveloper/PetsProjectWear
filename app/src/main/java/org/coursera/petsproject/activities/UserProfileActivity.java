package org.coursera.petsproject.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.coursera.petsproject.R;
import org.coursera.petsproject.activities.Interfaces.IUserProfileActivity;
import org.coursera.petsproject.rest.adapter.ResponseAdapter;
import org.coursera.petsproject.rest.interfaces.IEndPoint;
import org.coursera.petsproject.rest.model.PetUserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity implements IUserProfileActivity {

    private EditText tietUsetUPA;

    private String id;
    private String name;
    private String photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        initializeComponents();
    }

    private void initializeComponents(){
        tietUsetUPA = (EditText) findViewById(R.id.tietUsetUPA);
        id = "";
        name = "";
        photo = "";
    }

    @Override
    public void guardarUsuario(View view) {
        ResponseAdapter responseAdapter = new ResponseAdapter();
        Gson gson = responseAdapter.buildsGSonDeserializeUser();
        IEndPoint iEndPoint = responseAdapter.instagramEstablishConnection(gson);
        Call<PetUserResponse> petResponseCall = iEndPoint.getPetUSer(tietUsetUPA.getText().toString());

        petResponseCall.enqueue(new Callback<PetUserResponse>() {
            @Override
            public void onResponse(Call<PetUserResponse> call, Response<PetUserResponse> response) {
                PetUserResponse petResponse = response.body();
                id = petResponse.getPet().getIdPet();
                name = petResponse.getPet().getNamePet();
                photo = petResponse.getPet().getURLPhotoPet();

                goMainActivity();
            }

            @Override
            public void onFailure(Call<PetUserResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Problema al cargar las fotos del perfil", Toast.LENGTH_LONG).show();
                Log.e("Fallo Conexión", t.toString());
            }
        });
    }

    private void goMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        if( (!id.isEmpty()) && (!name.isEmpty()) && (!photo.isEmpty()) ) {
            intent.putExtra("num", "1");
            intent.putExtra("id_u", id);
            intent.putExtra("name_u", name);
            intent.putExtra("photo_u", photo);
        }
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goMainActivity();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void mostar(String uno, String dos, String tres) {
        Toast.makeText(getBaseContext(),
                "ID : " + uno + "\n" +
                "NAME : " + dos + "\n" +
                "PHOTO : " + tres,
                Toast.LENGTH_LONG).show();
    }
}
