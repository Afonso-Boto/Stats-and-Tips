package ihc.p7.statstips;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {
    private Button btn_login;
    private TextView txt_loginGuest, txt_register;
    private EditText txtEmail;
    private EditText txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        txtEmail = findViewById(R.id.textInputEmail);
        txtPass = findViewById(R.id.editTextNumberPassword);

        btn_login = findViewById(R.id.btnLogin);
        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email = txtEmail.getText().toString();
                String pass= txtPass.getText().toString();
                if (!email.contains("@") || email.contains(" "))
                    Toast.makeText(Login.this, "You did not enter a valid Email", Toast.LENGTH_SHORT).show();
                else {
                    if (email.length() > 7 && pass.length() > 3)
                        startActivity(new Intent(Login.this, HomePage.class));
                    else if (email.length() < 8 && pass.length() > 3)
                        Toast.makeText(Login.this, "You did not enter a valid Email", Toast.LENGTH_SHORT).show();
                    else if (email.length() > 7)
                        Toast.makeText(Login.this, "You did not enter a valid Password", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(Login.this, "Please enter a valid email and/or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txt_loginGuest = findViewById(R.id.textLoginGuest);
        txt_loginGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, HomePage.class));
            }
        });
        txt_register = findViewById(R.id.textRegister);
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }
}