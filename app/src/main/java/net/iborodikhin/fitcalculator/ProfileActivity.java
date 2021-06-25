package net.iborodikhin.fitcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.iborodikhin.fitcalculator.database.ProfileDbHelper;
import net.iborodikhin.fitcalculator.model.Profile;
import net.iborodikhin.fitcalculator.viewmodel.ProfileAdapter;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final ImageButton button = findViewById(R.id.save);
        final TextView height = findViewById(R.id.height);
        final TextView age = findViewById(R.id.age);
        final TextView weight = findViewById(R.id.weight);
        final RadioButton gender_is_male = findViewById(R.id.gender_is_male);
        final RadioButton gender_is_female = findViewById(R.id.gender_is_female);
        final Spinner activity = findViewById(R.id.activity);

        try {
            Profile profile = readProfile();
            height.setText(String.valueOf(profile.getHeight()));
            age.setText(String.valueOf(profile.getAge()));
            weight.setText(String.valueOf(profile.getWeight()));
            gender_is_male.setChecked(profile.isMale());
            gender_is_female.setChecked(profile.isFemale());
            activity.setSelection(profile.getActivityId());
        } catch (Exception e) {

        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String weightText = weight.getText().toString();
                String heightText = height.getText().toString();
                String ageText = age.getText().toString();
                boolean is_male = gender_is_male.isChecked();
                boolean is_female = gender_is_female.isChecked();
                long activityId = activity.getSelectedItemId();

                if (heightText.length() != 3) {
                    showAlert("Укажите рост от 100 до 999 см");

                    return;
                }

                if (weightText.length() < 2 || weightText.length() > 3) {
                    showAlert("Укажите вес от 10 до 999 кг");

                    return;
                }

                if (ageText.length() < 2 || ageText.length() > 2) {
                    showAlert("Укажите возраст от 10 до 99 лет");

                    return;
                }

                Profile profile = new Profile();
                profile
                        .setHeight(Integer.parseInt(heightText))
                        .setAge(Integer.parseInt(ageText))
                        .setWeight(Integer.parseInt(weightText))
                        .setMale(is_male)
                        .setFemale(is_female)
                        .setActivityId((int) activityId);

                writeProfile(profile);

                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });
    }

    protected void writeProfile(Profile profile) {
        ProfileDbHelper helper = new ProfileDbHelper(getBaseContext());
        helper.save(profile);
    }

    protected Profile readProfile() throws Exception {
        ProfileDbHelper helper = new ProfileDbHelper(getBaseContext());

        return helper.read();
    }

    protected void showAlert(String text) {
        Toast.makeText(ProfileActivity.this, text, Toast.LENGTH_LONG).show();
    }
}
