package com.example.kpmadan.otshealthproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AppointmentForm.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AppointmentForm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppointmentForm extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mDocKey;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView mNameField;
    private TextView mEmailField;
    private TextView mDctName;
    private TextView mDctSer;
    private ImageView mDocImg;


    private OnFragmentInteractionListener mListener;

    public AppointmentForm() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppointmentForm.
     */
    // TODO: Rename and change types and number of parameters
    public static AppointmentForm newInstance(String param1, String param2) {
        AppointmentForm fragment = new AppointmentForm();
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

        View rootView =  inflater.inflate(R.layout.fragment_appointment_form, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("doctor");
        mDocKey= getArguments().getString("doc_id");
        mNameField = (TextView) rootView.findViewById(R.id.nameField);
        mEmailField = (TextView) rootView.findViewById(R.id.emailField);
        mDctName = (TextView) rootView.findViewById(R.id.dct_name);
        mDctSer = (TextView) rootView.findViewById(R.id.dct_ser);
        mDocImg = (ImageView) rootView.findViewById(R.id.dct_img);
        mDatabase.child(mDocKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String doctor_name = (String) dataSnapshot.child("doctor_name").getValue();
                String doc_specl = (String) dataSnapshot.child("services").getValue();
                String doc_image = (String) dataSnapshot.child("image").getValue();

                mDctName.setText(doctor_name);
                mDctSer.setText(doc_specl);
                Picasso.with(getActivity()).load(doc_image).into(mDocImg);

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
