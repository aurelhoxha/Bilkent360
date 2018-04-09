package aurelhoxha.bilkent360.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import aurelhoxha.bilkent360.R;

import static android.content.Context.MODE_PRIVATE;


public class ScheduleFragment extends Fragment
{
    public static final String PREFS = "bilkent360";
    String day;
    String courseName;
    int firstHour, secondHour;
    TextView tv;
    View myFragmentView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.scheduleview, container, false);
        return myFragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        initTableFromDB();
    }

    private void initTableFromDB() {
        SharedPreferences pref = getActivity().getSharedPreferences(PREFS, MODE_PRIVATE);
        for(int r = 2; r<=10;r++){
            for(int c = 1; c<=5; c++){
                String title = pref.getString("row" + r + c, "");
                tv = (TextView)myFragmentView.findViewById(getResourceIdOfCellAt(r,c));
                tv.setText(title);
            }
        }
    }

    private int getResourceIdOfCellAt(int r, int c){
        switch (r){
            case 2:
                switch (c){
                    case 1:
                        return R.id.row21;
                    case 2:
                        return R.id.row22;
                    case 3:
                        return R.id.row23;
                    case 4:
                        return R.id.row24;
                    case 5:
                        return R.id.row25;
                }
            case 3:
                switch (c){
                    case 1:
                        return R.id.row31;
                    case 2:
                        return R.id.row32;
                    case 3:
                        return R.id.row33;
                    case 4:
                        return R.id.row34;
                    case 5:
                        return R.id.row35;
                }
            case 4:
                switch (c){
                    case 1:
                        return R.id.row41;
                    case 2:
                        return R.id.row42;
                    case 3:
                        return R.id.row43;
                    case 4:
                        return R.id.row44;
                    case 5:
                        return R.id.row45;
                }
            case 5:
                switch (c){
                    case 1:
                        return R.id.row51;
                    case 2:
                        return R.id.row52;
                    case 3:
                        return R.id.row53;
                    case 4:
                        return R.id.row54;
                    case 5:
                        return R.id.row55;
                }
            case 6:
                switch (c){
                    case 1:
                        return R.id.row61;
                    case 2:
                        return R.id.row62;
                    case 3:
                        return R.id.row63;
                    case 4:
                        return R.id.row64;
                    case 5:
                        return R.id.row65;
                }
            case 7:
                switch (c){
                    case 1:
                        return R.id.row71;
                    case 2:
                        return R.id.row72;
                    case 3:
                        return R.id.row73;
                    case 4:
                        return R.id.row74;
                    case 5:
                        return R.id.row75;
                }
            case 8:
                switch (c){
                    case 1:
                        return R.id.row81;
                    case 2:
                        return R.id.row82;
                    case 3:
                        return R.id.row83;
                    case 4:
                        return R.id.row84;
                    case 5:
                        return R.id.row85;
                }
            case 9:
                switch (c){
                    case 1:
                        return R.id.row91;
                    case 2:
                        return R.id.row92;
                    case 3:
                        return R.id.row93;
                    case 4:
                        return R.id.row94;
                    case 5:
                        return R.id.row95;
                }
            case 10:
                switch (c){
                    case 1:
                        return R.id.row101;
                    case 2:
                        return R.id.row102;
                    case 3:
                        return R.id.row103;
                    case 4:
                        return R.id.row104;
                    case 5:
                        return R.id.row105;
                }
        }
        return 0;
    }
}
