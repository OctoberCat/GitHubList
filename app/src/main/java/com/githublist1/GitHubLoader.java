package com.githublist1;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.repodb.Repository;
import com.repodb.RepositoryDataSource;
import com.rest.RepositoryAPI;
import com.rest.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;

/**
 * Created by Valentyn on 02.12.2015.
 */
class GitHubLoader extends AsyncTaskLoader<List<Repository>> {
    List<Repository> result = new ArrayList<Repository>();
    RepositoryDataSource repoDS;
    private final String TAG = "inside loader";
    boolean isOnline;

    public GitHubLoader(Context context, boolean isOnline) {
        super(context);
        this.isOnline = isOnline;
        repoDS = new RepositoryDataSource(context);
        repoDS.open();

    }

    @Override
    public List<Repository> loadInBackground() {
        Log.i(TAG, "start background");
        if (isOnline) {//get from API and update DB
            RepositoryAPI client = ServiceGenerator.createService(RepositoryAPI.class);
            //Fetch and print the list
            Call<List<Repository>> call = client.getRepo();
            try {
                result = call.execute().body();

                for (Repository repository : result) {
                    Log.i(TAG, repository.toString());
                }

                repoDS.dropAndUpgrade();
                repoDS.createRepositories(result);
                repoDS.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {

            result = repoDS.getAllRepositories();
            repoDS.close();
        }
        return result;
    }


}