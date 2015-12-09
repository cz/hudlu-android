package zheng.craig.hudlu.models;

import io.realm.RealmObject;

/**
 * Created by cz on 11/29/15.
 */
public class Favorite extends RealmObject {
    private String title;
    private String author;
    private String image;
    private String link;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getImage() {
        return image;
    }

    public String getLink() {
        return link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
