package org.qii.didiao.ui.userinfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import org.qii.didiao.R;
import org.qii.didiao.bean.UserBean;
import org.qii.didiao.ui.interfaces.AbstractAppActivity;
import org.qii.didiao.ui.interfaces.IUserInfo;
import org.qii.didiao.ui.main.MainTimeLineActivity;

/**
 * User: qii
 * Date: 12-8-18
 */
public class MyFavActivity extends AbstractAppActivity implements IUserInfo {
    private UserBean bean;


    @Override
    public UserBean getUser() {
        return bean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(getString(R.string.my_fav_list));
        String token = getIntent().getStringExtra("token");
        bean = (UserBean) getIntent().getParcelableExtra("user");
        if (getSupportFragmentManager().findFragmentByTag(MyFavListFragment.class.getName()) == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new MyFavListFragment(), MyFavListFragment.class.getName())
                    .commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                intent = new Intent(this, MainTimeLineActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
        }
        return false;
    }
}
