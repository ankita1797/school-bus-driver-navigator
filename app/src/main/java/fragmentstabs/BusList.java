package fragmentstabs;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import databases.DatabaseHandler;
import com.example.ankita.internshipapplication.R;

import java.util.List;

import adapter.BusAdapter;
import bean.BusBean;

public class BusList extends Dialog implements View.OnClickListener{

        private Context context;
        private List<BusBean> listBuses;
        private ListView lv;
        private  TextView et_buslist;
         public BusList(Context context, TextView et_buslist) {
            super(context);
            this.context = context;
            this.et_buslist= et_buslist;
             DatabaseHandler databaseHandler=new DatabaseHandler(context);
             listBuses=databaseHandler.getAllBuses();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_buslist);
            BusAdapter busAdapter = new BusAdapter(listBuses, context);
            lv = (ListView) findViewById(R.id.lv_bus);
            lv.setAdapter(busAdapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        et_buslist.setText("Bus no."+listBuses.get(position).getBusNumber().toString()+"\n"+listBuses.get(position).getSchool().toString()+" - "+listBuses.get(position).getAddress().toString());
                        dismiss();

                }
            });

        }

        @Override
        public void onClick(View v) {

        }
    }

