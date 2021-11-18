package ihc.p7.statstips;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Club#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Club extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Club() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Club.
     */
    // TODO: Rename and change types and number of parameters
    public static Club newInstance(String param1, String param2) {
        Club fragment = new Club();
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
        View v = inflater.inflate(R.layout.fragment_club, container, false);

        String[] value = getArguments() != null ? String.valueOf(getArguments().getString("club")).split(";") : null;

        HandlerDB db = new HandlerDB();

        System.err.println("Club() -> onCreateView()");
        System.err.println(Arrays.toString(value));

        ImageView goBack = (ImageView) v.findViewById(R.id.goBack);

        TextView textViewCoach = (TextView) v.findViewById(R.id.textViewCoach);
        textViewCoach.setText(value != null ? value[0].trim() : null);

        TextView textViewNGolos = (TextView) v.findViewById(R.id.textViewNGolos);
        textViewNGolos.setText(value != null ? value[1].trim() : null);

        TextView textViewFormedIn = (TextView) v.findViewById(R.id.textViewFormedIn);
        textViewFormedIn.setText(value != null ? value[3].trim() : null);

        TextView textViewClubName = (TextView) v.findViewById(R.id.textViewClubName);
        textViewClubName.setText(value != null ? value[2].trim() : null);

        String id_clube = value != null ? value[4].trim() : null;

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager() != null) {
                    Fragment frag = new FantasyLeague();
                    getFragmentManager().beginTransaction().replace(R.id.fl_navbar, frag).commit();
                }
            }
        });

        Button btnTeam = (Button) v.findViewById(R.id.btnJogadores);
        btnTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager() != null) {
                    Fragment frag = new Player();

                    // SQL Query
                    try {
                        Bundle b = new Bundle();
                        b.putString("player", db.getPlayer(id_clube));
                        frag.setArguments(b);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    getFragmentManager().beginTransaction().replace(R.id.fl_navbar, frag).commit();
                }
            }
        });

        return v;
    }
}