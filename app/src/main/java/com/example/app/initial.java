package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;



public class initial extends AppCompatActivity {
    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        button = findViewById(R.id.alert);

        EditText phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        EditText heightEditText = findViewById(R.id.heightEditText);
        EditText weightEditText = findViewById(R.id.weightEditText);
        EditText bloodEditText = findViewById(R.id.bloodEditText);
        EditText nameEditText =findViewById(R.id.nameEditText);
        EditText ageEditText = findViewById((R.id.ageEditText));

        Button nextButton = findViewById(R.id.start);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneNumberEditText.getText().toString();
                String height = heightEditText.getText().toString();
                String weight = weightEditText.getText().toString();
                String blood = bloodEditText.getText().toString();
                String name = nameEditText.getText().toString();
                String age= ageEditText.getText().toString();

                Intent intent = new Intent(initial.this, MainActivity.class);
                intent.putExtra("phone_number", phoneNumber);
                intent.putExtra("height", height);
                intent.putExtra("weight", weight);
                intent.putExtra("blood", blood);
                intent.putExtra("name",name);
                intent.putExtra("age",age);

                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(initial.this, PopupActivity.class);
                startActivity(intent);
            }
        });
    }
}
