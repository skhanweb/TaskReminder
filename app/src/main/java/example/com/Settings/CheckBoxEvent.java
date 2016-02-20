package example.com.Settings;

import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.Toast;

/**
 * Created by Toshiba on 27-Jan-16.
 */
public class CheckBoxEvent implements AdapterView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        CheckedTextView ctv = (CheckedTextView) view;
        if(ctv.isChecked()){
            boolean n = ctv.isChecked();
            String pos = (String) parent.getItemAtPosition(position);
          //  Toast.makeText(, "now it is unchecked", Toast.LENGTH_SHORT).show();
        }else{
            //Toast.makeText(MainActivity.this, "now it is checked", Toast.LENGTH_SHORT).show();
        }
    }
}
