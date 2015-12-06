package com.githublist1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.repodb.Repository;

import java.util.List;

/**
 * Created by Valentyn on 27.11.2015.
 */
class GitHubAdapter extends ArrayAdapter<Repository> {

    List<Repository> repositoryList;
    Context context;

    public GitHubAdapter(Context context, List<Repository> repositoryList) {
        super(context, R.layout.multi_item, repositoryList);
        this.repositoryList = repositoryList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row;//todo normal VH?
        if (convertView == null) { // Create new row view object
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(R.layout.multi_item, parent, false);
        } else
            // reuse old row view to save time/battery
            row = convertView;

        TextView name = (TextView) row.findViewById(R.id.repName);
        name.setText(repositoryList.get(position).getName());
        TextView stars = (TextView) row.findViewById(R.id.star);
        stars.setText(repositoryList.get(position).getStargazers_count());
        TextView forks = (TextView) row.findViewById(R.id.fork);
        forks.setText(repositoryList.get(position).getForks());
        TextView watchers = (TextView) row.findViewById(R.id.watcher);
        watchers.setText(repositoryList.get(position).getWatchers());
        return row;
    }

    @Override
    public Repository getItem(int position) {
        return super.getItem(position);
    }
}