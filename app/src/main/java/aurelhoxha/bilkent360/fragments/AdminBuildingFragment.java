package aurelhoxha.bilkent360.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import aurelhoxha.bilkent360.R;
import aurelhoxha.bilkent360.activity.AdminMenuActivity;
import aurelhoxha.bilkent360.activity.SignInActivity;
import aurelhoxha.bilkent360.other.Building;
import aurelhoxha.bilkent360.other.BuildingList;

/**
 * Created by aurel on 7/12/17.
 */

public class AdminBuildingFragment extends AppCompatActivity
{

    private ListView listOfBuildings;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1;
    private List<Building> buildingListi;
    public static final String BUILDING_NAME = "BuildingName";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_buildings);

        mDatabase = FirebaseDatabase.getInstance().getReference("RequestedBuildings");

        listOfBuildings = (ListView)findViewById(R.id.aurelListView);
        buildingListi = new ArrayList<>();

        listOfBuildings.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                final Building myBuilding = buildingListi.get(i);
                final String name = myBuilding.getName();
                String latValue = "" + myBuilding.getLatValue();
                String longValue = "" + myBuilding.getLongValue();
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AdminBuildingFragment.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_dialog_requested,null);

                final TextView mName = (TextView) mView.findViewById(R.id.buildingName);
                final TextView mLat = (TextView) mView.findViewById(R.id.buildingLatitude);
                final TextView mLong = (TextView) mView.findViewById(R.id.buildingLongitude);
                mName.setText("Building Name  : " + name);
                mLat.setText ("Latitude Value : " + latValue);
                mLong.setText("Longitude Value: " + longValue);

                TextView mAdd = (TextView) mView.findViewById(R.id.btnAcceptBuilding);
                TextView mCalcel = (TextView) mView.findViewById(R.id.btnCalcelBuilding);
                TextView mExit = (TextView) mView.findViewById(R.id.btnExitDialog);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                mAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDatabase1 = FirebaseDatabase.getInstance().getReference("Buildings");
                        mDatabase1.child(name).setValue(myBuilding);
                        Toast.makeText(getApplicationContext(), "Building Added Successfully", Toast.LENGTH_SHORT).show();
                        mDatabase.child(name).removeValue();
                        dialog.dismiss();
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_CANCELED, returnIntent);
                        finish();
                    }
                });

                mCalcel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDatabase.child(name).removeValue();
                        Toast.makeText(getApplicationContext(), "Building Deleted Successfully", Toast.LENGTH_SHORT).show();
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
                buildingListi.clear();
                for(DataSnapshot buildingSnapshot: dataSnapshot.getChildren())
                {
                    Building myListBuilding = buildingSnapshot.getValue(Building.class);
                    buildingListi.add(myListBuilding);
                }

                BuildingList adapter = new BuildingList(AdminBuildingFragment.this, buildingListi);
                listOfBuildings.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
