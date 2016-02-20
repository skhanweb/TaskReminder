package example.com.Create;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import example.com.task_reminder.R;

/**
 * Created by Toshiba on 10-Jan-16.
 */
public class Tab_CreateList extends BaseAdapter {

    Context mContext;
    int Resource_id;
   public ArrayList<String> values;

    public Tab_CreateList(Context mContext, int resource_id, ArrayList<String> values) {
        this.mContext = mContext;
        Resource_id = resource_id;
        this.values =values;
    }

    @Override

    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(Resource_id,parent,false);
        }

        ((TextView) convertView.findViewById(R.id.txtView)).setText(values.get(position));
     //  RadioButton rdo = (RadioButton)convertView.findViewById(R.id.rdoCreate);
        return convertView;
    }
}
