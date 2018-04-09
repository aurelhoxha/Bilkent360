package aurelhoxha.bilkent360.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import aurelhoxha.bilkent360.R;
import aurelhoxha.bilkent360.other.Task;
import aurelhoxha.bilkent360.other.TaskList;


public class TaskFragment extends Fragment
{
    private ImageButton addTaskButton;
    private EditText toDoTexti;
    private ListView listOfTasks;
    private DatabaseReference mDatabase;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private List<Task> taskLists;
    private String mUserId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myFragmentView = inflater.inflate(R.layout.fragment_task, container, false);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUserId = mFirebaseUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(mUserId).child("Tasks");
        addTaskButton = (ImageButton)myFragmentView.findViewById(R.id.addButton);
        toDoTexti = (EditText)myFragmentView.findViewById(R.id.todoText);
        listOfTasks = (ListView)myFragmentView.findViewById(R.id.listView);
        taskLists = new ArrayList<>();

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTasks();

            }
        });
        listOfTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task myTask = (Task)listOfTasks.getItemAtPosition(position);
                String title = myTask.getTitle();
                Query clickedElement = mDatabase.orderByChild("title").equalTo(title);
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
                taskLists.clear();
                for(DataSnapshot taskSnapshot: dataSnapshot.getChildren())
                {
                    Task mylistTask = taskSnapshot.getValue(Task.class);
                    taskLists.add(mylistTask);
                }

                TaskList adapter = new TaskList(getActivity(), taskLists);
                listOfTasks.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void addTasks()
    {
        String taskInfo;
        if(!toDoTexti.getText().toString().isEmpty()){
            taskInfo = toDoTexti.getText().toString();
            Task myNewTask = new Task(taskInfo);
            mDatabase.push().setValue(myNewTask);
            Toast.makeText(getActivity(), "Task Added Successfully", Toast.LENGTH_SHORT).show();
            toDoTexti.setText("");
        }
        else
            Toast.makeText(getActivity(), "Fill Fields Correct!", Toast.LENGTH_SHORT).show();
    }
}
