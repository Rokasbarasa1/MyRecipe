package com.example.myrecipe.views;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.myrecipe.R;

import java.util.Calendar;

public class DialogMakeRecipe extends DialogFragment {

    MakeRecipeDialogListener listener;
    int selectionModel;

    public DialogMakeRecipe(MakeRecipeDialogListener listener) {
        this.listener = listener;
    }

    //Creates a alert box with 3 options for the user: grocery, calendar or both when making a recipe.
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder makeRecipeDialog = new AlertDialog.Builder(getActivity());
        final View makeRecipeView = requireActivity().getLayoutInflater().inflate(R.layout.alert_make_recipe, null);


        //Just an empty button that just quits the alert
        makeRecipeDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        //Default selection when alert is opened is grocery
        selectionModel = 1;

        //Disables the default positive button
        makeRecipeDialog.setView(makeRecipeView).setPositiveButton("Complete", null);

        //Sets the grocery view as visible and calendar view as gone from the layout.
        makeRecipeView.findViewById(R.id.alert_groceries).setVisibility(View.VISIBLE);
        makeRecipeView.findViewById(R.id.alert_calendar).setVisibility(View.GONE);

        //Reacts to user clicking on the radio button group
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

        AlertDialog dialog = makeRecipeDialog.create();

        //Handles user clicking Complete
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePicker date;
                        TimePicker time;
                        EditText servings;
                        boolean good = false;
                        int day;
                        int month;
                        int year;
                        int hour;
                        int minute;
                        Calendar calendar;
                        switch (selectionModel){
                            case 1:
                                servings = makeRecipeView.findViewById(R.id.alert_groceries_servings);
                                if(servings.getText().toString().matches("")){
                                    TextView text = ((AlertDialog) dialog).findViewById(R.id.alert_groceries_text);
                                    text.setTextColor(getResources().getColor(R.color.colorError));
                                    good = false;
                                    break;
                                }else {
                                    listener.onDialogPositiveClickGrocery(Integer.parseInt(servings.getText().toString()));
                                    good =true;
                                    break;
                                }
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
                                good = true;
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
                                if(servings.getText().toString().matches("")){
                                    TextView text = ((AlertDialog) dialog).findViewById(R.id.alert_groceries_text);
                                    text.setTextColor(getResources().getColor(R.color.colorError));
                                    good = false;
                                    break;
                                }

                                listener.onDialogPositiveClickBoth(Integer.parseInt(servings.getText().toString()), calendar);
                                good = true;
                                break;
                            default:
                                System.out.println("This was not supposed to happen in make recipe alert");
                                good = false;
                                break;
                        }
                        if(good)
                            dialog.dismiss();
                    }
                });
            }
        });
        return dialog;
    }

    public interface MakeRecipeDialogListener{
        void onDialogPositiveClickGrocery(int servings);
        void onDialogPositiveClickCalendar(Calendar pointInTime);
        void onDialogPositiveClickBoth(int servings, Calendar pointInTime);
    }
}
