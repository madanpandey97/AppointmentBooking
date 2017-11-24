package com.example.kpmadan.otshealthproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
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

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DoctorDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DoctorDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorDetail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String mdoc_key = null;
    private ImageView mDocImage;
    private TextView mDocSepl;
    private TextView mDocName;
    private TextView mDocDesc;
    private TextView mDocFee;
    private TextView mDocNo;
    private TextView mDocEmail;
    private TextView mDocAdd;
    private Button bookBtn;

    private OnFragmentInteractionListener mListener;

    public DoctorDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorDetail newInstance(String param1, String param2) {
        DoctorDetail fragment = new DoctorDetail();
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
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_doctor_detail, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("doctor");

        mdoc_key = getArguments().getString("doc_id");
        mDocDesc = (TextView) rootView.findViewById(R.id.doctorDes);
        mDocName = (TextView) rootView.findViewById(R.id.doctorName);
        mDocSepl = (TextView) rootView.findViewById(R.id.doctorSpel);
        mDocImage = (ImageView) rootView.findViewById(R.id.doctorImage);

        mDocAdd = (TextView) rootView.findViewById(R.id.doctorAddress);
        mDocEmail = (TextView) rootView.findViewById(R.id.doctorEmail);
        mDocNo = (TextView) rootView.findViewById(R.id.doctorNo);
        mDocFee = (TextView) rootView.findViewById(R.id.doctorFee);

        //for booking appointment timing schedule

        bookBtn = (Button) rootView.findViewById(R.id.bookAppointment);
        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppointmentSchedule fragment = new AppointmentSchedule();
                Bundle args_new = new Bundle();
                args_new.putString("doc_id", mdoc_key);
                fragment.setArguments(args_new);

                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
            }
        });


        mDatabase.child(mdoc_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String doctor_name = (String) dataSnapshot.child("doctor_name").getValue();
                String doc_specl = (String) dataSnapshot.child("services").getValue();
                String doc_image = (String) dataSnapshot.child("image").getValue();
                String desc = (String) dataSnapshot.child("description").getValue();
                String fees = (String) dataSnapshot.child("fees").getValue();
                String contactno = (String) dataSnapshot.child("doctor_contact_no").getValue();
                String email = (String) dataSnapshot.child("doctor_email").getValue();
                String address = (String) dataSnapshot.child("address").getValue() ;

                mDocName.setText(doctor_name);
                mDocSepl.setText(doc_specl);
                mDocDesc.setText(desc);
                mDocFee.setText(fees);
                mDocEmail.setText(email);
                mDocAdd.setText(address);
                mDocNo.setText(contactno);
                Picasso.with(getActivity()).load(doc_image).into(mDocImage);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
