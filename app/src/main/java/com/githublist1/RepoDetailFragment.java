package com.githublist1;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.repodb.Repository;


public class RepoDetailFragment extends Fragment {
    final static String ARG_POSITION = "position";
    Repository currentRepository = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*ActionBar actionBar = getActivity().getActionBar(); // Version > v3.0
        actionBar.setDisplayHomeAsUpEnabled(true);*/

        if (savedInstanceState != null) {
            currentRepository = savedInstanceState.getParcelable(ARG_POSITION);
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repolist_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        if (args != null) {
            // Set repo based on argument passed in
            updateRepositoryDetail((Repository) args.getParcelable(ARG_POSITION));
        } else if (currentRepository != null) {
            // Set repo based on saved instance state defined during onCreateView
            updateRepositoryDetail(currentRepository);
        }
    }

    public void updateRepositoryDetail(Repository repoDetail) {
        TextView fullName = (TextView) getView().findViewById(R.id.full_name);
        TextView description = (TextView) getView().findViewById(R.id.description);
        TextView language = (TextView) getView().findViewById(R.id.language);
        TextView url = (TextView) getView().findViewById(R.id.url);
        fullName.setText(repoDetail.getFull_name());
        description.setText(repoDetail.getDescription());
        language.setText(repoDetail.getLanguage());
        url.setText(repoDetail.getHtml_url());
        currentRepository = repoDetail;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the current repo selection in case we need to recreate the fragment
        outState.putParcelable(ARG_POSITION, currentRepository);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
/*            case android.R.id.home:
                RepoListFragment newFragment = new RepoListFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.commit();
                return true;*/

        }
        return super.onOptionsItemSelected(item);
    }
}
