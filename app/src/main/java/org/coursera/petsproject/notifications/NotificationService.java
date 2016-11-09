package org.coursera.petsproject.notifications;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.coursera.petsproject.R;
import org.coursera.petsproject.activities.AboutActivity;
import org.coursera.petsproject.activities.MainActivity;
import org.coursera.petsproject.firebase.adapters.AdapterDataUser;
import org.coursera.petsproject.firebase.interfaces.EndPoint;
import org.coursera.petsproject.firebase.model.ResponseLikeUser;
import org.coursera.petsproject.rest.adapter.ResponseAdapter;
import org.coursera.petsproject.rest.interfaces.IEndPoint;
import org.coursera.petsproject.rest.model.PetUserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Victor Daniel Cortés Restrepo on 30/10/16.
 */

public class NotificationService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static final int NOTIFICATION_ID = 001;

    private String nomPerLike = "";
    private String idPerLike = "";
    private String phoPerLike = "";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        enviarNotificacion(remoteMessage);
    }

    private void enviarNotificacion(RemoteMessage message){

        Intent intent = new Intent();
        intent.setAction("FOLLOW");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_full_follow,
                        getString(R.string.texto_seguir), pendingIntent)
                        .build();

        NotificationCompat.WearableExtender wearableExtender =
                new WearableExtender()
                .setHintHideIcon(true)
                .setBackground(BitmapFactory.decodeResource(getResources(), R.drawable.img_bag_wear))
                .setGravity(Gravity.CENTER_VERTICAL);

        Uri sonido = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificacion = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon_dog_house)
                .setContentTitle("Notificacion")
                .setContentText(message.getNotification().getBody())
                .setSound(sonido)
                .setContentIntent(pendingIntent)
                .extend(wearableExtender.addAction(action))
                .extend(wearableExtender.addAction(accederPerfilLike()))
                .extend(wearableExtender.addAction(accederPerfilUser()))
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, notificacion.build());
    }

    private NotificationCompat.Action accederPerfilUser(){
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_full_dog_profile,
                        getString(R.string.texto_notificacion), goDogProfile()).build();

        return action;
    }

    private PendingIntent goDogProfile(){
        String id = MainActivity.usuarioConfigurado.getString("idPet", "no existe la variable");
        String na = MainActivity.usuarioConfigurado.getString("namePet", "no existe la variable");
        String ph = MainActivity.usuarioConfigurado.getString("photoPet", "no existe la variable");

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("num", "2");
        intent.putExtra("id", id);
        intent.putExtra("name", na);
        intent.putExtra("photo", ph);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return pendingIntent;
    }

    private NotificationCompat.Action accederPerfilLike(){
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_full_profile,
                        getString(R.string.texto_perfil_like), goProfileLike()).build();

        return action;
    }

    private PendingIntent goProfileLike(){
        verLike(null);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("num", "2");
        intent.putExtra("id", idPerLike);
        intent.putExtra("name", nomPerLike);
        intent.putExtra("photo", phoPerLike);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return pendingIntent;
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
                getPetUser(responseLikeUser.getId_usuario_local());
            }

            @Override
            public void onFailure(Call<ResponseLikeUser> call, Throwable t) {
                Toast.makeText(context, "PAILA", Toast.LENGTH_LONG);
            }
        });
    }

    public void getPetUser(String user) {
        ResponseAdapter responseAdapter = new ResponseAdapter();
        Gson gson = responseAdapter.buildsGSonDeserializeUser();
        IEndPoint iEndPoint = responseAdapter.instagramEstablishConnection(gson);
        Call<PetUserResponse> petResponseCall = iEndPoint.getPetUSer(user);

        petResponseCall.enqueue(new Callback<PetUserResponse>() {
            @Override
            public void onResponse(Call<PetUserResponse> call, Response<PetUserResponse> response) {
                PetUserResponse petResponse = response.body();
                idPerLike = petResponse.getPet().getIdPet();
                nomPerLike = petResponse.getPet().getNamePet();
                phoPerLike = petResponse.getPet().getURLPhotoPet();
            }

            @Override
            public void onFailure(Call<PetUserResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Problema al cargar las fotos del perfil", Toast.LENGTH_LONG).show();
                Log.e("Fallo Conexión", t.toString());
            }
        });
    }

}
