package ihc.p7.statstips;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ihc.p7.statstips.fragments.HomeFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FantasyLeague#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FantasyLeague extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView club1, club2, club3, club4, club5;
    List<List<String>> clubes = new ArrayList<>();
    List<List<String>> jogadores = new ArrayList<>();
    ListView lv, lvPlayer;
    static int mode = 0; //0=teams, 1=players
    TextView teams_players;

    public FantasyLeague() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FantasyLeague.
     */
    // TODO: Rename and change types and number of parameters
    public static FantasyLeague newInstance(String param1, String param2) {
        FantasyLeague fragment = new FantasyLeague();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fantasy_league, container, false);

        HandlerDB db = new HandlerDB();

        System.err.println("FantasyLeague() -> onCreateView()");

        //Save {id_clube, name}
        try {
            String[] values = db.getClubes().split(";");

            for (int i = 0; i< values.length - 2; i+=2) {
                clubes.add(new ArrayList<>());
                clubes.get(clubes.size()-1).add(values[i].trim()); //id_clube
                clubes.get(clubes.size()-1).add(values[i+1].trim()); //nome
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //Save {id_jogador, name}
        try {
            String[] values = db.getPlayers().split(";");

            for (int i = 0; i< values.length - 2; i+=2) {
                jogadores.add(new ArrayList<>());
                jogadores.get(jogadores.size()-1).add(values[i].trim()); //id_jogador
                jogadores.get(jogadores.size()-1).add(values[i+1].trim()); //nome
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Search box
        EditText text = (EditText) v.findViewById(R.id.searchEditText);



        // Search Clube
        Button btnSearch = (Button) v.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager() != null) {
                    Fragment frag = new Club();

                    searchClubQuery(db, frag, text);

                    getFragmentManager().beginTransaction().replace(R.id.fl_navbar, frag).commit();
                }
            }
        });

        // List View de Clubes/Players
        lv = (ListView) v.findViewById(R.id.listViewClubes);

        List<String> nomes_clubes = new ArrayList<>();
        clubes.forEach(lst -> nomes_clubes.add(lst.get(1)));

        List<String> nomes_players = new ArrayList<>();
        jogadores.forEach(lst -> nomes_players.add(lst.get(1)));

        //Team and Players List
        teams_players = (TextView) v.findViewById(R.id.teams_players);
        teams_players.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> lst = new ArrayList<>();
                if (mode == 0){
                    lst = nomes_clubes;
                    teams_players.setText("Teams");
                    mode = 1;
                }else if (mode == 1){
                    lst = nomes_players;
                    teams_players.setText("Players");
                    mode = 0;
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, lst) {
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
                        if (mode == 1) {
                            text.setText(nomes_clubes.get(position));
                            btnSearch.performClick();
                        }else if (mode == 0){
                            if (getFragmentManager() != null) {
                                Fragment frag = new PlayerPage();
                                String id_jogador = "";
                                String nome_jogador = parent.getItemAtPosition(position).toString().trim();
                                for(List<String> j : jogadores){
                                    if (j.get(1).equals(nome_jogador)) {
                                        id_jogador = j.get(0);
                                        System.err.printf("ENCONTRADO: %s,%s", id_jogador, j.get(1));
                                        break;
                                    }
                                }
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
                    }
                });
            }
        });

        teams_players.performClick(); //show teams






        Button btnAddClube = (Button) v.findViewById(R.id.btnAddClube);
        btnAddClube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager() != null) {
                    Fragment frag = new AddClube();
                    getFragmentManager().beginTransaction().replace(R.id.fl_navbar, frag).commit();
                }
            }
        });


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

    public void searchClubQuery(HandlerDB db, Fragment frag, EditText text){
        // SQL Query
        try {
            Bundle b = new Bundle();
            b.putString("club", db.getClubeByNome(text.getText().toString()));
            frag.setArguments(b);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}