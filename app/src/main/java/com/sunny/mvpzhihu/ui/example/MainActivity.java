package com.sunny.mvpzhihu.ui.example;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sunny.mvpzhihu.R;
import com.sunny.mvpzhihu.data.SyncService;
import com.sunny.mvpzhihu.ui.base.BaseActivity;
import com.sunny.mvpzhihu.utils.ActivityUtil;

public class MainActivity extends BaseActivity {

    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "com.sunny.mvpzhihu.ui.example.MainActivity.EXTRA_TRIGGER_SYNC_FLAG";

    /**
     * Return an Intent to start this Activity.
     * triggerDataSyncOnCreate allows disabling the background sync service onCreate. Should
     * only be set to false during testing.
     */
    public static Intent getStartIntent(Context context, boolean triggerDataSyncOnCreate) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_TRIGGER_SYNC_FLAG, triggerDataSyncOnCreate);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainFragment mainFragment = (MainFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (mainFragment == null) {
            // Create the fragment
            mainFragment = MainFragment.newInstance();
            ActivityUtil.addFragmentToActivity(
                    getSupportFragmentManager(), mainFragment, R.id.contentFrame);
        }

        if (getIntent().getBooleanExtra(EXTRA_TRIGGER_SYNC_FLAG, true)) {
            startService(SyncService.getStartIntent(this));
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_example;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
