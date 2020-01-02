package com.istiak.recyclerviewinmysqldatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;

    ArrayList<HashMap<String, String>> list;

    //constructor
    public CustomAdapter(Context context,  ArrayList<HashMap<String, String>> list) {
        this.context = context;
//        this.userName = userName;
//        this.contact = contact;
//        this.email=email;

        this.list=list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the item Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(view); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        holder.txtName.setText(list.get(position).get(Constant.KEY_USER_NAME));
        holder.txtContact.setText(list.get(position).get(Constant.KEY_CONTACT));
        holder.txtEmail.setText(list.get(position).get(Constant.KEY_EMAIL));
        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                //Toast.makeText(context, countryList[position], Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtName,txtContact,txtEmail;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_name);
            txtContact = itemView.findViewById(R.id.txt_contact);
            txtEmail = itemView.findViewById(R.id.txt_email);
        }
    }


}
