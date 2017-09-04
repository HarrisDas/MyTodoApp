package com.example.harri.mytodoapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by harri on 7/9/2017.
 */


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {


    private List<Task> tasks;

    protected Context context;
    public RecyclerViewAdapter(Context context, List<Task> listOfTask) {
        this.tasks = listOfTask;

        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolders viewHolders = null;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_list, parent,false);

        viewHolders = new RecyclerViewHolders(view,tasks);
        return viewHolders;
        }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {

       holder.categoryTitle.setText(tasks.get(position).getTask());
    }

    @Override
    public int getItemCount() {
        return this.tasks.size();    }
}
