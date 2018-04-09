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
 * Created by aurel on 7/12/17.
 */

public class EventList extends ArrayAdapter<Event> {
    private Activity context;
    private List<Event> myEventList;

    public EventList(Activity context, List<Event> myEventList)
    {
        super(context, R.layout.activity_events,myEventList);
        this.context = context;
        this.myEventList = myEventList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItems = inflater.inflate(R.layout.activity_events,null,true);

        TextView eventName = (TextView) listViewItems.findViewById(R.id.eventName);
        Event myEvent = myEventList.get(position);
        eventName.setText(myEvent.getEventName());

        return listViewItems;
    }
}
