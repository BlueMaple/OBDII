package cn.edu.nju.software.obdii.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.nju.software.obdii.R;

/**
 * Display OBD information
 */
public class OBDFragment extends Fragment {
    private static final float SPEED_MIN_VALUE = 0;
    private static final float SPEED_MAX_VALUE = 120;
    private static final float SPEED_MAX_ANGLE = 246.33f;
    private static final float DEGREE_PER_SPEED =
            SPEED_MAX_ANGLE / (SPEED_MAX_VALUE - SPEED_MIN_VALUE);

    private ImageView mDial;
    private ImageView mPointer;
    private TextView mSpeed;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_obd, container, false);

        return view;
    }
}
