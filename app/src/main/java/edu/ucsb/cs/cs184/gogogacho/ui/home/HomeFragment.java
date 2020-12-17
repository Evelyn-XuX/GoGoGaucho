package edu.ucsb.cs.cs184.gogogacho.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.w3c.dom.Text;

import edu.ucsb.cs.cs184.gogogacho.AreaA;
import edu.ucsb.cs.cs184.gogogacho.AreaD;
import edu.ucsb.cs.cs184.gogogacho.AreaE;
import edu.ucsb.cs.cs184.gogogacho.AreaF;
import edu.ucsb.cs.cs184.gogogacho.AreaG;
import edu.ucsb.cs.cs184.gogogacho.R;
import edu.ucsb.cs.cs184.gogogacho.ShowMajorCourse;
import edu.ucsb.cs.cs184.gogogacho.ShowMajorElective;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView units;
    private TextView mCourses;
    private TextView mElectives;
    private boolean complete;
    private Button showMajorC;
    private Button showMajorE;
    private ImageButton areaA;
    private ImageButton areaD;
    private ImageButton areaE;
    private ImageButton areaF;
    private ImageButton areaG;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        units = root.findViewById(R.id.textView20);
        mCourses = root.findViewById(R.id.textView29);
        mElectives = root.findViewById(R.id.textView28);

        /*
        TODO: Show completed unit(TextView20)
         */

        /*
        TODO: Show completed level for GE course
         */


        /*
        TODO: Click "Button" to list Major Courses that have been taken
         */
        showMajorC = root.findViewById(R.id.button);
        showMajorC.setOnClickListener(task->nextMajorCourse());

        /*
        TODO: Click "Button3" to list Major Elective that have been taken
         */
        showMajorE = root.findViewById(R.id.button3);
        showMajorE.setOnClickListener(task->nextMajorEle());

        /*
        TODO: Click "ImageButton2" to list Area A that have been taken
         */
        areaA = root.findViewById(R.id.imageButton2);
        areaA.setOnClickListener(task->nextAreaA());

        /*
        TODO: Click "ImageButton3" to list Area D that have been taken
         */
        areaD = root.findViewById(R.id.imageButton3);
        areaD.setOnClickListener(task->nextAreaD());

        /*
        TODO: Click "ImageButton4" to list Area E that have been taken
         */
        areaE = root.findViewById(R.id.imageButton4);
        areaE.setOnClickListener(task->nextAreaE());


        /*
        TODO: Click "ImageButton" to list Area F that have been taken
         */
        areaF = root.findViewById(R.id.imageButton);
        areaF.setOnClickListener(task->nextAreaF());

        /*
        TODO: Click "ImageButton5" to list Area G that have been taken
         */
        areaG = root.findViewById(R.id.imageButton5);
        areaG.setOnClickListener(task->nextAreaG());



        return root;
    }

    private void nextMajorCourse(){
        Intent intent = new Intent(getActivity(), ShowMajorCourse.class);
        startActivity(intent);
    }

    private void nextMajorEle(){
        Intent intent = new Intent(getActivity(), ShowMajorElective.class);
        startActivity(intent);
    }

    private void nextAreaA(){
        Intent intent = new Intent(getActivity(), AreaA.class);
        startActivity(intent);
    }

    private void nextAreaD(){
        Intent intent = new Intent(getActivity(), AreaD.class);
        startActivity(intent);
    }

    private void nextAreaE(){
        Intent intent = new Intent(getActivity(), AreaE.class);
        startActivity(intent);
    }

    private void nextAreaF(){
        Intent intent = new Intent(getActivity(), AreaF.class);
        startActivity(intent);
    }

    private void nextAreaG(){
        Intent intent = new Intent(getActivity(), AreaG.class);
        startActivity(intent);
    }




}