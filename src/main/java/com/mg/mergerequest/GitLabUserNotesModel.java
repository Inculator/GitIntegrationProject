package com.mg.mergerequest;

import org.gitlab.api.models.GitlabUser;

public class GitLabUserNotesModel {

    public static final String URL = "/notes";

    private Integer id;
    private String body;
    private String attachment;
    private GitlabUser author;
    private boolean system;
    private boolean upvote;
    private boolean downvote;
    private String type;
    private DiscussionPositionModel position;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public GitlabUser getAuthor() {
        return author;
    }

    public void setAuthor(GitlabUser author) {
        this.author = author;
    }

    public boolean isSystem() {
        return system;
    }

    public void setSystem(boolean system) {
        this.system = system;
    }

    public boolean isUpvote() {
        return upvote;
    }

    public void setUpvote(boolean upvote) {
        this.upvote = upvote;
    }

    public boolean isDownvote() {
        return downvote;
    }

    public void setDownvote(boolean downvote) {
        this.downvote = downvote;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DiscussionPositionModel getPosition() {
        return position;
    }

    public void setPosition(DiscussionPositionModel position) {
        this.position = position;
    }
}
