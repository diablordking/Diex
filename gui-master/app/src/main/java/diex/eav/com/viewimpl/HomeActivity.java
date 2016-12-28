package diex.eav.com.viewimpl;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import diex.eav.com.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class HomeActivity extends FragmentActivity
{
    private GoogleApiClient client;
    private SignInButton googleBtn;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private static final String TAG = "Main Activity";
    private FirebaseAuth.AuthStateListener mAuthListener;
   static String resultt;
    public static final int RC_SIGN_IN = 1;

  //   TextView txtName, txtEmail;

    Button btnSignIn,btnSignUp;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // create a instance of SQLite Database
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        // Get The Refference Of Buttons
//        txtName = (TextView) txtName.findViewById(R.id.tv_name);
        btnSignIn = (Button) findViewById(R.id.buttonSignIN);
        btnSignUp = (Button) findViewById(R.id.buttonSignUP);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null) {
                Toast.makeText(HomeActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                Intent itn = new Intent(HomeActivity.this, MainActivity.class);

               // txtName.setText(resultt);
                startActivity(itn);
            }
        };

        googleBtn = (SignInButton) findViewById(R.id.googleBtn);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(HomeActivity.this, connectionResult -> Toast.makeText(HomeActivity.this, "Error", Toast.LENGTH_LONG).show())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn2();
            }
        });

// Set OnClick Listener on SignUp button
        btnSignUp.setOnClickListener(v -> {
// TODO Auto-generated method stub

/// Create Intent for SignUpActivity abd Start The Activity
            Intent intentSignUP = new Intent(HomeActivity.this, SignUPActivity.class);
            startActivity(intentSignUP);
        });

    }
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    private void signIn2() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
/**
public  static String  getprofile(){
    return resultt;
}
**/
    // Methos to handleClick Event of Sign In Button
    public void signIn(View V)
    {
       // final Dialog dialog = new Dialog(HomeActivity.this);
       setContentView(R.layout.login);
       // dialog.setTitle("Login");

// get the Refferences of views
        final EditText editTextUserName=(EditText)findViewById(R.id.editTextUserNameToLogin);
        final EditText editTextPassword=(EditText)findViewById(R.id.editTextPasswordToLogin);

        Button btnSignIn=(Button)findViewById(R.id.buttonSignIn);

// Set On ClickListener
        btnSignIn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
// get The User name and Password
                String userName=editTextUserName.getText().toString();
                String password=editTextPassword.getText().toString();

// fetch the Password form database for respective user name
                String storedPassword=loginDataBaseAdapter.getSinlgeEntry(userName);

// check if the Stored password matches with Password entered by user
                if(password.equals(storedPassword))
                {
                    Toast.makeText(HomeActivity.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
                    Intent main=new Intent(HomeActivity.this,MainActivity.class);
                    startActivity(main);
                }
                else
                {
                    Toast.makeText(HomeActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
// Close The Database
        loginDataBaseAdapter.close();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
              //  handleSignInResult(result);
                resultt=account.getDisplayName();
                Log.d(TAG,account.getDisplayName()+"anan anan anan anananananananannananananananananananan");
                Log.e(TAG,account.getDisplayName()+"anan anan anan anananananananannananananananananananan");
                Log.i(TAG,account.getDisplayName()+"anan anan anan anananananananannananananananananananan");
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }


    public void firebaseAuthWithGoogle(GoogleSignInAccount account){
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "signInWithCredential", task.getException());
                        Toast.makeText(HomeActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                    // ...
                });

    }

}