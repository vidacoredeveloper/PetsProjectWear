package org.coursera.petsproject.rest.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.coursera.petsproject.rest.KeysJson;
import org.coursera.petsproject.rest.model.LikeResponse;
import org.coursera.petsproject.rest.model.PetGram;
import org.coursera.petsproject.rest.model.PetResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Victor Daniel Cortés Restrepo on 1/11/16.
 */

public class LikeDeserializer implements JsonDeserializer<LikeResponse> {
    @Override
    public LikeResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        LikeResponse likeResponse = gson.fromJson(json, LikeResponse.class);
        JsonArray likeResponseData = json.getAsJsonObject().getAsJsonArray(KeysJson.MEDIA_RESPONSE_ARRAY);
        likeResponse.setCode(deserializePetJSon(likeResponseData));
        return likeResponse;
    }

    private String deserializePetJSon(JsonArray petResponseData) {
        JsonObject petObject = petResponseData.get(0).getAsJsonObject();

        JsonObject userObject   = petObject.getAsJsonObject(KeysJson.LIKE_META);
        String code             = userObject.get(KeysJson.LIKE_CODE).getAsString();

        return code;
    }
}
