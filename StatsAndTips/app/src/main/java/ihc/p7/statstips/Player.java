package ihc.p7.statstips;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Player#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Player extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    HandlerDB db = null;
    ListView lv;

    public Player() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Player.
     */
    // TODO: Rename and change types and number of parameters
    public static Player newInstance(String param1, String param2) {
        Player fragment = new Player();
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
        View v = inflater.inflate(R.layout.fragment_player, container, false);

        String[] value = getArguments() != null ? String.valueOf(getArguments().getString("player")).split(";") : null;

        db = new HandlerDB();

        System.err.println("Player() -> onCreateView()");
        System.err.println(Arrays.toString(value));

        ImageView goBack = (ImageView) v.findViewById(R.id.goBack);

        // List View de Clubes/Players
        lv = (ListView) v.findViewById(R.id.listViewClubes);

        List<String> nomes = new ArrayList<>();

        Map<Integer, String> jogadores = new HashMap<>();

        String id_clube = null;
        for (int i = 0; i < Objects.requireNonNull(value).length - 4; i+=4){
            //i:nome, i+1:posicao, i+2:id_jogador, i+3:id_clube
            value[i] = value[i].trim();
            value[i+1] = value[i+1].trim();
            value[i+2] = value[i+2].trim();
            id_clube = value[i+3].trim();
            jogadores.put(i, String.format("%s,%s", value[i], value[i+2]));
            nomes.add(value[i]);

        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, nomes) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);

                // Generate ListView Item using TextView
                return view;
            }
        };
        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getFragmentManager() != null) {
                    Fragment frag = new PlayerPage();
                    String id_jogador = Objects.requireNonNull(jogadores.get(position)).split(",")[1];
                    try {
                        Bundle b = new Bundle();
                        b.putString("player_page", db.getPlayerPage(id_jogador));
                        frag.setArguments(b);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    getFragmentManager().beginTransaction().replace(R.id.fl_navbar, frag).commit();
                }
            }
        });

        // GO BACK button
        String finalId_clube = id_clube;
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager() != null) {
                    Fragment frag = new Club();
                    System.err.printf("ID CLUB=%s\n", finalId_clube);
                    // SQL Query
                    try {
                        Bundle b = new Bundle();
                        b.putString("club", db.getClubeByID(finalId_clube));
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

    public Fragment getPlayerPageWithQuery(String id_jogador){
        Fragment frag = new PlayerPage();

        // SQL Query
        try {
            Bundle b = new Bundle();
            b.putString("player_page", db.getPlayerPage(id_jogador));
            frag.setArguments(b);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return frag;
    }
}