package samar.abozid.personalassistant;

import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import samar.abozid.personalassistant.Models.EventModel;

public class EventDetailsActivity extends AppCompatActivity
{
    MaterialRippleLayout add_event_mrl;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        add_event_mrl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar calendar = Calendar.getInstance();

                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setType("vnd.android.cursor.item/event")
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calendar.getTimeInMillis())
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calendar.getTimeInMillis())
                        .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY , false) // just included for completeness
                        //.putExtra(CalendarContract.Events.TITLE, "Default Title ..")
                        //.putExtra(CalendarContract.Events.DESCRIPTION, "Default Description ..")
                        .putExtra(CalendarContract.Events.RRULE, "FREQ=DAILY;COUNT=10")
                        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                        .putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
                startActivity(intent);

                //EventModel eventModel = new EventModel("Default Title ..", calendar.getTimeInMillis());

                //String event_key = databaseReference.child("Events").child(getUID()).push().getKey();
                //databaseReference.child("Events").child(getUID()).child(event_key).setValue(eventModel);
            }
        });
    }

    private String getUID()
    {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return id;
    }
}
