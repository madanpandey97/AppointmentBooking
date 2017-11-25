package com.example.kpmadan.otshealthproject;

/**
 * Created by Madan on 23-10-2017.
 */
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import android.content.Context;
import android.widget.Toast;


public class DoctorTabView extends Fragment {
    private RecyclerView mDoctorList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button bookBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_doctor_tab_view, container, false);


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                if(firebaseAuth.getCurrentUser()==null){
                    Intent loginIntent = new Intent(getActivity(),SignupOptionActivity.class);

                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }
            }
        };

        mDatabase = FirebaseDatabase.getInstance().getReference().child("doctor");
        mDatabase.keepSynced(true);
        mDoctorList = (RecyclerView) rootView.findViewById(R.id.doctor_view_list);
        mDoctorList.setHasFixedSize(true);
        mDoctorList.setLayoutManager(new LinearLayoutManager(getActivity()));


        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        FirebaseRecyclerAdapter<DoctorList, DoctorViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DoctorList, DoctorViewHolder>(
                DoctorList.class,
                R.layout.doctor_row_card,
                DoctorViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(DoctorViewHolder viewHolder, DoctorList model, int position) {
                final String doc_id = getRef(position).getKey();

                viewHolder.setDoctorname(model.getDoctor_name());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setLocation(model.getLocation());
                viewHolder.setFees(model.getFees());
                viewHolder.setServices(model.getServices());
                viewHolder.setImage(getActivity().getApplicationContext(), model.getImage());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DoctorDetail fragment = new DoctorDetail();
                        Bundle args = new Bundle();
                        args.putString("doc_id", doc_id);
                        fragment.setArguments(args);

                        android.support.v4.app.FragmentTransaction fragmentTransaction =
                                getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.commit();
                    }
                });
            }
        };
        mDoctorList.setAdapter(firebaseRecyclerAdapter);
    }


    public  static class DoctorViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public DoctorViewHolder(View itemView){
            super(itemView);
            mView=itemView;

        }


        public  void setDoctorname(String title){
            TextView doctor_name = (TextView) mView.findViewById(R.id.doctor_name);
            doctor_name.setText(title);

        }
        public  void setDescription(String desc){
            TextView doctor_desc = (TextView) mView.findViewById(R.id.doctor_des);
            doctor_desc.setText(desc);

        }
        public void setServices(String services) {
            TextView doctor_service = (TextView) mView.findViewById(R.id.doctor_service);
            doctor_service.setText(services);
        }
        public  void setFees(String title){
            TextView doctor_fees = (TextView) mView.findViewById(R.id.doctor_fees);
            doctor_fees.setText(title);

        }
        public  void setLocation(String loc){
            TextView doctor_location = (TextView) mView.findViewById(R.id.doctor_location);
            doctor_location.setText(loc);

        }
        public void setImage(final Context ctx, final String image ){
            final ImageView doctor_image = (ImageView) mView.findViewById(R.id.doctor_image);


            // Picasso.with(ctx).load(image).into(doctor_image);

            Picasso.with(ctx).load(image).networkPolicy(NetworkPolicy.OFFLINE).into(doctor_image,new Callback(){
                @Override
                public void onSuccess(){

                }
                @Override
                public void onError(){
                    Picasso.with(ctx).load(image).into(doctor_image);
                }

            });
        }
    }



}


