package example.com.Settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.snapshot.ChildKey;

import java.util.List;

import example.com.task_reminder.R;

/**
 * Created by Toshiba on 22-Jan-16.
 */
public class SettingsAdapter extends BaseAdapter {

    Context mContext;
    int Resource_id;
    public List<String> Setting_List;

    public SettingsAdapter(Context mContext, int resource_id, List<String> setting_List) {
        this.mContext = mContext;
        Resource_id = resource_id;
        Setting_List = setting_List;
    }

    @Override

    public int getCount() {
        return Setting_List.size();
    }

    @Override
    public Object getItem(int position) {
        return Setting_List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(Resource_id,parent,false);
        }
        final CheckBox chkbx = (CheckBox)convertView.findViewById(R.id.chkbxSettings);
        ((TextView)convertView.findViewById(R.id.txtItem)).setText(Setting_List.get(position));



        /*chkbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (chkbx.isChecked())
                    Toast.makeText(mContext,position,Toast.LENGTH_SHORT).show();
            }
        });*/

        return convertView;
    }
}
