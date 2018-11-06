package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
            return;
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        Picasso.with(this)
                .load(sandwich.getImage())
                .into((ImageView) findViewById(R.id.image_iv));

        setTitle(sandwich.getMainName());

        setText(R.id.origin_tv, sandwich.getPlaceOfOrigin());
        setText(R.id.also_known_tv, flatten(sandwich.getAlsoKnownAs()));
        setText(R.id.description_tv, sandwich.getDescription());
        setText(R.id.ingredients_tv, flatten(sandwich.getIngredients()));
    }

    private void setText(int textViewId, String text) {
        TextView textView = findViewById(textViewId);
        textView.setText(text);
    }

    private String flatten(List<String> list) {
        StringBuilder result = new StringBuilder();

        for (String value : list) {
            if (result.length() > 0) {
                result.append(", ");
            }

            result.append(value);
        }

        return result.toString();
    }
}
