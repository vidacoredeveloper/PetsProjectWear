package org.coursera.petsproject.firebase.model;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import org.coursera.petsproject.activities.MainActivity;
import org.coursera.petsproject.firebase.adapters.AdapterDataUser;
import org.coursera.petsproject.firebase.interfaces.EndPoint;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Victor Daniel Cortés Restrepo on 7/11/16.
 */

public class RegistrarSeguir extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        String ACCION_KEY = "FOLLOW";
        String accion = intent.getAction();

        if(ACCION_KEY.equals(accion)) {
            Toast.makeText(context, "FOLLOW", Toast.LENGTH_LONG);
            verLike(context);
        }
    }

    public void verLike(final Context context){
        String id_user = MainActivity.usuarioConfigurado.getString("idPet", "no existe la variable");
        AdapterDataUser adapterDataUser = new AdapterDataUser();
        EndPoint endPoint = adapterDataUser.establecerConexionRest();
        final Call<ResponseLikeUser> responseSeguirCall = endPoint.getUserLike(id_user);
        responseSeguirCall.enqueue(new Callback<ResponseLikeUser>() {
            @Override
            public void onResponse(Call<ResponseLikeUser> call, Response<ResponseLikeUser> response) {
                ResponseLikeUser responseLikeUser = response.body();

                registrarSeguir(responseLikeUser.getId_usuario_local(), responseLikeUser.getId_usuario_instagram(), context);
                Toast.makeText(context, "PAILA", Toast.LENGTH_LONG);
            }

            @Override
            public void onFailure(Call<ResponseLikeUser> call, Throwable t) {
                Toast.makeText(context, "PAILA", Toast.LENGTH_LONG);
            }
        });
    }

    public void registrarSeguir(String user, String instagram, final Context context){
        AdapterDataUser adapterDataUser = new AdapterDataUser();
        EndPoint endPoint = adapterDataUser.establecerConexionRest();
        final Call<ResponseSeguir> responseSeguirCall = endPoint.registrarSeguir(instagram + "_" + user, "FOLLOW");
        responseSeguirCall.enqueue(new Callback<ResponseSeguir>() {
            @Override
            public void onResponse(Call<ResponseSeguir> call, Response<ResponseSeguir> response) {
                ResponseSeguir responseSeguir = response.body();
            }

            @Override
            public void onFailure(Call<ResponseSeguir> call, Throwable t) {
                Toast.makeText(context, "PAILA", Toast.LENGTH_LONG);
            }
        });
    }
}
