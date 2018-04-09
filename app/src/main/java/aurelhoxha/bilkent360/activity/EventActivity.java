package aurelhoxha.bilkent360.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import aurelhoxha.bilkent360.R;
import aurelhoxha.bilkent360.fragments.AdminBuildingFragment;
import aurelhoxha.bilkent360.other.Building;
import aurelhoxha.bilkent360.other.BuildingList;
import aurelhoxha.bilkent360.other.Event;
import aurelhoxha.bilkent360.other.EventList;

/**
 * Created by aurel on 7/12/17.
 */

public class EventActivity extends AppCompatActivity
{

    private ListView listOfEvents;
    private DatabaseReference mDatabase;
    private List<Event> eventListi;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        mDatabase = FirebaseDatabase.getInstance().getReference("Events");

        listOfEvents = (ListView)findViewById(R.id.eventListView);
        eventListi = new ArrayList<>();

        listOfEvents.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                final Event myEvent = eventListi.get(i);
                final String date = myEvent.getDate();
                String eventTitle = myEvent.getEventName();
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(EventActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_dialog_events,null);

                final EditText eDate = (EditText) mView.findViewById(R.id.eventDate);
                final EditText eName = (EditText) mView.findViewById(R.id.eventName);

                eDate.setText(date);
                eName.setText (eventTitle);

                TextView mAdd = (TextView) mView.findViewById(R.id.btnModifyEvent);
                TextView mCalcel = (TextView) mView.findViewById(R.id.btnCalcelEvent);
                TextView mExit = (TextView) mView.findViewById(R.id.btnExitDialog);



                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                mAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String title = myEvent.getEventName();
                        Log.i("EVENT",title);
                        Query clickedElement = mDatabase.orderByChild("eventName").equalTo(title);
                        clickedElement.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot taskSnapshot: dataSnapshot.getChildren()) {
                                    final Event newEvent =  new Event(eDate.getText().toString(), eName.getText().toString());
                                    taskSnapshot.getRef().setValue(newEvent);
                                }
                                Toast.makeText(getApplicationContext(), "Event Modified Successfully", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });

                mCalcel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = myEvent.getEventName();
                        Query clickedElement = mDatabase.orderByChild("eventName").equalTo(title);
                        clickedElement.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot taskSnapshot: dataSnapshot.getChildren()) {
                                    taskSnapshot.getRef().removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        Toast.makeText(getApplicationContext(), "Event Deleted Successfully", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                mExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventListi.clear();
                for(DataSnapshot buildingSnapshot: dataSnapshot.getChildren())
                {
                    Event myListEvent = buildingSnapshot.getValue(Event.class);
                    eventListi.add(myListEvent);
                }

                EventList adapter = new EventList(EventActivity.this, eventListi);
                listOfEvents.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
