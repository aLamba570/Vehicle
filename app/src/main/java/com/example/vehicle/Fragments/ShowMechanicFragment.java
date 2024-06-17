package com.example.vehicle.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vehicle.Adapter.MechanicAdapter;
import com.example.vehicle.Model.Model;
import com.example.vehicle.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowMechanicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowMechanicFragment extends Fragment {

    RecyclerView recyclerView;
    MechanicAdapter adapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShowMechanicFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowMechanicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowMechanicFragment newInstance(String param1, String param2) {
        ShowMechanicFragment fragment = new ShowMechanicFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_mechanic, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.ShowAllMechanicRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Firebase Recycler Options to get the data form firebase database using model class and reference
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance("https://music-d664b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("allmechanics"), Model.class)
                        .build();


        adapter = new MechanicAdapter(options);
        recyclerView.setAdapter(adapter);

        if (adapter.getItemCount() == 0) {
            // Log a message or show a Toast to indicate that there's no data
            // This can help identify if the issue is with data retrieval
            Log.d("TAG", "No data found");
        }

        return view;
    }
}