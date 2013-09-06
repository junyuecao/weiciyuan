package org.qii.didiao.ui.dm;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import org.qii.didiao.R;
import org.qii.didiao.ui.interfaces.AbstractAppActivity;
import org.qii.didiao.ui.main.MainTimeLineActivity;

/**
 * User: qii
 * Date: 13-1-23
 */
public class DMUserListActivity extends AbstractAppActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(getString(R.string.dm));
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new DMUserListFragment())
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
