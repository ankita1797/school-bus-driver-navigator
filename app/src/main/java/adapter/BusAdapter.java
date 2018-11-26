package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.ankita.internshipapplication.R;

import java.util.List;

import bean.BusBean;

public class BusAdapter extends BaseAdapter implements Filterable {

    List<BusBean> listBuses;
    private Context con;
    private static LayoutInflater inflater;
    private TextView et_buslist;

    public BusAdapter(List<BusBean> listBuses, Context con) {
        this.listBuses = listBuses;
        this.con = con;
        inflater =(LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public int getCount() {
        return listBuses.size();
    }

    @Override
    public Object getItem(int i) {
        return listBuses.get(i) ;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
            view=inflater.inflate(R.layout.bus_listview,null);
      TextView tv_busnumber= (TextView) view.findViewById(R.id.tv_busnumber);
      TextView tv_schooladdress= (TextView) view.findViewById(R.id.tv_schooladdress);

      tv_busnumber.setText("Bus no. "+ listBuses.get(i).getBusNumber());
      tv_schooladdress.setText(listBuses.get(i).getSchool()+" - "+ listBuses.get(i).getAddress());
      return  view;

    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
