package com.repodb;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class RepositoryDataSource {

    private SQLiteDatabase database;
    private RepositoryHelper dbHelper;
    private int version = 1;

    private String[] allColumns = {RepositoryHelper.COLUMN_ID, RepositoryHelper.COLUMN_NAME,
            RepositoryHelper.COLUMN_STARS, RepositoryHelper.COLUMN_FORKS, RepositoryHelper.COLUMN_WATCHERS,
            RepositoryHelper.COLUMN_FULL_NAME, RepositoryHelper.COLUMN_DESCRIPTION,
            RepositoryHelper.COLUMN_URL, RepositoryHelper.COLUMN_LANGUAGE};

    public RepositoryDataSource(Context context) {
        dbHelper = new RepositoryHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void dropAndUpgrade(){dbHelper.onUpgrade(database, version, version++ );}//do u feel shame, right?

    public Repository createRepository(String name, String stars, String forks, String watchers, String full_name,
                                       String description, String url, String language) {
        ContentValues values = new ContentValues();
        values.put(RepositoryHelper.COLUMN_NAME, name);
        values.put(RepositoryHelper.COLUMN_STARS, stars);
        values.put(RepositoryHelper.COLUMN_FORKS, forks);
        values.put(RepositoryHelper.COLUMN_WATCHERS, watchers);
        values.put(RepositoryHelper.COLUMN_FULL_NAME, full_name);
        values.put(RepositoryHelper.COLUMN_DESCRIPTION, description);
        values.put(RepositoryHelper.COLUMN_URL, url);
        values.put(RepositoryHelper.COLUMN_LANGUAGE, language);
        long insertId = database.insert(RepositoryHelper.TASKS_TABLE_NAME, null, values);
        Cursor cursor = database.query(RepositoryHelper.TASKS_TABLE_NAME, allColumns,
                RepositoryHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Repository newRepository = cursorToRepository(cursor);
        cursor.close();
        return newRepository;

    }

    ///will be used for fetching
    public void createRepositories(List<Repository> repositoryList) {
        ContentValues values = new ContentValues();
        for (Repository repository : repositoryList) {

            values.put(RepositoryHelper.COLUMN_NAME, repository.getName());
            values.put(RepositoryHelper.COLUMN_STARS, repository.getStargazers_count());
            values.put(RepositoryHelper.COLUMN_FORKS, repository.getForks());
            values.put(RepositoryHelper.COLUMN_WATCHERS, repository.getWatchers());
            values.put(RepositoryHelper.COLUMN_FULL_NAME, repository.getFull_name());
            values.put(RepositoryHelper.COLUMN_DESCRIPTION, repository.getDescription());
            values.put(RepositoryHelper.COLUMN_URL, repository.getHtml_url());
            values.put(RepositoryHelper.COLUMN_LANGUAGE, repository.getLanguage());

            database.insert(RepositoryHelper.TASKS_TABLE_NAME, null, values);
        }
    }

    ///

    public void deleteRepository(Repository repository) {
        long id = repository.getId();
        System.out.println("Repository deleted with id: " + id);
        database.delete(RepositoryHelper.TASKS_TABLE_NAME, RepositoryHelper.COLUMN_ID + " = " + id, null);
    }

    //////////////////////////////////////
    public Repository getRepository(long repositoryId) {
        String restrict = RepositoryHelper.COLUMN_ID + "=" + repositoryId;
        Cursor cursor = database.query(true, RepositoryHelper.TASKS_TABLE_NAME, allColumns, restrict, null, null, null,
                null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            Repository repository = cursorToRepository(cursor);
            return repository;
        }
        // Make sure to close the cursor
        cursor.close();
        return null;
    }

    public boolean updateRepository(long repositoryId, String name, String stars, String forks, String watchers, String full_name, String description, String url, String language) {
        ContentValues values = new ContentValues();
        values.put(RepositoryHelper.COLUMN_NAME, name);
        values.put(RepositoryHelper.COLUMN_STARS, stars);
        values.put(RepositoryHelper.COLUMN_FORKS, forks);
        values.put(RepositoryHelper.COLUMN_WATCHERS, watchers);
        values.put(RepositoryHelper.COLUMN_FULL_NAME, full_name);
        values.put(RepositoryHelper.COLUMN_DESCRIPTION, description);
        values.put(RepositoryHelper.COLUMN_URL, url);
        values.put(RepositoryHelper.COLUMN_LANGUAGE, language);

        String restrict = RepositoryHelper.COLUMN_ID + "=" + repositoryId;
        return database.update(RepositoryHelper.TASKS_TABLE_NAME, values, restrict, null) > 0;
    }

    public List<Repository> getAllRepositories() {
        List<Repository> repositories = new ArrayList<Repository>();

        Cursor cursor = database.query(RepositoryHelper.TASKS_TABLE_NAME, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Repository repository = cursorToRepository(cursor);
            repositories.add(repository);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return repositories;
    }

    /////////////////////////////////////
    private Repository cursorToRepository(Cursor cursor) {
        Repository repository = new Repository();
        repository.setId(cursor.getLong(0));
        repository.setName(cursor.getString(1));
        repository.setStargazers_count(cursor.getString(2));
        repository.setForks(cursor.getString(3));
        repository.setWatchers(cursor.getString(4));
        repository.setFull_name(cursor.getString(5));
        repository.setDescription(cursor.getString(6));
        repository.setHtml_url(cursor.getString(7));
        repository.setLanguage(cursor.getString(8));
        return repository;
    }
}
