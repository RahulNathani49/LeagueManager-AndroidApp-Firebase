package com.example.leaguemanager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<Matches> matcheslist;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://leaguemanager-55cc8-default-rtdb.firebaseio.com/");
    DatabaseReference teams = databaseReference.child("teams");

    public MyAdapter(Context context, ArrayList<Matches> matcheslist) {
        this.context = context;
        this.matcheslist = matcheslist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.teamdisplayview,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Matches match = matcheslist.get(position);
        holder.date.setText("Match Date : "+match.getDate());
        holder.t1.setText("Team 1 : "+match.getT1());
        holder.t2.setText("Team 2 : "+match.getT2());

    }

    @Override
    public int getItemCount() {
        return matcheslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView t1,t2,date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
            date = itemView.findViewById(R.id.date);
        }
    }
}
