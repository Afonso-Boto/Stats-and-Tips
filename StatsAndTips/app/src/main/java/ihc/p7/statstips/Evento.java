package ihc.p7.statstips;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import ihc.p7.statstips.fragments.HomeFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Evento#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Evento extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Evento() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Evento.
     */
    // TODO: Rename and change types and number of parameters
    public static Evento newInstance(String param1, String param2) {
        Evento fragment = new Evento();
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
        View v = inflater.inflate(R.layout.fragment_evento, container, false);

        //1º passar da DB para ArrayList<Odd>
        //2º criar o adapter e passar o arraylist -> OddListAdapter x = new ...
        //3º

        HandlerDB db = new HandlerDB();

        System.err.println("Eventos() -> onCreateView()");

        ListView odds = (ListView) v.findViewById(R.id.listView);
        ArrayList<Odd> lst = new ArrayList<>();
        try {
            String[] query = db.getOdds().split(";");

            for (int i = 0; i < query.length - 11; i+=10){
                String nome = db.getNameByIdEquipa(query[i].trim()).split(";")[0] + " x " + db.getNameByIdEquipa(query[i+1].trim()).split(";")[0];
                lst.add(new Odd(nome, query[i+2].trim(), query[i+3].trim(), query[i+4].trim(), query[i+5].trim(), query[i+6].trim(), query[i+7].trim(), query[i+8].trim(), query[i+9].trim()));
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        OddListViewAdapter oddAdapter = new OddListViewAdapter(Objects.requireNonNull(getContext()), lst);
        odds.setAdapter(oddAdapter);

        // Go Back Button
        ImageView goBack = (ImageView) v.findViewById(R.id.goBack);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager() != null) {
                    Fragment frag = new HomeFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fl_navbar, frag).commit();
                }
            }
        });

        return v;
    }
}