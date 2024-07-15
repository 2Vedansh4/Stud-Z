package com.example.studz.ui.quiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.studz.CustomAdapter;
import com.example.studz.CustomItem;
import com.example.studz.R;
import com.example.studz.chaptertopics.TopicActivity;
import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizFragment extends Fragment  implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuizFragment() {
        // Required empty public constructor
    }
     MaterialButton button ;
    CardView heading01,heading02,heading03 ;
    ImageView image01,image02,image03 ;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuizFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuizFragment newInstance(String param1, String param2) {
        QuizFragment fragment = new QuizFragment();
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
        View view  =  inflater.inflate(R.layout.fragment_quiz, container, false);
        heading01 = view.findViewById(R.id.heading01);
        heading02 = view.findViewById(R.id.heading02);
        heading03 = view.findViewById(R.id.heading03);


        image01 = view.findViewById(R.id.image01);
        image02 = view.findViewById(R.id.image02);
        image03 = view.findViewById(R.id.image03);


        loadimage();

        heading01.setOnClickListener( this);
        heading02.setOnClickListener(this);
        heading03.setOnClickListener(this);




        return view ;
        // Inflate the layout for this fragment
    //   View root = inflater.inflate(R.layout.fragment_quiz, container, false);
//        CustomItem[] items = {
//                new CustomItem(R.drawable.ic_launcher_foreground, "Answers"),
//                new CustomItem(R.drawable.ic_launcher_foreground, "Questions"),
//        };
//        // Create a custom adapter
//        CustomAdapter adapter = new CustomAdapter(requireContext(), R.layout.list_item, items);
//
//        // Get the ListView reference and set the adapter
//        @SuppressLint({"MissingInflatedId", "LocalSuppress"})


        // Set click listener for each row
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // Handle item click here
//                CustomItem clickedItem = (CustomItem) parent.getItemAtPosition(position);
//
//                Intent intent = new Intent(getContext(),  quiz2.class);
//
//                intent.putExtra("chaptername",clickedItem.getText());
//                startActivity(intent);
//
//                Toast.makeText(requireContext(), "Clicked on: " + clickedItem.getText(), Toast.LENGTH_SHORT).show();
//            }
//        });

     //   return root;
    }
    private void loadimage() {
        Glide.with(getContext()).load("https://firebasestorage.googleapis.com/v0/b/stud-z.appspot.com/o/clipart2499189.png?alt=media&token=bce36ead-eafe-4c77-b5b4-61b0c46e80d7").into(image01);
        Glide.with(getContext()).load("https://firebasestorage.googleapis.com/v0/b/stud-z.appspot.com/o/clipart28138.png?alt=media&token=40a759ef-5f66-49b4-9bc7-c05f3157f008").into(image02);
        Glide.with(getContext()).load("https://firebasestorage.googleapis.com/v0/b/stud-z.appspot.com/o/clipart1067202.png?alt=media&token=c32e4d71-27fe-4de9-a0a9-f90e0f0a5589").into(image03);



    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), toquiz.class);
        if (v.getId() == R.id.heading01){
            intent.putExtra("Subject","Physics");
            startActivity(intent);

        } else if (v.getId() == R.id.heading02) {
            intent.putExtra("Subject","Chemistry");

            startActivity(intent);

        } else if (v.getId() == R.id.heading03) {
            intent.putExtra("Subject","Mathematics");

            startActivity(intent);

        }

    }

}
