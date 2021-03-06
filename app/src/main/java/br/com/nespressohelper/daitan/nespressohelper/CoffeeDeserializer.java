package br.com.nespressohelper.daitan.nespressohelper;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by estrela on 12/26/17.
 */

public class CoffeeDeserializer implements JsonDeserializer<Coffee> {
    @Override
    public Coffee deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        final JsonObject jsonObject = json.getAsJsonObject();

        JsonElement nameElement = jsonObject.get("name");
        final String name = nameElement.getAsString();

        final int capsules = jsonObject.get("capsules").getAsInt();
        final int mainWater = jsonObject.get("mainWater").getAsInt();
        final int mainMilk = jsonObject.get("mainMilk").getAsInt();

        final int intensity = jsonObject.get("intensity").getAsInt();

        final String description = jsonObject.get("description").getAsString();

        final Coffee coffee = new Coffee();
        coffee.setName(name);
        coffee.setCapsules(capsules);
        coffee.setBars1(mainWater);
        coffee.setBars2(mainMilk);
        coffee.setIntensity(intensity);
        coffee.setDescription(description);

        return coffee;
    }
}
