package samar.abozid.personalassistant;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity
{

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null)
        {
            TimerTask task = new TimerTask()
            {
                @Override
                public void run()
                {
                    // go to the start activity
                    Intent i = new Intent(getApplicationContext(), StartActivity.class);
                    startActivity(i);
                    // kill current activity
                    finish();
                }
            };
            // Show splash screen for 3 seconds
            new Timer().schedule(task, 3000);
        } else
        {
            TimerTask task = new TimerTask()
            {
                @Override
                public void run()
                {
                    // go to the main activity
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    // kill current activity
                    finish();
                }
            };
            // Show splash screen for 3 seconds
            new Timer().schedule(task, 3000);
        }
    }
}