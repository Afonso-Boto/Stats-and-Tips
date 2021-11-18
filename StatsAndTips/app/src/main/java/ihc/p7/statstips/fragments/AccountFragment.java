package ihc.p7.statstips.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import ihc.p7.statstips.HomePage;
import ihc.p7.statstips.Login;
import ihc.p7.statstips.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Spinner dropdown, dropRem;

    List<String> items = new ArrayList<String>();
    List<String> itemsRem = new ArrayList<String>();


/*String[] items = new String[]{
        "FC Porto", "Portugal", "Beira-Mar", "Manchester United",
};*/

    Button b1, b2, b3;
    TextView t1,t2,t3, t4;
    TextInputEditText et1,et2,et3;
    View fragmentView;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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

        View view = inflater.inflate(R.layout.fragment_account, container, false);

        b1 = (Button) view.findViewById(R.id.btnEditInfo);
        b2 = (Button) view.findViewById(R.id.btnSaveChanges);
        b3 = (Button) view.findViewById(R.id.btnDiscardChanges);

        t1 = (TextView) view.findViewById(R.id.textViewName);
        t2 = (TextView) view.findViewById(R.id.textViewEmail);
        t3 = (TextView) view.findViewById(R.id.textFavTeam);
        t4 = (TextView) view.findViewById(R.id.textRemFavTeam);


        et1 = (TextInputEditText) view.findViewById(R.id.editTextViewName);
        et2 = (TextInputEditText) view.findViewById(R.id.editTextViewEmail);
        et3 = (TextInputEditText) view.findViewById(R.id.editTextViewFavourite);

        items.add("FC Porto");
        items.add("Portugal");
        items.add("Beira-Mar");
        itemsRem.add("Don't remove");
        itemsRem.add("FC Porto");
        itemsRem.add("Portugal");
        itemsRem.add("Beira-Mar");

        dropdown = (Spinner) view.findViewById(R.id.spinner);
        initspinnerfooter();
        dropRem = (Spinner) view.findViewById(R.id.spinnerRem);
        removeSpinner();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        this.fragmentView = view;

        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                b1Clicked(v);
            }
        });
        b2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                b2Clicked(v);
            }
        });
        b3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                b3Clicked(v);
            }
        });
    }

    private void initspinnerfooter() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_selected_items, items);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_items);

        dropdown.setAdapter(adapter);

    }

    private void removeSpinner() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_selected_items, itemsRem);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_items);

        dropRem.setAdapter(adapter);

    }

    // Function that allows you to change your profile info
    // It is called when se click on edit info btn
    public void b1Clicked(View view){
        b1.setVisibility(view.GONE);
        b2.setVisibility(view.VISIBLE);
        b3.setVisibility(view.VISIBLE);
        t1.setVisibility(view.GONE);
        t2.setVisibility(view.GONE);
        t3.setVisibility(view.GONE);
        t4.setVisibility(View.VISIBLE);
        et1.setVisibility(view.VISIBLE);
        et2.setVisibility(view.VISIBLE);
        et3.setVisibility(view.VISIBLE);
        dropdown.setVisibility(view.GONE);
        dropRem.setVisibility(View.VISIBLE);
    }

    //Function to save all changes made to the profile
    // It is called when se click on save changes btn
    public void b2Clicked(View view){
        b1.setVisibility(view.VISIBLE);
        b2.setVisibility(view.GONE);
        b3.setVisibility(view.GONE);
        t1.setVisibility(view.VISIBLE);
        t2.setVisibility(view.VISIBLE);
        t3.setVisibility(view.VISIBLE);
        t4.setVisibility(View.GONE);
        et1.setVisibility(view.GONE);
        if (et1.getText().toString().length()>0) t1.setText(et1.getText().toString());
        et2.setVisibility(view.GONE);
        if (et2.getText().toString().length()>0) t2.setText(et2.getText().toString());
        et3.setVisibility(view.GONE);
        if (et3.getText().toString().length()>0) {
            items.add(et3.getText().toString());
            itemsRem.add(et3.getText().toString());
        }
        String remove = dropRem.getSelectedItem().toString();
        if(items.contains(remove)){
            items.remove(remove);
            itemsRem.remove(remove);
            initspinnerfooter();
            removeSpinner();
        }
        dropdown.setVisibility(view.VISIBLE);
        dropRem.setVisibility(View.GONE);
    }

    //Function to discard all changes made to the profile
    // It is called when se click on discard btn
    public void b3Clicked(View view){
        b1.setVisibility(view.VISIBLE);
        b2.setVisibility(view.GONE);
        b3.setVisibility(view.GONE);
        t1.setVisibility(view.VISIBLE);
        t2.setVisibility(view.VISIBLE);
        t3.setVisibility(view.VISIBLE);
        t4.setVisibility(View.GONE);
        et1.setVisibility(view.GONE);
        et2.setVisibility(view.GONE);
        et3.setVisibility(view.GONE);
        dropdown.setVisibility(view.VISIBLE);
        dropRem.setVisibility(View.GONE);
    }
}