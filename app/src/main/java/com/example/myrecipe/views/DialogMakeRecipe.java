package com.example.myrecipe.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.myrecipe.R;
import com.example.myrecipe.models.Tag;

import java.sql.Time;
import java.util.Calendar;

public class DialogMakeRecipe extends DialogFragment {

    private MakeRecipeDialogListener listener;
    private int selectionModel;


    public DialogMakeRecipe(MakeRecipeDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder makeRecipeDialog = new AlertDialog.Builder(getActivity());
        final View makeRecipeView = requireActivity().getLayoutInflater().inflate(R.layout.alert_make_recipe, null);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        selectionModel = 1;

        makeRecipeDialog.setView(makeRecipeView)
                .setPositiveButton("Complete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getContext(),"Clicked yes", Toast.LENGTH_SHORT).show();
                        DatePicker date;
                        TimePicker time;
                        EditText servings;
                        int day;
                        int month;
                        int year;
                        int hour;
                        int minute;
                        Calendar calendar;
                        switch (selectionModel){
                            case 1:
                                servings = makeRecipeView.findViewById(R.id.alert_groceries_servings);

                                listener.onDialogPositiveClickGrocery(Integer.parseInt(servings.getText().toString()));
                                break;
                            case 2:
                                date = makeRecipeView.findViewById(R.id.datePicker);
                                time = makeRecipeView.findViewById(R.id.timePicker);
                                day = date.getDayOfMonth();
                                month = date.getMonth();
                                year = date.getYear();
                                hour = time.getHour();
                                minute = time.getMinute();
                                calendar = Calendar.getInstance();
                                calendar.set(year, month, day, hour, minute);

                                listener.onDialogPositiveClickCalendar(calendar);
                                break;
                            case 3:
                                date = makeRecipeView.findViewById(R.id.datePicker);
                                time = makeRecipeView.findViewById(R.id.timePicker);
                                day = date.getDayOfMonth();
                                month = date.getMonth();
                                year = date.getYear();
                                hour = time.getHour();
                                minute = time.getMinute();
                                calendar = Calendar.getInstance();
                                calendar.set(year, month, day, hour, minute);

                                servings = makeRecipeView.findViewById(R.id.alert_groceries_servings);

                                listener.onDialogPositiveClickBoth(Integer.parseInt(servings.getText().toString()), calendar);
                                break;
                            default:
                                System.out.println("This was not supposed to happen in make recipe alert");
                                break;
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getContext(),"Clicked no", Toast.LENGTH_SHORT).show();
                    }
                });

        makeRecipeView.findViewById(R.id.alert_groceries).setVisibility(View.VISIBLE);
        makeRecipeView.findViewById(R.id.alert_calendar).setVisibility(View.GONE);

        makeRecipeView.findViewById(R.id.alert_radio_grocery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRecipeView.findViewById(R.id.alert_groceries).setVisibility(View.VISIBLE);
                makeRecipeView.findViewById(R.id.alert_calendar).setVisibility(View.GONE);
                selectionModel = 1;
            }
        });
        makeRecipeView.findViewById(R.id.alert_radio_calendar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRecipeView.findViewById(R.id.alert_groceries).setVisibility(View.GONE);
                makeRecipeView.findViewById(R.id.alert_calendar).setVisibility(View.VISIBLE);
                selectionModel = 2;
            }
        });
        makeRecipeView.findViewById(R.id.alert_radio_both).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRecipeView.findViewById(R.id.alert_groceries).setVisibility(View.VISIBLE);
                makeRecipeView.findViewById(R.id.alert_calendar).setVisibility(View.VISIBLE);
                selectionModel = 3;
            }
        });

        return makeRecipeDialog.create();
    }

    public interface MakeRecipeDialogListener{
        public void onDialogPositiveClickGrocery(int servings);
        public void onDialogPositiveClickCalendar(Calendar pointInTime);
        public void onDialogPositiveClickBoth(int servings, Calendar pointInTime);
    }
}
