package fragmentdrawer;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import databases.DatabaseHandler;

import com.example.ankita.internshipapplication.MainActivity;
import com.example.ankita.internshipapplication.R;

import bean.BusBean;

import static android.content.Context.MODE_PRIVATE;

public class AddBusFragment extends Fragment {
    private EditText addschool, addbus, addAddress;
    private Button btn_add;
    private MainActivity mainActivity;
    private BusBean busBean;
    private DatabaseHandler databaseHandler;
    String school;

    public AddBusFragment() {
    }

    @SuppressLint("ValidFragment")
    public AddBusFragment(MainActivity mainActivity, String school ) {
        this.mainActivity=mainActivity;
        this.school=school;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getActivity().getSharedPreferences("user_login", MODE_PRIVATE);
        school= prefs.getString("school", null);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_addbus, container, false);
        addschool= (EditText)rootview.findViewById(R.id.addschool);
        addbus= (EditText)rootview.findViewById(R.id.addbus);
        addAddress= (EditText)rootview.findViewById(R.id.addAddress);
        addschool.setText(school);
        addschool.setEnabled(false);

        btn_add = (Button) rootview.findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s2 = addbus.getText().toString();
                String s3 = addAddress.getText().toString();
                if (addschool.equals("") || s2.equals("") || s3.equals("")) {
                    Toast.makeText(mainActivity, "All fields are mandatory", Toast.LENGTH_LONG).show();
                } else {
                    databaseHandler = new DatabaseHandler(mainActivity);
                    busBean = new BusBean();
                    busBean.setBusNumber(s2);
                    busBean.setSchool(school);
                    busBean.setAddress(s3);
                    try {
                        databaseHandler.addBus(busBean);
                        Toast.makeText(mainActivity, "Sucessfully added", Toast.LENGTH_SHORT).show();
                        addAddress.setText("");
                        addbus.setText("");
                        addschool.setText("");


                    } catch (Exception e) {
                        Toast.makeText(mainActivity, "error", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

        return rootview;
    }
    public void showBackButton() {
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
