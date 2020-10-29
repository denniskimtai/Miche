package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CropStepsActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_steps);

        gridLayout = findViewById(R.id.mainGrid);

        setSingleEvent(gridLayout);

        builder = new AlertDialog.Builder(this);

        builder.setTitle("Purple Passion");

        builder.setIcon(R.drawable.logo);

        //show pop up dialog with information
        builder.setMessage("Its cultivated commercially for its sweet seedy fruit. It does well in high altitudes and optimally at temperatures between 22 and 27 degrees.\n\n" +
                "Please follow the steps below to get a full summary of what you need to cultivate it. \n\n"+
                "For agronomical support services click on the Lima Smart button \n\n");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        // change text size of alert dialog builder
        AlertDialog alert = builder.create();
        alert.show();
        alert.getWindow().getAttributes();

        TextView textView = alert.findViewById(android.R.id.message);
        textView.setTextSize(13);



    }

    // we are setting onClickListener for each element in gridlayout
    private void setSingleEvent(GridLayout gridLayout) {
        for(int i = 0; i<gridLayout.getChildCount();i++){
            CardView cardView = (CardView)gridLayout.getChildAt(i);
            final int finalI= i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(CropStepsActivity.this, CropDetailsActivity.class);
                    startActivity(intent);

                }
            });
        }
    }


}