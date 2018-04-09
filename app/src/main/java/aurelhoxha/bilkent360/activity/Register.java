package aurelhoxha.bilkent360.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import aurelhoxha.bilkent360.R;
import aurelhoxha.bilkent360.other.User;

public class Register extends AppCompatActivity implements View.OnClickListener {
    EditText name, email, password;
    Button mSign;


    //Firebase Connection Instances
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    ProgressDialog mDialog;

    String takeName,takeEmail,takePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name     = (EditText)findViewById(R.id.registerPersonId);
        email    = (EditText)findViewById(R.id.registerEmailId);
        password = (EditText)findViewById(R.id.registerPasswordId);
        mSign    = (Button)  findViewById(R.id.signUpRegisterButtonId);
        mAuth = FirebaseAuth.getInstance();
        mSign.setOnClickListener(this);
        mDialog = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    @Override
    public void onClick(View v) {
        if(v == mSign){
            userRegister();
        }
    }

    private void userRegister() {
        takeName     = name.getText().toString().trim();
        takeEmail    = email.getText().toString().trim();
        takePassword = password.getText().toString().trim();
        if(TextUtils.isEmpty(takeName)){
            Toast.makeText(Register.this,"Enter Name",Toast.LENGTH_SHORT).show();
            return;
        } else if(TextUtils.isEmpty(takeEmail)){
            Toast.makeText(Register.this,"Enter Email",Toast.LENGTH_SHORT).show();
            return;
        } else if(TextUtils.isEmpty(takePassword)){
            Toast.makeText(Register.this,"Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }else if(takePassword.length() < 6 ){
            Toast.makeText(Register.this,"Password must be greater than 6 letters!",Toast.LENGTH_SHORT).show();
            return;
        }


        mDialog.setMessage("Creating User! Please wait...");
        mDialog.setCanceledOnTouchOutside(false);
        mAuth.createUserWithEmailAndPassword(takeEmail, takePassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mDialog.dismiss();
                    sendUserEmailVerification();
                        onAuth(task.getResult().getUser());
                } else {
                    Toast.makeText(Register.this, "Error on creating user!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void sendUserEmailVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if( user != null )
        {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Register.this, "Verification email sent to: " + takeEmail, Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                    }
                }
            });

        }
    }
    private boolean checkVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        boolean created = false;
        if (user != null) {
            if (user.isEmailVerified() == true) {
                created = true;
            }
        }
        return created;
    }

    private void onAuth(FirebaseUser user) {
        createNewUser(user.getUid());
    }

    private void createNewUser(String uid) {
        User user = buildNewUser();
        mDatabase.child(uid).child("Info").setValue(user);
    }

    private User buildNewUser() {
        return new User( getDisplayName(),getDisplayEmail() );
    }

    public String getDisplayName() {
        return takeName;
    }

    public String getDisplayEmail() {
        return takeEmail;
    }
}
