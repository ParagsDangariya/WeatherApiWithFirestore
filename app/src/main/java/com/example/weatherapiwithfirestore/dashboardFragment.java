package com.example.weatherapiwithfirestore;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class dashboardFragment extends Fragment {

    TextView txt_name;
    Button btn_logout;
    FirebaseFirestore db;
    FirebaseUser user;
    Controller con;
    ImageView imageView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        readFirestore();
        txt_name= view.findViewById(R.id.txt_dashname);
        btn_logout = view.findViewById(R.id.btn_logout);
        imageView = view.findViewById(R.id.iv);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                con = new Controller();
                con.navigatetofragment(R.id.loginFragment,getActivity(),null);
            }
        });

        Getdataservice service = RetroFitInstance.getRetrofitInstance().create(Getdataservice.class);


        Call<Weather> call = service.getWeather();
        System.out.println("Call : " + call);
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                Weather real = response.body();
                ArrayList<ConsolidatedWeather> conArray = new ArrayList<>(real.getConsolidatedWeather());



                System.out.println("Response: " + conArray.get(0).getWeatherStateName());

            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                System.out.println("Error : "+t.getMessage());
            }
        });

    }

    public  void readFirestore(){
        DocumentReference docref =db.collection("users").document(user.getUid());
        docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot =task.getResult();
                    if (documentSnapshot.exists()){
                        Log.d("snapdata",documentSnapshot.getData().toString());
                        txt_name.setText("Welcome ..."+documentSnapshot.get("name"));
                    }
                }
            }
        });

    }

    public dashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = getArguments().getParcelable("user");
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


}
