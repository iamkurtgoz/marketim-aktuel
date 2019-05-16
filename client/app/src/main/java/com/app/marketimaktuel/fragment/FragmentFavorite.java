package com.app.marketimaktuel.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.marketimaktuel.Config;
import com.app.marketimaktuel.R;
import com.app.marketimaktuel.activities.ActivityNewsDetail;
import com.app.marketimaktuel.activities.MainActivity;
import com.app.marketimaktuel.adapter.AdapterNews;
import com.app.marketimaktuel.models.News;
import com.app.marketimaktuel.utils.DbHandler;

import java.util.ArrayList;
import java.util.List;

public class FragmentFavorite extends Fragment {

    private List<News> data = new ArrayList<News>();
    private View root_view, parent_view;
    private RecyclerView recyclerView;
    private AdapterNews mAdapter;
    private MainActivity mainActivity;
    LinearLayout lyt_root;
    DbHandler databaseHandler;

    public FragmentFavorite() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.fragment_favorite, null);
        parent_view = getActivity().findViewById(R.id.main_content);
        lyt_root = root_view.findViewById(R.id.root_layout);

        recyclerView = root_view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        if (Config.ENABLE_RTL_MODE) {
            lyt_root.setRotationY(180);
        }

        return root_view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();

        databaseHandler = new DbHandler(getActivity());
        data = databaseHandler.getAllData();

        //set data and list adapter
        mAdapter = new AdapterNews(getActivity(), recyclerView, data);
        recyclerView.setAdapter(mAdapter);

        if (data.size() == 0) {
            showNoItemView(true);
        } else {
            showNoItemView(false);
        }

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterNews.OnItemClickListener() {
            @Override
            public void onItemClick(View v, News obj, int position) {
                ActivityNewsDetail.navigate((MainActivity) getActivity(), v.findViewById(R.id.image), obj);
            }
        });

    }

    private void showNoItemView(boolean show) {
        View lyt_no_item = root_view.findViewById(R.id.lyt_no_item_later);
        ((TextView) root_view.findViewById(R.id.no_item_message)).setText(R.string.no_favorite_found);
        if (show) {
            recyclerView.setVisibility(View.GONE);
            lyt_no_item.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            lyt_no_item.setVisibility(View.GONE);
        }
    }
}
