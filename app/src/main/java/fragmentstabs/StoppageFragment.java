package fragmentstabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ankita.internshipapplication.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;

import adapter.StoppageAdapter;
import bean.StoppageBean;
import databases.DatabaseHandler;
import databases.DatabaseStoppage;


public class StoppageFragment extends Fragment{
    private Context context;
    private List<StoppageBean> listStoppages;
    private static final int PLACE_PICKER_REQUEST = 999;
    private ListView lv;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(26.1890, 91.7729), new LatLng(27.1890, 91.8729));
    DatabaseStoppage databaseStoppage;
    StoppageBean stoppageBean;

    public StoppageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_stoppage, container, false);
        DatabaseStoppage databaseStoppage=new DatabaseStoppage(getActivity());
        listStoppages=databaseStoppage.getAllStoppages();
        StoppageAdapter stoppageAdapter = new StoppageAdapter(listStoppages, getActivity());
        lv = (ListView) rootview.findViewById(R.id.lv_bus);
        lv.setAdapter(stoppageAdapter);
        Button pickerButton = (Button) rootview.findViewById(R.id.pickerButton);
        pickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PlacePicker.IntentBuilder intentBuilder =
                            new PlacePicker.IntentBuilder();
                    intentBuilder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);
                    startActivityForResult(intentBuilder.build(getActivity()), PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException
                        | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });
        lv.setLongClickable(true);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                StoppageBean stoppageBean=new StoppageBean();
                DatabaseStoppage databaseStoppage= new DatabaseStoppage(getContext());
                databaseStoppage.deleteStoppage(listStoppages.get(i));
                return true;
            }
        });
        return rootview;
    }

    @Override
    public void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST
                && resultCode == Activity.RESULT_OK) {

            final Place place = PlacePicker.getPlace(getActivity(), data);
            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
            LatLng latLng=place.getLatLng();
            double latitude= latLng.latitude;
            double longitude= latLng.longitude;
            String attributions = (String) place.getAttributions();
            if (attributions == null) {
                attributions = "";
            }
            databaseStoppage = new DatabaseStoppage(getActivity());
            stoppageBean=new StoppageBean();
            stoppageBean.setPlace(name.toString());
            stoppageBean.setAddress(address.toString()+"  Lat:  "+ String.valueOf(latitude)+"  Long:  "+ String.valueOf(longitude));
            try {
                 databaseStoppage.addStoppage(stoppageBean);
            } catch (Exception e) {
                 Toast.makeText(getActivity(),"error", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
