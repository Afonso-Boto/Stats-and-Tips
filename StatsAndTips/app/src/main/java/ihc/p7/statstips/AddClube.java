package ihc.p7.statstips;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ihc.p7.statstips.fragments.HomeFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddClube#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddClube extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String id_clube_current;
    String id_equipa_current;
    EditText nameVal, formedIn_positionVal, coach_nationalityVal, clubePlayerVal;

    public AddClube() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddClube.
     */
    // TODO: Rename and change types and number of parameters
    public static AddClube newInstance(String param1, String param2) {
        AddClube fragment = new AddClube();
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
        View v = inflater.inflate(R.layout.fragment_add_clube, container, false);

        HandlerDB db = new HandlerDB();

        //Get all IDs clubes/equipas
        List<String> id_clubes = new ArrayList<>();
        List<String> id_equipas = new ArrayList<>();
        try {
            String[] value = db.getIDClubeEquipa().split(";");
            id_clubes.add(value[0].trim());
            id_equipas.add(value[1].trim());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //Generate ID CLUBE
        while (true){
            id_clube_current = "C" + getRandomNumber(1,100);
            if (!id_clubes.contains(id_clube_current)){
                id_clubes.add(id_clube_current);
                break;
            }

        }

        //Generate ID EQUIPA
        while (true){
            id_equipa_current = "E" + getRandomNumber(1,100);
            if (!id_equipas.contains(id_equipa_current)){
                id_equipas.add(id_equipa_current);
                break;
            }

        }

        List<String> id_jogadores = new ArrayList<>();
        try {
            String[] value = db.getIDJogador().split(";");
            id_jogadores.add(value[0].trim());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        List<String> jogadores = new ArrayList<>();

        TextView name = (TextView) v.findViewById(R.id.name);
        TextView formedIn_position = (TextView) v.findViewById(R.id.formedIn);
        TextView coach_nationality = (TextView) v.findViewById(R.id.coach);
        TextView clubePlayer = (TextView) v.findViewById(R.id.clubePlayer);
        clubePlayer.setVisibility(View.GONE);

        nameVal = (EditText) v.findViewById(R.id.nameVal);
        formedIn_positionVal = (EditText) v.findViewById(R.id.formedInVal);
        coach_nationalityVal = (EditText) v.findViewById(R.id.coachVal);
        clubePlayerVal = (EditText) v.findViewById(R.id.clubePlayerVal);
        clubePlayerVal.setVisibility(View.GONE);

        Button btnAddClube = (Button) v.findViewById(R.id.btnAddClube);
        Button btnAddPlayer = (Button) v.findViewById(R.id.btnAddPlayer);
        Button btnSavePlayer = (Button) v.findViewById(R.id.btnSavePlayer);

        // ADD CLUBE
        btnAddClube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save the club to db
                if (!nameVal.getText().toString().trim().matches("")
                        || !formedIn_positionVal.getText().toString().trim().matches("")
                        || !coach_nationalityVal.getText().toString().trim().matches("")) {

                    try {
                        db.addClube(id_clube_current, nameVal.getText().toString().trim(),
                                Integer.parseInt(formedIn_positionVal.getText().toString().trim()), id_equipa_current, coach_nationalityVal.getText().toString().trim());

                        if (!jogadores.isEmpty()){
                            for (String j : jogadores) {
                                String[] tmp = j.split(",");
                                db.addPlayer_IDClube(tmp[0], tmp[1], tmp[2], tmp[3], id_clube_current);
                            }
                        }

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }


                    if (getFragmentManager() != null) {
                        Fragment frag = new FantasyLeague();
                        getFragmentManager().beginTransaction().replace(R.id.fl_navbar, frag).commit();
                    }
                }
            }
        });

        //ADD JOGADOR -> CHANGE the UI
        btnAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formedIn_position.setText("Position");
                formedIn_positionVal.setInputType(InputType.TYPE_CLASS_TEXT);
                coach_nationality.setText("Nationality");


                btnAddPlayer.setVisibility(View.GONE);
                btnAddClube.setVisibility(View.GONE);

                btnSavePlayer.setVisibility(View.VISIBLE);
                clubePlayer.setVisibility(View.VISIBLE);
                clubePlayerVal.setVisibility(View.VISIBLE);
            }
        });

        //SAVE JOGADOR para List OU ADD DIRETO A EQUIPA
        btnSavePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nameVal.getText().toString().trim().matches("")
                        || !formedIn_positionVal.getText().toString().trim().matches("")
                        || !coach_nationalityVal.getText().toString().trim().matches("")) {

                    if (formedIn_positionVal.getText().toString().trim().matches("GR")
                        || formedIn_positionVal.getText().toString().trim().matches("DEF")
                        || formedIn_positionVal.getText().toString().trim().matches("MED")
                        || formedIn_positionVal.getText().toString().trim().matches("AV")) {


                        //Tornar visivel a opcao de associar o JOGADOR a um CLUBE
                        clubePlayer.setVisibility(View.VISIBLE);
                        clubePlayerVal.setVisibility(View.VISIBLE);

                        //Generate ID JOGADOR
                        String id;
                        while (true) {
                            id = "J" + getRandomNumber(1, 1000);
                            if (!id_jogadores.contains(id)) {
                                id_jogadores.add(id);
                                break;
                            }

                        }

                        //Confirmar se o user quer que o JOGADOR vá para o CLUBE CRIADO ou CLUBE EXISTENTE
                        if (!clubePlayerVal.getText().toString().trim().matches("")) {
                            try {
                                db.addPlayer_NameClube(id, nameVal.getText().toString().trim(), coach_nationalityVal.getText().toString().trim()
                                        , formedIn_positionVal.getText().toString().trim(), clubePlayerVal.getText().toString().trim());
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        } else {
                            String jogador = String.format("%s,%s,%s,%s", id, nameVal.getText().toString().trim(),
                                    coach_nationalityVal.getText().toString().trim(), formedIn_positionVal.getText().toString().trim());
                            jogadores.add(jogador);
                        }

                        //Revert UI Changes
                        cleanUI();
                        formedIn_position.setText("Formed In");
                        formedIn_positionVal.setInputType(InputType.TYPE_CLASS_PHONE);
                        coach_nationality.setText("Coach");


                        btnAddPlayer.setVisibility(View.VISIBLE);
                        btnAddClube.setVisibility(View.VISIBLE);

                        btnSavePlayer.setVisibility(View.GONE);
                        clubePlayer.setVisibility(View.GONE);
                        clubePlayerVal.setVisibility(View.GONE);
                    }else {
                        Toast.makeText(getContext(), "Invalid position, please select GR, DEF, MED or AV as a position", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        ImageView goBack = (ImageView) v.findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager() != null) {
                    Fragment frag = new FantasyLeague();
                    getFragmentManager().beginTransaction().replace(R.id.fl_navbar, frag).commit();
                }
            }
        });





        return v;
    }

    public int getRandomNumber(int min, int max){
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public void cleanUI(){
        nameVal.setText("");
        formedIn_positionVal.setText("");
        coach_nationalityVal.setText("");
        clubePlayerVal.setText("");
    }
}