package com.example.leaguemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://leaguemanager-55cc8-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onLogin(View view){
        EditText lemail=findViewById(R.id.lemail);
        EditText lpassword=findViewById(R.id.lpassword);

        String semail= lemail.getText().toString();
        String spassword=lpassword.getText().toString();

        if(semail.isEmpty()||spassword.isEmpty()){
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }else{
            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(semail)) {
                        String userPassword = snapshot.child(semail).child("password").getValue(String.class);
                        if (userPassword.equals(spassword)) {
                            Toast.makeText(Login.this, "Succesfully Logged In", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this,Dashboard.class);
                            intent.putExtra("email",semail);
                            startActivity(intent);

                        } else {
                            Toast.makeText(Login.this, "Invalid credentials !! please try again", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }

    public void signupRedirect(View view){
        Intent intent = new Intent(Login.this,MainActivity.class);
        startActivity(intent);
    }
}