package net.mEmoZz.gallery.views.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import net.mEmoZz.gallery.Constants;
import net.mEmoZz.gallery.R;
import net.mEmoZz.gallery.adapters.GalleryPagerAdapter;
import net.mEmoZz.gallery.models.ImageModel;

import java.util.List;

public class GalleryPagerActivity extends AppCompatActivity {

    private ViewPager pager;
    private int currenItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_pager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        getList();
    }

    private void initView() {
        pager = (ViewPager) findViewById(R.id.galleryPager);
    }

    private void getList() {
        if (getIntent() != null) {
            List<ImageModel> modelList = getIntent().getParcelableArrayListExtra(Constants.KEY_LIST);
            int position = getIntent().getIntExtra(Constants.KEY_CLICKED_POSITION, 0);
            currenItem = position;
            if (modelList != null && modelList.size() > 0) {
                pager.setAdapter(new GalleryPagerAdapter(modelList));
                pager.setCurrentItem(position);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    pager.setTransitionName(Constants.TRANSITION_NAME);
            } else {
                Toast.makeText(this, getString(R.string.try_later), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult();
                if (currenItem == pager.getCurrentItem()) supportFinishAfterTransition();
                else finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult();
        if (currenItem == pager.getCurrentItem()) super.onBackPressed();
        else finish();
    }

    private void setResult() {
        setResult(RESULT_OK, new Intent().putExtra(Constants.KEY_POSITION, pager.getCurrentItem()));
    }
}
