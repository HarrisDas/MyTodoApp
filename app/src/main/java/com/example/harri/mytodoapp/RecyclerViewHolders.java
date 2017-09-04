package com.example.harri.mytodoapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by harri on 7/9/2017.
 */
public class RecyclerViewHolders extends RecyclerView.ViewHolder {

    private static final String TAG = RecyclerViewHolders.class.getSimpleName();
    public CheckBox markIcon;
    public TextView categoryTitle;
    public ImageView deleteIcon;
    public List<Task> taskObject;

    public RecyclerViewHolders(View itemView, final List<Task> taskObject) {
        super(itemView);

        this.taskObject = taskObject;
        categoryTitle = (TextView) itemView.findViewById(R.id.task_title);
        markIcon = (CheckBox) itemView.findViewById(R.id.task_icon);
        deleteIcon = (ImageView) itemView.findViewById(R.id.task_delete);

        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                String taskTitle = taskObject.get(getAdapterPosition()).getTask();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Query deletequery = reference.orderByChild("task").equalTo(taskTitle);
                deletequery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();

                            }
                        }


                            @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "Cancelled Exception", databaseError.toException());
                        }
                    });
                }
            });


    }

public void checkOn(View view){

   // view.setBackground(R.drawable.class.getResource("checkbox_on_background"));
}


    public RecyclerViewHolders(View itemView) {
        super(itemView);
    }
}
