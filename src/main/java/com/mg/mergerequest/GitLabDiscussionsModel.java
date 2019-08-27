package com.mg.mergerequest;

import java.util.List;

public class GitLabDiscussionsModel {

    public static final String URL = "/discussions";

    private String id;
    private List<GitLabUserNotesModel> notes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<GitLabUserNotesModel> getNotes() {
        return notes;
    }

    public void setNotes(List<GitLabUserNotesModel> notes) {
        this.notes = notes;
    }
}
