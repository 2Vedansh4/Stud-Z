package com.example.studz.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.studz.R;
import com.example.studz.chaptertopics.TopicActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public HomeFragment() {

        // Required empty public constructor
    }
    CardView heading1,heading2,heading3,heading4,heading5 ;
    ImageView image1,image2,image3,image4,image5 ;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  =  inflater.inflate(R.layout.fragment_home, container, false);
        heading1 = view.findViewById(R.id.heading1);
        heading2 = view.findViewById(R.id.heading2);
        heading3 = view.findViewById(R.id.heading3);
        heading4 = view.findViewById(R.id.heading4);
        heading5 = view.findViewById(R.id.heading5);

        image1 = view.findViewById(R.id.image1);
        image2 = view.findViewById(R.id.image2);
        image3 = view.findViewById(R.id.image3);
        image4 = view.findViewById(R.id.image4);
        image5 = view.findViewById(R.id.image5);

        loadimage();

        heading1.setOnClickListener(this);
        heading2.setOnClickListener(this);
        heading3.setOnClickListener(this);
        heading4.setOnClickListener(this);
        heading5.setOnClickListener(this);



        return view ;

    }

    private void loadimage() {
        Glide.with(getContext()).load("https://firebasestorage.googleapis.com/v0/b/stud-z.appspot.com/o/img.jpg?alt=media&token=2d75c419-4e63-4138-84ba-813ed0470824").into(image1);
        Glide.with(getContext()).load("https://firebasestorage.googleapis.com/v0/b/stud-z.appspot.com/o/img2.png?alt=media&token=6e4aadf1-a675-45ec-82fa-1ba577c6a4c7").into(image2);
        Glide.with(getContext()).load("https://firebasestorage.googleapis.com/v0/b/stud-z.appspot.com/o/img.jpg?alt=media&token=2d75c419-4e63-4138-84ba-813ed0470824").into(image3);
        Glide.with(getContext()).load("https://firebasestorage.googleapis.com/v0/b/stud-z.appspot.com/o/img2.png?alt=media&token=6e4aadf1-a675-45ec-82fa-1ba577c6a4c7").into(image4);
        Glide.with(getContext()).load("https://firebasestorage.googleapis.com/v0/b/stud-z.appspot.com/o/img.jpg?alt=media&token=2d75c419-4e63-4138-84ba-813ed0470824").into(image5);


    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(),TopicActivity.class);
        if (v.getId() == R.id.heading1){
            intent.putExtra("chapterName","heading1");
            startActivity(intent);

        } else if (v.getId() == R.id.heading2) {
            intent.putExtra("chapterName","heading2");

            startActivity(intent);

        } else if (v.getId() == R.id.heading3) {
            intent.putExtra("chapterName","heading3");

            startActivity(intent);

        } else if (v.getId() == R.id.heading4) {
            intent.putExtra("chapterName","heading4");

            startActivity(intent);

        } else if (v.getId() == R.id.heading5) {
            intent.putExtra("chapterName","heading5");

            startActivity(intent);

        }

    }
}