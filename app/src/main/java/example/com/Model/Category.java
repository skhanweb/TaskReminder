package example.com.Model;

import com.firebase.client.Firebase;

import java.util.List;

/**
 * Created by Toshiba on 20-Jan-16.
 */
public class Category {

    List<String> CategoryList;
    String name;
    Firebase Myref;


    public Category()
    {

    }


    public List<String> getCategoryList() {
        return CategoryList;
    }

    public void setCategoryList(List<String> categoryList) {
        CategoryList = categoryList;
    }


    public Category(String name) {name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }



}
