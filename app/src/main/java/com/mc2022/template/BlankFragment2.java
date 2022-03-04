package com.mc2022.template;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class BlankFragment2 extends Fragment {

    public static TextView txt, txt2, txt3;
    public static ImageView imageView;
    public static Button btn;
    public static EditText editTxt;
    public static String save;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_blank2, container, false);
        txt = v.findViewById(R.id.txt1);
        txt2 = v.findViewById(R.id.txt2);
        txt3 = v.findViewById(R.id.txt3);
        btn = v.findViewById(R.id.btn);
        editTxt = v.findViewById(R.id.editTxt);

        imageView = v.findViewById(R.id.imageView);
        String title = getArguments().getString("title");
        String body = getArguments().getString("body");
        String imgURL = getArguments().getString("img");
        txt.setText(title);
        txt2.setText(body);
        Picasso.get().load(imgURL).into(imageView);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 save =editTxt.getText().toString();
                 txt3.setText(save);
                 editTxt.setVisibility(View.INVISIBLE);
            }
        });
        txt3.setText(save);
        return v;
    }
}