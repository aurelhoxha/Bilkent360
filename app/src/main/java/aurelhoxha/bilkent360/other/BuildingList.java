package aurelhoxha.bilkent360.other;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import aurelhoxha.bilkent360.R;


/**
 * Created by aurel on 6/19/17.
 */

public class BuildingList extends ArrayAdapter<Building> {
    private Activity context;
    private List<Building> myBuildingList;

    public BuildingList(Activity context, List<Building> myBuildingList)
    {
        super(context, R.layout.activity_listlayout,myBuildingList);
        this.context = context;
        this.myBuildingList = myBuildingList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItems = inflater.inflate(R.layout.activity_listlayout,null,true);

        TextView textViewBuildingName = (TextView) listViewItems.findViewById(R.id.tvBuildingName);
        Building myBuilding = myBuildingList.get(position);
        textViewBuildingName.setText(myBuilding.getName());

        return listViewItems;
    }
}


