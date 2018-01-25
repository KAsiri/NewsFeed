package khalidalasiri.newsfeed;

/**
 * Created by kasir on 1/23/2018.
 */

public class News {
    private String title;
    private String section;
    private String author;
    private String date;

    public News(String title, String section, String author, String date) {
        this.title = title;
        this.section = section;
        this.author = author;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}