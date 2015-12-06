package com.repodb;


import android.os.Parcel;
import android.os.Parcelable;

public class Repository implements Parcelable {
    private long id;
    private String name;
    private String stargazers_count;
    private String forks;
    private String watchers;
    private String full_name;
    private String description;
    private String html_url;
    private String language;

    public Repository(Parcel in) {
        id = in.readLong();
        name = in.readString();
        stargazers_count = in.readString();
        forks = in.readString();
        watchers = in.readString();
        full_name = in.readString();
        description = in.readString();
        html_url = in.readString();
        language = in.readString();

    }

    public Repository() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(String string) {
        this.stargazers_count = string;
    }

    public String getForks() {
        return forks;
    }

    public void setForks(String forks) {
        this.forks = forks;
    }

    public String getWatchers() {
        return watchers;
    }

    public void setWatchers(String watchers) {
        this.watchers = watchers;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "Repository [id=" + id + ", name=" + name + ", stargazers_count=" + stargazers_count + ", forks=" + forks + ", watchers="
                + watchers + ", full_name=" + full_name + ", description=" + description + "" + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(stargazers_count);
        dest.writeString(forks);
        dest.writeString(watchers);
        dest.writeString(full_name);
        dest.writeString(description);
        dest.writeString(html_url);
        dest.writeString(language);
    }

    public static final Parcelable.Creator<Repository> CREATOR = new Parcelable.Creator<Repository>() {
        public Repository createFromParcel(Parcel in) {
            return new Repository(in);
        }

        public Repository[] newArray(int size) {
            return new Repository[size];
        }
    };
}

