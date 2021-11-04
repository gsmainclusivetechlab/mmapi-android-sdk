package com.gsmasdk.gsmatest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class CustomUseCaseAdapter extends ArrayAdapter<String> {

    public CustomUseCaseAdapter(@NonNull Context context, ArrayList<String> arrayList) {
        super(context, 0, arrayList);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.usecase_item, parent, false);
        }
        ((TextView)currentItemView.findViewById(R.id.useCaseItem)).setText(getItem(position));
        return currentItemView;
    }
}