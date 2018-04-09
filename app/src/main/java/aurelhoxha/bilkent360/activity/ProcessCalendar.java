package aurelhoxha.bilkent360.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import aurelhoxha.bilkent360.R;
import aurelhoxha.bilkent360.other.Event;

/**
 * Created by aurel on 6/26/17.
 */

public class ProcessCalendar  extends AppCompatActivity {

    private TextView textOfDate;
    private ImageButton addTheDate;
    private EditText calendarEvent;
    private Button addEventi;
    private DatabaseReference mDatabase;
    private DatePickerDialog.OnDateSetListener mDataSetListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_calendar);

        mDatabase = FirebaseDatabase.getInstance().getReference("Events");
        textOfDate    = (TextView)findViewById(R.id.calendarShow);
        addTheDate    = (ImageButton)findViewById(R.id.calendarDateBtn);
        calendarEvent = (EditText)findViewById(R.id.calendarText);
        addEventi     = (Button)findViewById(R.id.calendarBtn);

        addEventi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEventInfo();
            }
        });
        addTheDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar myCal = Calendar.getInstance();
                int year = myCal.get(Calendar.YEAR);
                int month = myCal.get(Calendar.MONTH);
                int day = myCal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog myDialog = new DatePickerDialog(
                        ProcessCalendar.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDataSetListener,
                        year,month,day);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });

        mDataSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                textOfDate.setText(date);
            }
        };

    }

    private void getEventInfo()
    {
        if(!textOfDate.getText().toString().isEmpty() && !calendarEvent.getText().toString().isEmpty()){
            String dateToDatabase;
            String eventToDatabase;
            dateToDatabase = textOfDate.getText().toString().trim();
            eventToDatabase = calendarEvent.getText().toString().trim();
            Event myNewEvent = new Event(dateToDatabase,eventToDatabase);
            mDatabase.push().setValue(myNewEvent);
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
            Toast.makeText(ProcessCalendar.this, "Event has been Added Successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(ProcessCalendar.this, "Fill Fields Correct!", Toast.LENGTH_SHORT).show();
        }

    }
}

