package com.example.searchapp.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.searchapp.R;
import com.example.searchapp.activities.WebActivity;
import com.example.searchapp.models.University;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<University> universities;
    public Activity activity;

    public Context context;

    public Bundle bundle;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView[] textViewArr;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textViewArr = new TextView[4];
            textViewArr[0] = (TextView) view.findViewById(R.id.textView);
            textViewArr[1] = (TextView) view.findViewById(R.id.textView2);
            textViewArr[2] = (TextView) view.findViewById(R.id.textView3);
            textViewArr[3] = (TextView) view.findViewById(R.id.textView4);
            textViewArr[3].setTextColor(Color.YELLOW);
            textViewArr[3].setPaintFlags(textViewArr[3].getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        }

        public TextView[] getTextView() {
            return textViewArr;
        }
    }

    public CustomAdapter(List<University> universityList) {
        this.universities = universityList;
    }

    // step2 : Create new views (invoked by the layout manager)
    @SuppressLint("MissingInflatedId")
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(CustomAdapter.ViewHolder viewHolder, final int position) {
        //viewHolder.getTextView().setText("hello");
        University university = universities.get(position);
        if (university != null) {
            TextView[] textViews = viewHolder.getTextView();
            textViews[0].setText("" + position);
            textViews[1].setText(university.getName());
            textViews[2].setText(university.getCountry());
            String url = university.getWeb_pages().get(0);
            textViews[3].setMovementMethod(LinkMovementMethod.getInstance());
            textViews[3].setText(url);
            textViews[3].setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, WebActivity.class);
                    intent.putExtra("url", university.getWeb_pages().get(0));
                    startActivity(context, intent, bundle);
                }
            }));
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return universities.size();
    }
}

