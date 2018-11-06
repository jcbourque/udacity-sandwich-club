package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich;

        try {
            JSONObject data = new JSONObject(json);
            JSONObject name = data.getJSONObject("name");

            sandwich = new Sandwich();

            sandwich.setMainName(name.getString("mainName"));
            sandwich.setAlsoKnownAs(toList(name.getJSONArray("alsoKnownAs")));
            sandwich.setPlaceOfOrigin(data.getString("placeOfOrigin"));
            sandwich.setDescription(data.getString("description"));
            sandwich.setImage(data.getString("image"));
            sandwich.setIngredients(toList(data.getJSONArray("ingredients")));

        } catch (JSONException e) {
            e.printStackTrace();
            sandwich = null;
        }

        return sandwich;
    }

    private static List<String> toList(JSONArray array) throws JSONException {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            list.add(array.getString(i));
        }

        return list;
    }
}
