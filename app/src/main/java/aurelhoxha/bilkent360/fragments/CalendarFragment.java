package aurelhoxha.bilkent360.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import aurelhoxha.bilkent360.R;
import aurelhoxha.bilkent360.other.Event;

public class CalendarFragment extends Fragment {

    private CalendarView myCalendar;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myFragmentView = inflater.inflate(R.layout.fragment_calendar, container, false);
        myCalendar  = (CalendarView)myFragmentView.findViewById(R.id.calendar);
        mDatabase = FirebaseDatabase.getInstance().getReference("Events");

        myCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                final String date = dayOfMonth + "/" + month + "/" + year;

                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot eventSnapshot: dataSnapshot.getChildren())
                        {
                            Event myEvent = eventSnapshot.getValue(Event.class);
                            if(myEvent.getDate().equals(date)) {
                                Toast.makeText(getActivity(), "Event:"+myEvent.getEventName(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
        // Inflate the layout for this fragment
        return myFragmentView;

    }

}
