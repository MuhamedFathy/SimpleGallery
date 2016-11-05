package net.mEmoZz.gallery.views.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import net.mEmoZz.gallery.Constants;
import net.mEmoZz.gallery.R;
import net.mEmoZz.gallery.adapters.GalleryAdapter;
import net.mEmoZz.gallery.models.ImageModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainScreen extends AppCompatActivity {

    private Toolbar toolbar;
    public RecyclerView picList;
    private SwipeRefreshLayout refreshLayout;
    private StaggeredGridLayoutManager manager;
    private GalleryAdapter adapter;
    private List<ImageModel> links = new ArrayList<>();
    private boolean loading = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);

        initView();
        randomLinks();
        initList();
        initSwipeToRefresh();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        picList = (RecyclerView) findViewById(R.id.picList);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
    }

    private void initList() {
        int rowSize;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            rowSize = 2;
        else
            rowSize = 3;
        manager = new StaggeredGridLayoutManager(rowSize, StaggeredGridLayoutManager.VERTICAL);
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        picList.setLayoutManager(manager);
        adapter = new GalleryAdapter(this, links);
        picList.setAdapter(adapter);
        refreshLayout.setRefreshing(false);
        activateEndless();
    }

    private void activateEndless() {
        picList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = manager.getChildCount();
                totalItemCount = manager.getItemCount();
                int[] firstVisibleItems = null;
                firstVisibleItems = manager.findFirstVisibleItemPositions(firstVisibleItems);
                if (firstVisibleItems != null && firstVisibleItems.length > 0) {
                    pastVisibleItems = firstVisibleItems[0];
                }

                if (loading) {
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        loading = false;

                        // Fake timer to simulate connection
                        new CountDownTimer(1400, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                loading = true;
                                randomLinks();
                                if (adapter != null)
                                    adapter.notifyItemRangeChanged(adapter.getItemCount(), links.size() - 1);
                            }
                        }.start();
                    }
                }
            }
        });
    }

    private void initSwipeToRefresh() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (adapter != null) adapter.clear();
                randomLinks();
                initList();
            }
        });
    }

    /**
     * Generate links with different sizes
     * and fill {@link ImageModel} with it
     * then add it into the list
     */
    private void randomLinks() {
        for (int i = 0; i < 12; i++) {
            int width = new Random().nextInt(1920 - 200 + 1) + 200;
            int height = new Random().nextInt(1420 - 200 + 1) + 200;
            ImageModel imageModel = new ImageModel();
            imageModel.setUrl("http://lorempixel.com/" + width + "/" + height + "/");
            imageModel.setWidth(width);
            imageModel.setHeight(height);
            float ratio = (float) imageModel.getHeight() / (float) imageModel.getWidth();
            imageModel.setRatio(ratio);
            links.add(imageModel);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.CURRENT_POSITION_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                int position = data.getIntExtra(Constants.KEY_POSITION, -1);
                if (position != -1)
                    picList.smoothScrollToPosition(position);
            }
        }
    }
}
