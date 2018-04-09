package aurelhoxha.bilkent360.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import aurelhoxha.bilkent360.R;


public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    private EditText inputEmail;
    private Button btnResetPass;
    private TextView btnBack;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        inputEmail   = (EditText)findViewById(R.id.changePassText);
        btnResetPass = (Button)findViewById(R.id.changePassBtn);
        btnBack      = (TextView)findViewById(R.id.changePassGoBack);

        btnResetPass.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if( v== btnBack){
            startActivity(new Intent(ForgotPassword.this,SignInActivity.class));
            finish();
        }
        else if( v == btnResetPass){
            resetPassword(inputEmail.getText().toString());
        }
    }

    private void resetPassword(final String email) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this,"Please check you email: "+email,Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    Toast.makeText(ForgotPassword.this,"Failed to send the email.",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }
}
