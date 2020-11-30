package com.example.myrecipe.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipe.R;
import com.example.myrecipe.adapter.AdapterTag;
import com.example.myrecipe.models.Tag;
import com.example.myrecipe.viewModels.ViewModelTags;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FragmentTags extends Fragment implements AdapterTag.OnListTagClickListener {
    private FloatingActionButton add_btn;
    private RecyclerView tagList;
    private AdapterTag adapterTag;
    private ViewModelTags viewModel;
    private FragmentManager supportFragmentManager;
    private TextView toolbarTitle;
    private ActionBar upArrow;


    public FragmentTags(FragmentManager supportFragmentManager, TextView toolbarTitle, ActionBar upArrow) {
        this.supportFragmentManager = supportFragmentManager;
        this.toolbarTitle = toolbarTitle;
        this.upArrow = upArrow;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_tags, container, false);

        //Tag List
        initTagsRecyclerView(rootView);

        //Expandable button
        setUpExpandableFloatingButton(rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbarTitle.setText("Recipes");
        upArrow.setDisplayHomeAsUpEnabled(false);
    }

    @SuppressLint("FragmentLiveDataObserve")
    private void initTagsRecyclerView(View rootView){
        viewModel = new ViewModelProvider(this).get(ViewModelTags.class);

        viewModel.getTags().observe(this, new Observer<List<Tag>>() {
            @Override
            public void onChanged(List<Tag> tags) {
                adapterTag.setData(tags);
            }
        });
        viewModel.getTagsFromDatabase();

        tagList = rootView.findViewById(R.id.rv_tags);
        tagList.hasFixedSize();
        tagList.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        adapterTag = new AdapterTag(viewModel.getTags().getValue(), this);
        tagList.setAdapter(adapterTag);
    }

    private void setUpExpandableFloatingButton(final View rootView){
        add_btn = rootView.findViewById(R.id.add_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new FragmentRecipeCreate(null);
                toolbarTitle.setText("Create recipe");
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                upArrow.setDisplayHomeAsUpEnabled(true);
            }
        });
    }

    @Override
    public void onClick(int position) {
        Fragment fragment = null;
        fragment = new FragmentTagsExpanded(supportFragmentManager, toolbarTitle, upArrow, viewModel.getTag(position));
        toolbarTitle.setText(viewModel.getTag(position).getName());
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
        upArrow.setDisplayHomeAsUpEnabled(true);
    }
}