package ihc.p7.statstips;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ihc.p7.statstips.fragments.HomeFragment;
//
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Leagues#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Leagues extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View fragmentView;
    TextView tvPT, tvIT, tvGER, tvEN, tvES, tvFR;

    public Leagues() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Leagues newInstance(String param1, String param2) {
        Leagues fragment = new Leagues();
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
        View view = inflater.inflate(R.layout.fragment_leagues, container, false);

        tvPT = (TextView) view.findViewById(R.id.ptLeague);
        tvES = (TextView) view.findViewById(R.id.esLeague);
        tvEN = (TextView) view.findViewById(R.id.enLeague);
        tvFR = (TextView) view.findViewById(R.id.frLeague);
        tvIT = (TextView) view.findViewById(R.id.itLeague);
        tvGER = (TextView) view.findViewById(R.id.gerLeague);

        ImageView goBack = (ImageView) view.findViewById(R.id.goBack);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager() != null) {
                    Fragment frag = new HomeFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fl_navbar, frag).commit();
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.fragmentView = view;

        tvPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPTClicked(v);
            }
        });
        tvES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvESClicked(v);
            }
        });
        tvEN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvENClicked(v);
            }
        });
        tvIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvITClicked(v);
            }
        });
        tvFR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvFRClicked(v);
            }
        });
        tvGER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvGERClicked(v);
            }
        });
    }

    public void tvPTClicked(View view) {
        StandingPT fragmentPT = new StandingPT();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_navbar, fragmentPT);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void tvITClicked(View view) {
        StandingIT fragmentIT = new StandingIT();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_navbar, fragmentIT);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void tvESClicked(View view) {
        StandingES fragmentES = new StandingES();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_navbar, fragmentES);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void tvENClicked(View view) {
        StandingEN fragmentEN = new StandingEN();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_navbar, fragmentEN);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void tvFRClicked(View view) {
        StandingFR fragmentFR = new StandingFR();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_navbar, fragmentFR);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void tvGERClicked(View view) {
        StandingGER fragmentGER = new StandingGER();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_navbar, fragmentGER);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}