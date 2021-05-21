package com.example.proiect_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    public static final String PREFERENCES_FILE_NAME = "User";

    EditText email;
    EditText password;
    Button login;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.signInButton);
        signUp = findViewById(R.id.signUpButton);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences settingsFile = getSharedPreferences(PREFERENCES_FILE_NAME, 0);
                SharedPreferences.Editor myEditor = settingsFile.edit();
                myEditor.putString("user", email.getText().toString());
                myEditor.putString("password", password.getText().toString());
                myEditor.apply();

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences settingsFile = getSharedPreferences(PREFERENCES_FILE_NAME, 0);

                if(settingsFile.getString("user","null").equals(email.getText().toString()) && settingsFile.getString("password","null").equals(password.getText().toString())){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Incorect mail or password",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
