package com.mg.mergerequest;

@SuppressWarnings({"squid:S00116", "squid:S00100", "squid:S00117"})
public class DiscussionPositionModel {

    private String old_path;
    private String new_path;
    private String old_line;
    private String new_line;

    public String getOld_path() {
        return old_path;
    }

    public void setOld_path(String old_path) {
        this.old_path = old_path;
    }

    public String getNew_path() {
        return new_path;
    }

    public void setNew_path(String new_path) {
        this.new_path = new_path;
    }

    public String getOld_line() {
        return old_line;
    }

    public void setOld_line(String old_line) {
        this.old_line = old_line;
    }

    public String getNew_line() {
        return new_line;
    }

    public void setNew_line(String new_line) {
        this.new_line = new_line;
    }
}
