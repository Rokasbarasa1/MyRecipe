package com.example.myrecipe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipe.R;
import com.example.myrecipe.models.CalendarTodo;
import com.example.myrecipe.models.Recipe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AdapterSchedule extends RecyclerView.Adapter<AdapterSchedule.ViewHolder>{

    //Handles showing the schedule items and handles the messy logic of showing the month and year or not in specific spots.

    private List<CalendarTodo> schedules;
    private AdapterSchedule.OnClickTextListener listener;
    private String monthName;
    private int yearNumber;
    private List<Recipe> matchingRecipes;

    public AdapterSchedule(List<CalendarTodo> schedules, List<Recipe> matchingRecipes, OnClickTextListener listener){
        this.schedules = schedules;
        this.listener = listener;
        this.matchingRecipes = matchingRecipes;
    }

    @NonNull
    @Override
    public AdapterSchedule.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.piece_shedule_item, parent, false);
        return new AdapterSchedule.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSchedule.ViewHolder viewHolder, int position) {

        //SSORRRRYYYYYY
        //This if statement mess helps to figure out where to place the headline of the month
        //It first checks if the month and year were set before.
        //Or if they were set and are not equal to the current positions year and month.
        //Then because the recycler view counts backwards to zero it checks if the position
        //that will be above it will have the same month, if it does it will hide the month of its own.
        //Then if it sees that there are no more postions above it, it will show its month name.

        if(monthName == null || yearNumber == 0
                || !monthName.equals(getMonth(schedules.get(position).getCalendarTime()))
                || yearNumber != schedules.get(position).getYear())
        {
            if((position-1) == -1
                    || !getMonth(schedules.get(position).getCalendarTime()).equals(getMonth(schedules.get(position-1).getCalendarTime()))){
                viewHolder.monthYear.setText(getMonth(schedules.get(position).getCalendarTime()) + " " + schedules.get(position).getYear());
                monthName = getMonth(schedules.get(position).getCalendarTime());
                yearNumber = schedules.get(position).getYear();
            }
            else
                viewHolder.layoutToggle.setVisibility(View.GONE);
        }
        else
            viewHolder.layoutToggle.setVisibility(View.GONE);

        for (int i = 0; i < schedules.size(); i++) {
            if(schedules.get(position).getRecipeId() == matchingRecipes.get(i).getId()) {
                viewHolder.name.setText(matchingRecipes.get(i).getName());
                break;
            }
        }
        viewHolder.dayOfWeek.setText(getDayOfWeek(schedules.get(position).getCalendarTime()) + "");
        viewHolder.numberOfDay.setText(schedules.get(position).getDay() + "");

        //Some layz if statments to fix the one digit time
        if(schedules.get(position).getHour() < 10){
            if(schedules.get(position).getMinute() < 10)
                viewHolder.time.setText("0" + schedules.get(position).getHour() + " : " + "0" + schedules.get(position).getMinute());
            else
                viewHolder.time.setText("0" + schedules.get(position).getHour() + " : " + schedules.get(position).getMinute());

        }else {
            if(schedules.get(position).getMinute() < 10)
                viewHolder.time.setText(schedules.get(position).getHour() + " : " + "0" + schedules.get(position).getMinute());
            else
                viewHolder.time.setText(schedules.get(position).getHour() + " : " + schedules.get(position).getMinute());
        }
    }

    //Figures out the month name
    private String getMonth(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM");
        return dateFormat.format(calendar.getTime());
    }

    //Figures out the week day in short format
    private String getDayOfWeek(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE");
        return dateFormat.format(calendar.getTime());
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    //When the live data updates it updates this
    public void setData(List<CalendarTodo> schedules, List<Recipe> matchingRecipes) {
        this.schedules = schedules;
        this.matchingRecipes = matchingRecipes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView dayOfWeek;
        TextView numberOfDay;
        TextView monthYear;
        TextView time;
        FrameLayout layoutToggle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.findViewById(R.id.piece_schedule_more_information).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(name.getText().toString());
                }
            });
            layoutToggle = itemView.findViewById(R.id.piece_schedule_sneaky_view_group);
            name = itemView.findViewById(R.id.piece_schedule_event_name);
            dayOfWeek = itemView.findViewById(R.id.piece_schedule_week_day);
            numberOfDay = itemView.findViewById(R.id.piece_schedule_number_day);
            monthYear = itemView.findViewById(R.id.piece_schedule_sneaky_month_year);
            time = itemView.findViewById(R.id.piece_schedule_event_time);
        }
    }

    public interface OnClickTextListener{
        void onClick(String name);
    }
}
