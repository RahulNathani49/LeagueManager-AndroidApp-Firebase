package com.example.leaguemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://leaguemanager-55cc8-default-rtdb.firebaseio.com/");

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner mySpinner=(Spinner) findViewById(R.id.teams);
        ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.teams));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
    }
    public void registerUser(View view) {
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        EditText cpassword = findViewById(R.id.cpassword);
        EditText name = findViewById(R.id.name);
        String semail = email.getText().toString();
        String spassword = password.getText().toString();
        String scpassword = cpassword.getText().toString();
        String sname = name.getText().toString();
        Spinner teams = findViewById(R.id.teams);
        String teamselected = teams.getSelectedItem().toString();
        if(sname.isEmpty() || semail.isEmpty() || spassword.isEmpty() || scpassword.isEmpty() || teamselected.isEmpty() || teamselected==null){
            Toast.makeText(this, "All Fields are required and both passwords must be same", Toast.LENGTH_SHORT).show();
        }
        else{
            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(semail))
                    {
                        Toast.makeText(MainActivity.this,"Email is already Registered",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        int teamcount=0;
                        for(DataSnapshot ds : snapshot.getChildren()) {
                            String team = ds.child("team").getValue(String.class);
                            if(team.equals(teamselected)){teamcount++;}
                        }
                        if(teamcount>=5){
                            Toast.makeText(MainActivity.this,"Team limit exceeds more than 5 members !",Toast.LENGTH_SHORT).show();
                        }else{
                            databaseReference.child("users").child(semail).child("email").setValue(semail);
                            databaseReference.child("users").child(semail).child("name").setValue(sname);
                            databaseReference.child("users").child(semail).child("team").setValue(teamselected);
                            databaseReference.child("users").child(semail).child("password").setValue(spassword);
                            Toast.makeText(MainActivity.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,Dashboard.class);
                            intent.putExtra("email",semail);
                            startActivity(intent);
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }

    public void loginRedirect(View view){
        Intent intent = new Intent(MainActivity.this,Login.class);
        startActivity(intent);
    }
}