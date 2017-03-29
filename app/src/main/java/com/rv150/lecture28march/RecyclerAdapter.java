package com.rv150.lecture28march;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivan on 28.03.17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {

    private List<Repository> repositories;

    RecyclerAdapter() {
        repositories = new ArrayList<>();
    }

    RecyclerAdapter(List<Repository> repositories) {
        this.repositories = repositories;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_item, parent, false);
        return new ItemViewHolder(view);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;

        ItemViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.repo_name);
        }

        void bind(Repository repository) {
            name.setText(repository.getName());
        }
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Repository repository = repositories.get(position);
        holder.bind(repository);
    }


    @Override
    public int getItemCount() {
        return repositories.size();
    }

    public void setData(List<Repository> repositories) {
        this.repositories = repositories;
        notifyDataSetChanged();
    }
}