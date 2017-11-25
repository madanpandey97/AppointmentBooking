package com.example.kpmadan.otshealthproject;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AppointmentSchedule.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AppointmentSchedule#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppointmentSchedule extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private  TabHost host;
    private ListView listView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private String mDocKey;
    private List<String> afternoon =new ArrayList<>();
    private List<String> morning = new ArrayList<>();
    private List<String> evening = new ArrayList<>();
    private ListView listViewevng;
    private ListView listViewaft;
    private ImageView mDocImage;
    private TextView mDocName;
    private TextView mDocSer;


    private OnFragmentInteractionListener mListener;

    public AppointmentSchedule() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppointmentSchedule.
     */
    // TODO: Rename and change types and number of parameters
    public static AppointmentSchedule newInstance(String param1, String param2) {
        AppointmentSchedule fragment = new AppointmentSchedule();
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
        View rootView = inflater.inflate(R.layout.fragment_appointment_schedule, container, false);
        mDocKey= getArguments().getString("doc_id");
        host = (TabHost)rootView.findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Morning");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Morning");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("AfterNoon");
        spec.setContent(R.id.tab2);
        spec.setIndicator("AfterNoon");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Evening");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Evening");
        host.addTab(spec);

        // list view working <code></code>

        mDatabase = FirebaseDatabase.getInstance().getReference().child("doctor");
        String[] months={"Janaury","Feb","March","April","May","June","July","August","September","Octomber","November","December"};
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,morning);
        listView = (ListView) rootView.findViewById(R.id.morning_list);
        listViewaft = (ListView) rootView.findViewById(R.id.afternoon_list);
        listViewevng = (ListView) rootView.findViewById(R.id.evening_list);
        mDocImage = (ImageView) rootView.findViewById(R.id.doc_img);
        mDocName = (TextView) rootView.findViewById(R.id.doc_name);
        mDocSer = (TextView) rootView.findViewById(R.id.doc_ser);
        mDatabase.keepSynced(true);

        mDatabase.child(mDocKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                morning= (ArrayList) dataSnapshot.child("morning").getValue();
                Log.v("datais ", afternoon.toString());
                String[] morArr = new String[morning.size()];
                morArr = morning.toArray(morArr);
                ArrayAdapter<String> arrayadapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,morArr);
                listView.setAdapter(arrayadapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDatabase.child(mDocKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                afternoon = (ArrayList) dataSnapshot.child("afternoon").getValue();

                Log.v("datais ", afternoon.toString());
                String[] morArr = new String[afternoon.size()];
                morArr = afternoon.toArray(morArr);
                ArrayAdapter<String> arrayadapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,morArr);
                listViewaft.setAdapter(arrayadapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDatabase.child(mDocKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                evening = (ArrayList) dataSnapshot.child("evening").getValue();

                Log.v("datais ", evening.toString());
                String[] morArr = new String[evening.size()];
                morArr = evening.toArray(morArr);
                ArrayAdapter<String> arrayadapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,morArr);
                listViewevng.setAdapter(arrayadapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child(mDocKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String doctor_name = (String) dataSnapshot.child("doctor_name").getValue();
                String doc_specl = (String) dataSnapshot.child("services").getValue();
                String doc_image = (String) dataSnapshot.child("image").getValue();

                mDocName.setText(doctor_name);
                mDocSer.setText(doc_specl);
                Picasso.with(getActivity()).load(doc_image).into(mDocImage);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AppointmentForm fragment = new AppointmentForm();
                Bundle args_new = new Bundle();
                args_new.putString("doc_id", mDocKey);
                fragment.setArguments(args_new);

                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
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
