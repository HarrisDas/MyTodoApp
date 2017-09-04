package com.example.harri.mytodoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;

    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerViewAdapter mRecyclerAdapter;

    private EditText mTaskEditText;
    public DatabaseReference databaseReference;
    private List<Task> listOfTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

       // setSupportActionBar(toolbar);
        listOfTask = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
       //editer
        mTaskEditText = (EditText) findViewById(R.id.add_task_box);
        //recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.task_list);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        // button
        Button addTaskButton = (Button) findViewById(R.id.add_task_button);

//if button is not null
        assert addTaskButton != null;
addTaskButton.setOnClickListener(new View.OnClickListener(){


    @Override
    public void onClick(View v) {
        String enteredTask = mTaskEditText.getText().toString();
        if(enteredTask==""){

            Toast.makeText(MainActivity.this, "You must enter a task to Add it",Toast.LENGTH_LONG).show();

            return;
        }
else{
            Task taskObject = new Task(enteredTask);
            databaseReference.push().setValue(taskObject);
            mTaskEditText.setText("");
        }
    }
});


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getAllTask(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getAllTask(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {
                taskDeletion(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void taskDeletion(DataSnapshot dataSnapshot) {


        for (DataSnapshot singleDataSnapshot : dataSnapshot.getChildren()) {
            String currentTask = singleDataSnapshot.getValue(String.class);

            for (int i = 0; i < listOfTask.size(); i++) {

                if (listOfTask.get(i).getTask().equals(currentTask)) {

                    listOfTask.remove(i);

                }
            }

            mRecyclerAdapter.notifyDataSetChanged();

            mRecyclerAdapter = new RecyclerViewAdapter(MainActivity.this, listOfTask);

            mRecyclerView.setAdapter(mRecyclerAdapter);

        }
    }


    private void getAllTask(DataSnapshot dataSnapshot) {

    for (DataSnapshot singleShot: dataSnapshot.getChildren()){

String newTask= singleShot.getValue(String.class);
        listOfTask.add(new Task(newTask));


        mRecyclerAdapter = new RecyclerViewAdapter(MainActivity.this,listOfTask);
        mRecyclerAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    }

    }

