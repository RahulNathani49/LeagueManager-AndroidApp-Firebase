package com.example.leaguemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://leaguemanager-55cc8-default-rtdb.firebaseio.com/");
    DatabaseReference teams = databaseReference.child("teams");
    String email;
    TextView user;
    TextView name;
    TextView teamname;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<Matches> matchlist;
    String myteam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
         email = getIntent().getStringExtra("email");
         user = findViewById(R.id.username);
         name = findViewById(R.id.nname);
         teamname = findViewById(R.id.tteam);
         recyclerView = findViewById(R.id.rec);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));
         matchlist = new ArrayList<Matches>();
         myAdapter = new MyAdapter(this,matchlist);
         recyclerView.setAdapter(myAdapter);



         databaseReference.child("teams").addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Matches match = dataSnapshot.getValue(Matches.class);
                        matchlist.add(match);
                 }
                 myAdapter.notifyDataSetChanged();
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });

         databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(email))
                {
                    String team = snapshot.child(email).child("team").getValue(String.class);
                    String names = snapshot.child(email).child("name").getValue(String.class);
                    String usname = snapshot.child(email).child("email").getValue(String.class);
                    user.setText("Welcome "+ usname +",");
                    name.setText("YourName :"+names);
                    teamname.setText("You are in Team : "+team);
                    myteam = team;
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void goBack(View view){
        Intent intent = new Intent(Dashboard.this,Login.class);
        startActivity(intent);
    }
    public void yourMatches(View view){
        databaseReference.child("teams").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                matchlist.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String team1 = dataSnapshot.child("t1").getValue(String.class);
                    String team2 = dataSnapshot.child("t2").getValue(String.class);
                    if(team1.equals(myteam) || team2.equals(myteam)){
                        Matches match = dataSnapshot.getValue(Matches.class);
                        matchlist.add(match);
                    }
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void allMatches(View view){
        databaseReference.child("teams").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Matches match = dataSnapshot.getValue(Matches.class);
                    matchlist.add(match);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}