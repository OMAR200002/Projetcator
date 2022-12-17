package com.example.projet_v2;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class listeContributorRecyclerViewAdapter extends RecyclerView.Adapter<listeContributorRecyclerViewAdapter.MyViewHolder>{

    ArrayList<Utilisateur> contributeurs;
    Context context;
    public listeContributorRecyclerViewAdapter(Context context, ArrayList<Utilisateur> contributeurs){
        this.contributeurs=contributeurs;
        this.context=context;

    }
    @NonNull
    @Override
    public listeContributorRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.element_contribution,parent,false);

        return new listeContributorRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull listeContributorRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.texte1.setText(contributeurs.get(position).getLogin());
        holder.texte2.setText(contributeurs.get(position).getEmail());

    }

    @Override
    public int getItemCount() {
        return contributeurs.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView texte1,texte2;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            texte1=itemView.findViewById(R.id.contributor);
            texte2=itemView.findViewById(R.id.githublink);

        }
    }
}

