package org.coursera.petsproject.adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.coursera.petsproject.R;
import org.coursera.petsproject.activities.MainActivity;
import org.coursera.petsproject.firebase.adapters.AdapterDataUser;
import org.coursera.petsproject.firebase.interfaces.EndPoint;
import org.coursera.petsproject.firebase.model.ResponseLikeUser;
import org.coursera.petsproject.firebase.model.ResponseNotificacion;
import org.coursera.petsproject.firebase.model.ResponseUser;
import org.coursera.petsproject.rest.adapter.ResponseAdapter;
import org.coursera.petsproject.rest.interfaces.IEndPoint;
import org.coursera.petsproject.rest.model.LikeResponse;
import org.coursera.petsproject.rest.model.PetGram;
import org.coursera.petsproject.rest.model.PetUserResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Victor Daniel Cortés Restrepo on 23/09/16.
 */

public class PhotoPetAdapter extends RecyclerView.Adapter<PhotoPetAdapter.PhotoPetViewHolder> {

    //Atributos de la clase.
    private ArrayList<PetGram> pets;
    private Context context;

    /**
     * Método contructor de la clase.
     * @param pets, lista de mascotas.
     */
    public PhotoPetAdapter(ArrayList<PetGram> pets) {
        this.pets = pets;
    }

    /**
     * Método que establece la asociacion del layout con la lógica.
     * @param parent, padre del recycler view.
     * @param viewType, tipo del view.
     * @return el view holder generado en la clase estatica.
     */
    @Override
    public PhotoPetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Asociamos el layout al recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_view_photo_pet, parent, false);
        context = parent.getContext();
        return new PhotoPetViewHolder(view);
    }

    /**
     * Método que permite asociar cada elemento de la lista con los views.
     * @param photoPetViewHolder, contenedor de los views.
     * @param position, posicion actual del elemento de la lista.
     */
    @Override
    public void onBindViewHolder(PhotoPetViewHolder photoPetViewHolder, int position) {
        final PetGram pet = pets.get(position);
        Picasso .with(context)
                .load(pet.getURLPhotoPet())
                .placeholder(R.drawable.img_pet_10)
                .into(photoPetViewHolder.ivPhotoPetLCVPP);
        photoPetViewHolder.tvRankPhotoPetLCVPP.setText(String.valueOf(pet.getLikesPet()));

        photoPetViewHolder.ivGoldenBonePhotoPetLCVPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String token = FirebaseInstanceId.getInstance().getToken();
                String user = pet.getIdPet();
                String foto = pet.getURLPhotoPet();
                AdapterDataUser adapterDataUser = new AdapterDataUser();
                EndPoint endPoint = adapterDataUser.establecerConexionRest();
                final Call<ResponseLikeUser> responseUserCall = endPoint.registrarLike(token, user, foto);
                responseUserCall.enqueue(new Callback<ResponseLikeUser>() {
                    @Override
                    public void onResponse(Call<ResponseLikeUser> call, Response<ResponseLikeUser> response) {
                        ResponseLikeUser responseUser = response.body();
                        enviarNotificacion(responseUser);
                        //darLikeInstagram(MainActivity.petGram.getIdPet());
                    }

                    @Override
                    public void onFailure(Call<ResponseLikeUser> call, Throwable t) {

                    }
                });

                Snackbar.make(view, pet.getNamePet() + " es mascota favoria.", Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }

    /**
     * Método que permite consultar la cantidad de elementos que contiene mi lista.
     * @return pets.size, tamanho de la lista.
     */
    @Override
    public int getItemCount() {
        return pets.size();
    }

    /**
     * Clase que permite generar la lógica que le va a dar vida a los views.
     */
    public static class PhotoPetViewHolder extends RecyclerView.ViewHolder {

        //Views correspondientes al card view.
        private ImageView ivPhotoPetLCVPP;
        private TextView tvRankPhotoPetLCVPP;
        private ImageView ivGoldenBonePhotoPetLCVPP;

        /**
         * Método constructor por defecto de la clase.
         * @param itemView, contendor de todos los views.
         */
        public PhotoPetViewHolder(View itemView) {
            super(itemView);

            ivPhotoPetLCVPP = (ImageView) itemView.findViewById(R.id.ivPhotoPetLCVPP);
            tvRankPhotoPetLCVPP = (TextView) itemView.findViewById(R.id.tvRankPhotoPetLCVPP);
            ivGoldenBonePhotoPetLCVPP = (ImageView) itemView.findViewById(R.id.ivGoldenBonePhotoPetLCVPP);
        }
    }

    public void enviarNotificacion(ResponseLikeUser responseUser) {
        AdapterDataUser adapterDataUser = new AdapterDataUser();
        EndPoint endPoint = adapterDataUser.establecerConexionRest();
        Call<ResponseNotificacion> usuarioResponseCall = endPoint.notificacionLike(responseUser.getIdUsuarioInstagram());
        usuarioResponseCall.enqueue(new Callback<ResponseNotificacion>() {
            @Override
            public void onResponse(Call<ResponseNotificacion> call, Response<ResponseNotificacion> response) {
                ResponseNotificacion responseNotificacion = response.body();
                Log.d("NOT_USER", responseNotificacion.getId_usuario_instagram());
                Log.d("NOT_DESP", responseNotificacion.getId_dispositivo());
            }

            @Override
            public void onFailure(Call<ResponseNotificacion> call, Throwable t) {

            }
        });
    }

    public void darLikeInstagram(String photo){
        ResponseAdapter responseAdapter = new ResponseAdapter();
        Gson gson = responseAdapter.buildsGSonDeserializeUser();
        IEndPoint iEndPoint = responseAdapter.instagramEstablishConnection(gson);
        Call<LikeResponse> likeResponseCall = iEndPoint.setLikes(photo);

        likeResponseCall.enqueue(new Callback<LikeResponse>() {
            @Override
            public void onResponse(Call<LikeResponse> call, Response<LikeResponse> response) {
                Snackbar.make(null, "Diste Like", Snackbar.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onFailure(Call<LikeResponse> call, Throwable t) {
                Snackbar.make(null, "Error Like", Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
