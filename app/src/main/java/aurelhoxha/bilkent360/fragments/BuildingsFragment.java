package aurelhoxha.bilkent360.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import aurelhoxha.bilkent360.R;
import aurelhoxha.bilkent360.activity.MapsActivity;
import aurelhoxha.bilkent360.other.Building;
import aurelhoxha.bilkent360.other.BuildingList;

public class BuildingsFragment extends Fragment
{

    private ListView listOfBuildings;
    private DatabaseReference mDatabase;
    private List<Building> buildingListi;
    public static final String BUILDING_NAME = "BuildingName";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myFragmentView = inflater.inflate(R.layout.fragment_buildings, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference("Buildings");
        listOfBuildings = (ListView)myFragmentView.findViewById(R.id.aurelListView);
        buildingListi = new ArrayList<>();

        listOfBuildings.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                Building myBuilding = buildingListi.get(i);
                Intent intent = new Intent(getActivity(), MapsActivity.class);

                intent.putExtra(BUILDING_NAME, myBuilding.getName());
                startActivity(intent);
            }
        });
        return myFragmentView;
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

                BuildingList adapter = new BuildingList(getActivity(), buildingListi);
                listOfBuildings.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
