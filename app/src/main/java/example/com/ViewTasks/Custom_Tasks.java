package example.com.ViewTasks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import example.com.task_reminder.R;

/**
 * Created by Toshiba on 26-Jan-16.
 */
public class Custom_Tasks extends BaseAdapter {

    Context mContext;
    int Resource_id;
    public List<String> Task_List;

    public Custom_Tasks(List<String> task_List, int resource_id, Context mContext) {
        Task_List = task_List;
        Resource_id = resource_id;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return Task_List.size();
    }

    @Override
    public Object getItem(int position) {
        return Task_List.get(position);
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
        ((TextView)convertView.findViewById(R.id.textViewtasks)).setText(Task_List.get(position));

        return convertView;
    }
}
