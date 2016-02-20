package example.com.Categories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import example.com.task_reminder.R;

/**
 * Created by Toshiba on 20-Jan-16.
 */
public class CustomAdapter extends BaseAdapter {

    Context mContext;
    int Resource_id;
    public List<String> Category_List;

    public CustomAdapter(List<String> category_List, int resource_id, Context mContext) {
        Category_List = category_List;
        Resource_id = resource_id;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return Category_List.size();
    }

    @Override
    public Object getItem(int position) {
        return Category_List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // ImageButton ImgBtn;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(Resource_id,parent,false);
        }
        // ImgBtn=(ImageButton)convertView.findViewById(R.id.SelectCat);
        ((TextView)convertView.findViewById(R.id.textViewCat)).setText(Category_List.get(position));

        return convertView;
    }
}
