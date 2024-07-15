package com.example.studz;

// CustomAdapter.java
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.studz.ui.analysislist;
import com.example.studz.ui.quiz.QuizData;

public class customadapteranalysis extends ArrayAdapter<analysislist> {

    public customadapteranalysis(Context context, int resource, analysislist[] objects) {
        super(context,resource,objects);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.analysislist_item, parent, false);
        }

        // Get the current item
        analysislist m = getItem(position);

        // Bind data to the view elements

        TextView question = convertView.findViewById(R.id.question3);
        RadioGroup radioGroup = convertView.findViewById(R.id.radioGroup1);
        TextView correctanswer = convertView.findViewById(R.id.textView61);
        CardView cardView = convertView.findViewById(R.id.cardview); // Assuming you have a CardView with this ID



        if (m != null) {
            question.setText(m.getQuestion());
            radioGroup.removeAllViews();
            correctanswer.setText(m.getAnswer());

            createRadioButton(m.getOption1(),radioGroup);
            createRadioButton(m.getOption2(),radioGroup);
            createRadioButton(m.getOption3(),radioGroup);
            createRadioButton(m.getOption4(),radioGroup);
            if(m.getCorrectanswer().equals( m.getAnswer())  ){
                cardView.setBackgroundColor(Color.parseColor("#3237FF00"));

            }else {
                cardView.setBackgroundColor(Color.parseColor("#32FF0000"));


            }

        }
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            View view = radioGroup.getChildAt(i);
            if (view instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) view;
                String optionText = radioButton.getText().toString();

                // Compare the option text with userAdditionalData and select the matching radio button
                if (optionText.equals(m.getCorrectanswer())) {
                    radioButton.setChecked(true);
                    break; // No need to continue checking once a match is found
                }
            }
        }

        return convertView;
    }
    private void createRadioButton(String optionText,RadioGroup radioGroup) {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setText(optionText);
        int radioButtonId = View.generateViewId();
        radioButton.setId(radioButtonId);
        radioGroup.addView(radioButton);
    }

}

