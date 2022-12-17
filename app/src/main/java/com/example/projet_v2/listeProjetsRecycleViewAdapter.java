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

public class listeProjetsRecycleViewAdapter extends RecyclerView.Adapter<listeProjetsRecycleViewAdapter.MyViewHolder>{
    private RecycleViewInterface recycleViewInterface;
    ArrayList<Projet> plist;
    Context context;
    public listeProjetsRecycleViewAdapter(Context context, ArrayList<Projet> plist,RecycleViewInterface recycleViewInterface){
        this.plist=plist;
        this.context=context;
        this.recycleViewInterface = recycleViewInterface;
    }
    @NonNull
    @Override
    public listeProjetsRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.element_projet,parent,false);

        return new listeProjetsRecycleViewAdapter.MyViewHolder(view, recycleViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull listeProjetsRecycleViewAdapter.MyViewHolder holder, int position) {
        holder.texte1.setText(plist.get(position).getTitre());


    }

    @Override
    public int getItemCount() {
        return plist.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView texte1,texte2;
        public MyViewHolder(@NonNull View itemView,RecycleViewInterface recycleViewInterface) {

            super(itemView);
            texte1=itemView.findViewById(R.id.projetTitre);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recycleViewInterface != null){
                        int pos  = getAdapterPosition();

                        if (pos != RecyclerView.NOT_FOCUSABLE){
                            recycleViewInterface.onItemClick(pos);
                        }

                    }
                }
            });
        }
    }
}
