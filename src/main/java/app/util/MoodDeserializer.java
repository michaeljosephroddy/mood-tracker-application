package app.util;

import app.model.Mood;
import app.model.MoodType;
import com.google.gson.*;

import java.lang.reflect.Type;

public class MoodDeserializer implements JsonDeserializer<Mood> {

    @Override
    public Mood deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // Extract and validate the emotion field
        String emotionString = jsonObject.get("emotion").getAsString();
        MoodType emotion;
        try {
            emotion = MoodType.valueOf(emotionString.toUpperCase()); // Convert to uppercase for case-insensitive
                                                                     // matching
        } catch (IllegalArgumentException e) {
            throw new JsonParseException("Invalid emotion: " + emotionString);
        }

        // Extract the intensity field
        int intensity = jsonObject.get("intensity").getAsInt();

        return new Mood(emotion, intensity);
    }
}