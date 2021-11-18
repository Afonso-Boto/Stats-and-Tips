package ihc.p7.statstips;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hitomi.cmlibrary.CircleMenu;

import ihc.p7.statstips.fragments.AccountFragment;
import ihc.p7.statstips.fragments.GuideFragment;
import ihc.p7.statstips.fragments.HomeFragment;

public class HomePage extends AppCompatActivity {

    private String[] names = {"Recommended Events", "Standings - League/Cup", "Teams - League/Cup"};
    private CircleMenu circleMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        BottomNavigationView bottomNavBar = findViewById(R.id.bottom_navbar);
        bottomNavBar.setOnNavigationItemSelectedListener(navbarListener);


        // To start with HomeFragment()
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_navbar, new HomeFragment()).commit();


        Button btnEventos = (Button) findViewById(R.id.btnEventos);
        btnEventos.setOnClickListener(this::onClick);

        Button btnStanding = (Button) findViewById(R.id.btnStanding);
        btnStanding.setOnClickListener(this::onClick);

        Button btnBD = (Button) findViewById(R.id.btnBD);
        btnBD.setOnClickListener(this::onClick);

    }

    public void onClick(View view) {
        Fragment fragment;
        switch (view.getId()) {
            case R.id.btnEventos:
                fragment = new Evento();
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_navbar, fragment).commit();
                break;

            case R.id.btnStanding:
                fragment = new Leagues();
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_navbar, fragment).commit();
                break;

            case R.id.btnBD:
                fragment = new FantasyLeague();
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_navbar, fragment).commit();
                break;
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navbarListener = item -> {
        Fragment selected = null;
        switch (item.getItemId()){
            case R.id.homepage:
                selected = new HomeFragment();
                break;
            case R.id.guide:
                selected = new GuideFragment();
                break;
            case R.id.account:
                selected = new AccountFragment();
                break;
        }

        assert selected != null;
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_navbar, selected).commit();
        return true;
    };


}