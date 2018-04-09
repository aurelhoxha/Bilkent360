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
 * Created by aurel on 6/24/17.
 */

public class TaskList  extends ArrayAdapter<Task>
{
    private Activity context;
    private List<Task> myTaskList;

    public TaskList(Activity context, List<Task> myTaskList)
    {
        super(context, R.layout.activity_listlayout,myTaskList);
        this.context = context;
        this.myTaskList = myTaskList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItems = inflater.inflate(R.layout.tasklayout,null,true);

        TextView textViewTaskName = (TextView) listViewItems.findViewById(R.id.taskName);
        Task myTask = myTaskList.get(position);
        textViewTaskName.setText(myTask.getTitle());

        return listViewItems;
    }
}