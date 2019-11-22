package samar.abozid.personalassistant;


import android.content.Intent;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import samar.abozid.personalassistant.Models.EventModel;

public class EventsActivity extends AppCompatActivity
{
    FloatingActionButton floatingActionButton;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    List<EventModel> list;
    PresentAdapter presentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        floatingActionButton = findViewById(R.id.add_event);
        recyclerView = findViewById(R.id.recyclerview);

        list = new ArrayList<>();

        list.add(new EventModel("Graduation project presentation" , "23 April, 2019"));
        list.add(new EventModel("Interview" , "15 July, 2019"));

        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        presentAdapter = new PresentAdapter(list);

        recyclerView.setAdapter(presentAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener()
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
                        .putExtra(CalendarContract.Events.TITLE, "Fun Day ..")
                        .putExtra(CalendarContract.Events.DESCRIPTION, "")
                        .putExtra(CalendarContract.Events.RRULE, "FREQ=DAILY;COUNT=10")
                        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                        .putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
                startActivity(intent);
                list.add(new EventModel("Fun day" , "24 April, 2019"));
                presentAdapter.notifyDataSetChanged();
            }
        });
    }

    public class PresentAdapter extends RecyclerView.Adapter<PresentAdapter.PresentViewHolder>
    {
        List<EventModel> list;

        PresentAdapter(List<EventModel> list)
        {
            this.list = list;
        }

        @NonNull
        @Override
        public PresentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
        {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.event_item, viewGroup, false);
            return new PresentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PresentViewHolder presentViewHolder, int i)
        {
            final String day = list.get(i).getTitle();
            final String time = list.get(i).getTime();

            presentViewHolder.title.setText(day);
            presentViewHolder.time_txt.setText(time);
        }

        @Override
        public int getItemCount()
        {
            return list.size();
        }

        class PresentViewHolder extends RecyclerView.ViewHolder
        {
            TextView title,time_txt;

            PresentViewHolder(@NonNull View itemView)
            {
                super(itemView);

                title = itemView.findViewById(R.id.event_title_txt);
                time_txt = itemView.findViewById(R.id.event_time_txt);
            }
        }
    }
}
