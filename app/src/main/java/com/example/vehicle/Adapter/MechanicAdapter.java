package com.example.vehicle.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vehicle.Model.Model;
import com.example.vehicle.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MechanicAdapter extends FirebaseRecyclerAdapter<Model, MechanicAdapter.Viewholder> {

    public MechanicAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {

        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull MechanicAdapter.Viewholder holder, int position, @NonNull Model model) {
        holder.nameTxt.setText(model.getMechanicName());
        holder.phoneNumberTxt.setText(model.getMechanicNumber());
        holder.LocationTxt.setText(model.getMechanicLocation());
        holder.priceTxt.setText(model.getMechanicPrice());
        holder.vechicleNameTxt.setText(model.getVehicleName());

    }

    @NonNull
    @Override
    public MechanicAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_data_file, parent, false);
        return new MechanicAdapter.Viewholder(view);
    }

    class Viewholder extends RecyclerView.ViewHolder {


        TextView nameTxt, phoneNumberTxt, vechicleNameTxt, LocationTxt, priceTxt;


        public Viewholder(@NonNull View itemView) {
            super(itemView);

            //asssiginig the address of the android materials to show  mechanic Details
            nameTxt = (TextView) itemView.findViewById(R.id.NameTxt);
            phoneNumberTxt = (TextView) itemView.findViewById(R.id.PhoneNumberTxt);
            vechicleNameTxt = (TextView) itemView.findViewById(R.id.VehicleNameTxt);
            LocationTxt = (TextView) itemView.findViewById(R.id.LocationTxt);
            priceTxt = (TextView) itemView.findViewById(R.id.PriceTxt);


        }
    }
}
