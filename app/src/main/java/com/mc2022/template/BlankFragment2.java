package com.mc2022.template;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class BlankFragment2 extends Fragment {

    public static TextView txt, txt2;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_blank2, container, false);
        String str1=getArguments().getString("title");
        String str2=getArguments().getString("body");
        String str3=getArguments().getString("img");
        txt = v.findViewById(R.id.txt1);
        txt.setText(str1);
        txt2 = v.findViewById(R.id.txt2);
        txt2.setText(str2);
        imageView = v.findViewById(R.id.imageView);
        Picasso.get().load(str3).into(imageView);
        return v;
    }


}