package example.com.Model;

/**
 * Created by Toshiba on 20-Jan-16.
 */
public class Task {

    private String name;
    private String description;
    private String date;
    private String time;
    private String category;

    public Task(String name, String description, String date, String time,String category) {
        this.name = name;
        this.description = description;
        this. date = date;
        this.time = time;
        this.category = category;
    }
    public Task(){}

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public void setCategory(String category) {
        category = category;
    }

    public String getTime() {
        return time;
    }

    public void setName(String name) {
        name = name;
    }

    public void setDescription(String description) {
        description = description;
    }


    public void setDate(String date) {
        date = date;
    }

    public void setTime(String time) {
        time = time;
    }
}
