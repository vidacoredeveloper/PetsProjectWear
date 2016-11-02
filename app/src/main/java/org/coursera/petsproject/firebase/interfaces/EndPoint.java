package org.coursera.petsproject.firebase.interfaces;

import org.coursera.petsproject.firebase.Constant;
import org.coursera.petsproject.firebase.model.ResponseLikeUser;
import org.coursera.petsproject.firebase.model.ResponseNotificacion;
import org.coursera.petsproject.firebase.model.ResponseUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Victor Daniel Cortés Restrepo on 31/10/16.
 */

public interface EndPoint {

    @FormUrlEncoded
    @POST(Constant.KEY_POST_ID_TOKEN)
    public Call<ResponseUser> registrarUsuario(@Field("id_dispositivo") String token, @Field("id_usuario_instagram") String user);

    @FormUrlEncoded
    @POST(Constant.KEY_POST_REGISTRAR_LIKE)
    public Call<ResponseLikeUser> registrarLike(@Field("id_dispositivo") String token, @Field("id_usuario_instagram") String user, @Field("id_foto_instagram") String foto);

    @GET(Constant.KEY_GET_NOTIFICACION)
    public Call<ResponseNotificacion> notificacionLike(@Path("id_usuario_instagram") String usuario);
}
