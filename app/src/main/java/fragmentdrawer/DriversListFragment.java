package fragmentdrawer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import databases.DatabaseDriver;

import com.example.ankita.internshipapplication.MainActivity;
import com.example.ankita.internshipapplication.R;

import java.util.List;

import adapter.DriversAdapter;
import bean.DriverBean;

public class DriversListFragment extends Fragment
{
    private List<DriverBean> listDrivers;
    private ListView lv;
    private DatabaseDriver databaseHandlerDriver;
    private MainActivity mainActivity;


    public DriversListFragment() {
    }

    @SuppressLint("ValidFragment")
    public DriversListFragment(MainActivity mainActivity) {
        this.mainActivity= mainActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_driverslist, container, false);
        databaseHandlerDriver=new DatabaseDriver(mainActivity);
        listDrivers=databaseHandlerDriver.getAllDrivers();
        DriversAdapter driversAdapter=new DriversAdapter(listDrivers ,mainActivity);
        lv = (ListView) rootView.findViewById(R.id.lv_drivers);
        lv.setAdapter(driversAdapter);


        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}


