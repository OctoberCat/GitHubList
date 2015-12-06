package com.githublist1;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.repodb.Repository;
import com.repodb.RepositoryDataSource;

import java.util.ArrayList;
import java.util.List;


public class RepoListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<Repository>> {
    OnRepositorySelectedListener mCallback;
    RepositoryDataSource dataSource;
    ArrayList<Repository> repositoryList;//
    private static String SAVED_INST = "saved";
    GitHubAdapter mAdapter;


    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnRepositorySelectedListener {

        void onRepositorySelected(Repository repository);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            dataSource = new RepositoryDataSource(getActivity());
            dataSource.open();
            repositoryList = (ArrayList<Repository>) dataSource.getAllRepositories();
            dataSource.close();
            if (repositoryList != null) {
                mAdapter = new GitHubAdapter(getActivity(), repositoryList);
                setListAdapter(mAdapter);
            }
            getLoaderManager().initLoader(0, null, this).forceLoad();
        } else {
            repositoryList = savedInstanceState.getParcelableArrayList(SAVED_INST);
            setListAdapter(new GitHubAdapter(getActivity(), repositoryList));
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // When in two-pane layout, set the listview to highlight the selected list item
        // (We do this during onStart because at the point the listview is available.)
        if (getFragmentManager().findFragmentById(R.id.details_fragment) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnRepositorySelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnRepositorySelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Notify the parent activity of selected item

        mCallback.onRepositorySelected((Repository) l.getItemAtPosition(position));

        // Set the item as checked to be highlighted when in two-pane layout
        getListView().setItemChecked(position, true);
    }


    @Override
    public Loader<List<Repository>> onCreateLoader(int id, Bundle args) {
        return new GitHubLoader(getActivity(), isOnline());//clumsy way to pass Internet check
    }

    @Override
    public void onLoadFinished(Loader<List<Repository>> loader, List<Repository> data) {
        repositoryList = (ArrayList<Repository>) data;
        mAdapter.clear();
        mAdapter.addAll(repositoryList);
        // fire the event
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Repository>> loader) {

    }


    boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current repo selection in case we need to recreate the fragment
        outState.putParcelableArrayList(SAVED_INST, repositoryList);
    }

}
