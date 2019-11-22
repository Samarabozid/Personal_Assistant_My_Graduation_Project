package samar.abozid.personalassistant;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.victor.loading.rotate.RotateLoading;

public class MainActivity extends AppCompatActivity
{
    TextView donthave;
    EditText email_field,password_field;
    Button sign_in_btn;

    String email_txt,password_txt;

    RotateLoading rotateLoading;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        donthave = findViewById(R.id.dont);
        email_field = findViewById(R.id.email_field);
        password_field = findViewById(R.id.password_field);
        sign_in_btn = findViewById(R.id.sign_in_btn);
        rotateLoading = findViewById(R.id.rotateloading);

        auth = FirebaseAuth.getInstance();

        donthave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        sign_in_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                email_txt = email_field.getText().toString();
                password_txt = password_field.getText().toString();

                if (TextUtils.isEmpty(email_txt))
                {
                    Toast.makeText(getApplicationContext(), "please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password_txt))
                {
                    Toast.makeText(getApplicationContext(), "please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                rotateLoading.start();

                UserLogin(email_txt,password_txt);
            }
        });
    }

    private void UserLogin(String email, String password)
    {
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                            startActivity(intent);
                        } else
                        {
                            rotateLoading.stop();
                            String taskmessage = task.getException().getMessage();
                            Toast.makeText(getApplicationContext(), taskmessage, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private long exitTime = 0;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void doExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000)
        {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finishAffinity();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed()
    {
        doExitApp();
    }
}
