package ie.com.lovetotravel.models;

import java.io.Serializable;

public class Journal implements Serializable {

    String title, date, entry;
    boolean favourite;

    public Journal(){

    }

    public Journal(String title, String entry, String date, boolean favourite) {
        this.title = title;
        this.date = date;
        this.entry = entry;
        this.favourite = favourite;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    @Override
    public String toString() {
        return title + ", " + date + ", " + entry
                + ", fav =" + favourite;
    }
}
