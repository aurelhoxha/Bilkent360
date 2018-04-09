package aurelhoxha.bilkent360.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import aurelhoxha.bilkent360.R;

public class Schedule extends Activity implements OnItemSelectedListener {
    EditText course;
    Button showSchedule;
    Button addNewCourse;
    Button reset;
    Spinner day;
    Spinner firstHour, secondHour;
    int dayPos, firstHourPos, secondHourPos;
    String courseName;
    String asd, dayText;
    String[][] scheduleData = new String[9][5];
    public static final String PREFS = "bilkent360";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_schedule);

        initializeTableFromDB();

        course = (EditText) findViewById(R.id.getCourseName);
        showSchedule = (Button) findViewById(R.id.showSchedule);
        addNewCourse = (Button) findViewById(R.id.addNewCourse);
        reset = (Button) findViewById(R.id.reset);

        addItemsOnSpinnerHours();

        day = (Spinner) findViewById(R.id.daySpinner);
        firstHour = (Spinner) findViewById(R.id.hour1Spinner);
        secondHour = (Spinner) findViewById(R.id.hour2Spinner);

        day.setOnItemSelectedListener(this);
        firstHour.setOnItemSelectedListener(this);
        secondHour.setOnItemSelectedListener(this);


        addListenerOnButton();
    }

    private void initializeTableFromDB() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(PREFS, MODE_PRIVATE);

        String scheduleDataString = sharedPreferences.getString("scheduleData", "");
        if (scheduleDataString.length() > 0) {

        } else {
            scheduleData = new String[9][5];

        }
    }

    // add items into spinner dynamically
    public void addItemsOnSpinnerHours() {

        firstHour = (Spinner) findViewById(R.id.hour1Spinner);
        List<String> list = new ArrayList<String>();
        list.add("Starting Time..");
        list.add("08:40");
        list.add("09:40");
        list.add("10:40");
        list.add("11:40");
        list.add("12:40");
        list.add("13:40");
        list.add("14:40");
        list.add("15:40");
        list.add("16:40");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        firstHour.setAdapter(dataAdapter);

        secondHour = (Spinner) findViewById(R.id.hour2Spinner);
        List<String> list2 = new ArrayList<String>();
        list2.add("Finishing Time..");
        list2.add("09:30");
        list2.add("10:30");
        list2.add("11:30");
        list2.add("12:30");
        list2.add("13:30");
        list2.add("14:30");
        list2.add("15:30");
        list2.add("16:30");
        list2.add("17:30");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        secondHour.setAdapter(dataAdapter2);
    }

    public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {

        switch (parent.getId()) {
            case R.id.daySpinner:
                dayPos = pos; //1..5
                dayText = parent.getItemAtPosition(pos).toString();
                break;
            case R.id.hour1Spinner:
                firstHourPos = pos; //1..9
                break;
            case R.id.hour2Spinner:
                secondHourPos = pos; //1..9
                break;
        }
    }

    public void updateCellOnDB(int r, int c, String str) {
        if (r < 10 && r > 0 && c > 0 && c < 6) {

            int cell_r = r + 1;
            int cell_c = c;
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(PREFS, MODE_PRIVATE);
//            System.out.println("row" + cell_r + cell_c + " -> " + str);


            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("row" + cell_r + cell_c, str);
            editor.commit();

        }

    }

    public void addListenerOnButton() {
        addNewCourse.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                courseName = course.getText().toString();
                if (dayPos != 0 && firstHourPos != 0 && secondHourPos != 0 && courseName.length() > 0) {
                    if(firstHourPos>secondHourPos){
                        Toast.makeText(getApplicationContext(), "Starting time cannot be earlier than finishing time!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    for (int i = firstHourPos; i <= secondHourPos; i++) {
                        updateCellOnDB(i, dayPos, courseName);
                    }
                    Toast.makeText(getApplicationContext(), courseName+ " is added to schedule!", Toast.LENGTH_LONG).show();
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_CANCELED, returnIntent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please fill all fields!", Toast.LENGTH_LONG).show();
                }

            }
        });


        showSchedule.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Toast.makeText(getApplicationContext(), dayText, Toast.LENGTH_LONG).show();//it works
//                Toast.makeText(getApplicationContext(), courseName, Toast.LENGTH_LONG).show();// not works
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();

            }
        });

        reset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                SharedPreferences sharedPreferences = getSharedPreferences(PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(getApplicationContext(), "Schedule is cleaned!", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }

    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }
}

