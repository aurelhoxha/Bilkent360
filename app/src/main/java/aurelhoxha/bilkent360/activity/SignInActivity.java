package aurelhoxha.bilkent360.activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import aurelhoxha.bilkent360.R;


public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmail, mPassword;
    private Button mLogin,mSign;
    private TextView mForgotPassword;

    //Authentication to Firebase Instances
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String mUserId;
    private FirebaseUser mUser;

    private String userEmail,userPassword;

    public static final String TAG = "Login";

    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);



        mEmail          = (EditText) findViewById(R.id.loginEmailId);
        mPassword       = (EditText) findViewById(R.id.loginPasswordId);
        mLogin          = (Button)   findViewById(R.id.signInButtonId);
        mSign           = (Button)   findViewById(R.id.signUpButtonId);
        mForgotPassword = (TextView) findViewById(R.id.forgotPassId);


        mDialog         = new ProgressDialog(this);
        mLogin.setOnClickListener(this);
        mSign.setOnClickListener(this);
        mForgotPassword.setOnClickListener(this);


        mAuth         = FirebaseAuth.getInstance();

        if( mAuth.getCurrentUser() != null ) {
            //mAuth = FirebaseAuth.getInstance();
            //mFirebaseUser = mAuth.getCurrentUser();
            //mUserId = mFirebaseUser.getUid();
            //mDatabase = FirebaseDatabase.getInstance().getReference("Admins");
//            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot snapshot) {
//                    if (snapshot.child(mUserId).exists()) {
//                        startActivity(new Intent(SignInActivity.this, AdminMenuActivity.class));
//                        finish();
//                    }else
//                    {
                        startActivity(new Intent(SignInActivity.this, MenuActivity.class));
                        finish();
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });


        }
    }

    @Override
    public void onClick(View v) {

        if(v == mLogin){
            userSign();

        }
        else if(v == mSign){
            startActivity(new Intent(SignInActivity.this,Register.class));
        }
        else if(v == mForgotPassword){
            startActivity(new Intent(SignInActivity.this,ForgotPassword.class));
        }

    }

    private void userSign() {

        userEmail = mEmail.getText().toString().trim();
        userPassword = mPassword.getText().toString().trim();

        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(SignInActivity.this,"Enter Email",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(SignInActivity.this,"Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }

        mDialog.setMessage("Login please wait...");
        mDialog.setIndeterminate(true);
        mDialog.show();

        mAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if( !task.isSuccessful() ){
                    mDialog.dismiss();
                    Toast.makeText(SignInActivity.this,"Login not successful!",Toast.LENGTH_LONG).show();
                }else{
                    mDialog.dismiss();
                    //Check if the user is Admin or not
                    //mAuth = FirebaseAuth.getInstance();
                    //mFirebaseUser = mAuth.getCurrentUser();
                    //mUserId = mFirebaseUser.getUid();
                    //mDatabase = FirebaseDatabase.getInstance().getReference("Admins");

                   // mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
  //                      @Override
 //                       public void onDataChange(DataSnapshot snapshot) {
//                            if (snapshot.child(mUserId).exists()) {
//                                Log.i("TYPE","GO TO ADMIN");
//                                Intent intent = new Intent(SignInActivity.this, AdminMenuActivity.class);
//                                startActivity(intent);
//                                finish();
//                            }else
//                            {
                                Intent intent = new Intent(SignInActivity.this, MenuActivity.class);
                                startActivity(intent);
                                finish();
//                            }
                    //    }

//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//


                }
            }
        });

    }
}
