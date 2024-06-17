package com.example.vehicle.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vehicle.Model.Model;
import com.example.vehicle.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class HomeAdapter extends FirebaseRecyclerAdapter<Model, HomeAdapter.Viewholder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public HomeAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull Viewholder holder, int position, @NonNull Model model) {
        Context context=holder.itemView.getContext();

        //Adding data to xml file
        holder.nameTxt.setText(model.getMechanicName());
        holder.phoneNumberTxt.setText(model.getMechanicNumber());
        holder.LocationTxt.setText(model.getMechanicLocation());
        holder.priceTxt.setText(model.getMechanicPrice());
        holder.vechicleNameTxt.setText(model.getVehicleName());

        //implementing onClickListener to make a Phone Call
        holder.mCallMechanicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Accessing users Phone Number
                String phone=model.getMechanicNumber();

                //implementing phone call using Intent
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                context.startActivity(intent);

            }
        });

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_data_layout, parent, false);
        return new Viewholder(view);
    }

    class Viewholder extends RecyclerView.ViewHolder {

        TextView nameTxt, phoneNumberTxt, vechicleNameTxt, LocationTxt, priceTxt;
        Button mCallMechanicBtn;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            //asssiginig the address of the Android materials
            nameTxt = (TextView) itemView.findViewById(R.id.NameTxt);
            phoneNumberTxt = (TextView) itemView.findViewById(R.id.PhoneNumberTxt);
            vechicleNameTxt = (TextView) itemView.findViewById(R.id.VehicleNameTxt);
            LocationTxt = (TextView) itemView.findViewById(R.id.LocationTxt);
            priceTxt = (TextView) itemView.findViewById(R.id.PriceTxt);
            mCallMechanicBtn=(Button)itemView.findViewById(R.id.CallMechanicBtn);
        }
    }
}
