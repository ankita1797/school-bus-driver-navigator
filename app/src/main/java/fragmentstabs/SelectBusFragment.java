package fragmentstabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ankita.internshipapplication.HomeFragment;
import com.example.ankita.internshipapplication.MainActivity;
import com.example.ankita.internshipapplication.R;


public class SelectBusFragment extends Fragment{
    private TextView et_busplate;
    private MainActivity mainActivity;
    private TextView start, stop;

    public SelectBusFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_selectbus, container, false);
        et_busplate= (TextView) rootview.findViewById(R.id.et_busplate);
        start= (TextView) rootview.findViewById(R.id.start);
        stop= (TextView) rootview.findViewById(R.id.stop);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop.setVisibility(View.VISIBLE);
                start.setVisibility(View.GONE);
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.setVisibility(View.VISIBLE);
                stop.setVisibility(View.GONE);
            }
        });

        et_busplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BusList busList= new BusList(getActivity(), et_busplate);
                busList.show();

            }
        });
        return rootview;
    }

}
