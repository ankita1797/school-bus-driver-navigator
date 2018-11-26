package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.ankita.internshipapplication.R;

import java.util.List;

import bean.StoppageBean;

public class StoppageAdapter extends BaseAdapter implements Filterable {

      List<StoppageBean> listStoppages;
      private Context con;
      private static LayoutInflater inflater;

    public StoppageAdapter(List<StoppageBean> listStoppages, Context con) {
        this.listStoppages = listStoppages;
        this.con = con;
        inflater =(LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
    @Override
    public int getCount() {
        return listStoppages.size();
    }

    @Override
    public Object getItem(int i) {
        return listStoppages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
            view=inflater.inflate(R.layout.stoppage_listview,null);
        TextView tv_place= (TextView) view.findViewById(R.id.tv_place);
        TextView tv_address= (TextView) view.findViewById(R.id.tv_address);

        tv_place.setText(i+1+". "+listStoppages.get(i).getPlace());
        tv_address.setText(listStoppages.get(i).getAddress());
        return  view;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
