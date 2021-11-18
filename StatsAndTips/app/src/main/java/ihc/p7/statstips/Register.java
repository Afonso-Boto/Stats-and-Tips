package ihc.p7.statstips;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private Button btn_Reg;
    private CheckBox checkBoxOld, checkBoxTerms;
    private TextView txt_BackLogin;
    private EditText txtEmail;
    private EditText txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtEmail = findViewById(R.id.textRegInputEmail);
        txtPass = findViewById(R.id.editRegTextNumberPassword);
        checkBoxOld = findViewById(R.id.checkOld);
        checkBoxTerms = findViewById(R.id.checkTerms);

        btn_Reg = findViewById(R.id.btnRegister);
        btn_Reg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email = txtEmail.getText().toString();
                String pass= txtPass.getText().toString();
                if (!email.contains("@") || email.contains(" "))
                    Toast.makeText(Register.this, "You did not enter a valid Email", Toast.LENGTH_SHORT).show();
                else if (!checkBoxOld.isChecked()){
                    Toast.makeText(Register.this, "You need to be 18 years or older", Toast.LENGTH_SHORT).show();
                }
                else if(!checkBoxTerms.isChecked()){
                    Toast.makeText(Register.this, "You did not agree with terms of service", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (email.length() > 7 && pass.length() > 3){
                        startActivity(new Intent(Register.this, HomePage.class));
                        sendMail(email);
                    }
                    else if (email.length() < 8 && pass.length() > 3)
                        Toast.makeText(Register.this, "You did not enter a valid Email", Toast.LENGTH_SHORT).show();
                    else if (email.length() > 7)
                        Toast.makeText(Register.this, "You did not enter a valid Password", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(Register.this, "Please enter a valid email and/or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txt_BackLogin = findViewById(R.id.textBackLogin);
        txt_BackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }

    private void sendMail(String mail) {
        String msg = "Conta criada com sucesso!!";
        String subject = "Stats&Tips";

        //Send Email
        JavaMailAPI javaMailAPI = new JavaMailAPI(this, mail.trim(), subject.trim(), msg);

        javaMailAPI.execute();
    }
}