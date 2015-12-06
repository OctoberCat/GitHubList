package com.githublist1;

import android.app.Activity;
import android.app.FragmentTransaction;

import android.os.Bundle;


import com.repodb.Repository;


public class MainActivity extends Activity implements RepoListFragment.OnRepositorySelectedListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping com.gihublist.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of ExampleFragment
            RepoListFragment firstFragment = new RepoListFragment();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    public void onRepositorySelected(Repository selectedRepository) {
        // The user selected the headline of an article from the RepositoryListFragment

        // Capture the article fragment from the activity layout
        RepoDetailFragment detailFragment = (RepoDetailFragment)
                getFragmentManager().findFragmentById(R.id.details_fragment);

        if (detailFragment != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the DetailsFragment to update its content
            detailFragment.updateRepositoryDetail(selectedRepository);

        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            RepoDetailFragment newFragment = new RepoDetailFragment();
            Bundle args = new Bundle();
            args.putParcelable(RepoDetailFragment.ARG_POSITION, selectedRepository);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);


            transaction.commit();
        }
    }
}
