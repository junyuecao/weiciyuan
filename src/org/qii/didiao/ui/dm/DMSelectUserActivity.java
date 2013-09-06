package org.qii.didiao.ui.dm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.qii.didiao.R;
import org.qii.didiao.bean.UserBean;
import org.qii.didiao.bean.UserListBean;
import org.qii.didiao.bean.android.AsyncTaskLoaderResult;
import org.qii.didiao.dao.search.SearchDao;
import org.qii.didiao.support.asyncdrawable.TimeLineBitmapDownloader;
import org.qii.didiao.support.lib.PerformanceImageView;
import org.qii.didiao.support.utils.GlobalContext;
import org.qii.didiao.ui.basefragment.AbstractFriendsFanListFragment;
import org.qii.didiao.ui.interfaces.AbstractAppActivity;
import org.qii.didiao.ui.interfaces.IUserInfo;
import org.qii.didiao.ui.loader.FriendUserLoader;
import org.qii.didiao.ui.main.MainTimeLineActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * User: qii
 * Date: 13-3-2
 */
public class DMSelectUserActivity extends AbstractAppActivity implements IUserInfo {

    private List<UserBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dmselectuseractivity_layout);
        getActionBar().setTitle(R.string.select_dm_receiver);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.list_content, new SelectFriendsListFragment(GlobalContext.getInstance().getCurrentAccountId()))
                    .commit();
        }

        AutoCompleteTextView search = (AutoCompleteTextView) findViewById(R.id.search);
        AutoCompleteAdapter adapter = new AutoCompleteAdapter(this, android.R.layout.simple_dropdown_item_1line);
        search.setAdapter(adapter);
        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("user", data.get(position));
                setResult(0, intent);
                finish();
            }
        });


    }

    @Override
    public UserBean getUser() {
        return GlobalContext.getInstance().getAccountBean().getInfo();
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

    private class AutoCompleteAdapter extends ArrayAdapter<UserBean> implements Filterable {

        private DMSelectUserActivity activity;

        public AutoCompleteAdapter(DMSelectUserActivity context, int textViewResourceId) {
            super(context, textViewResourceId);
            data = new ArrayList<UserBean>();
            this.activity = context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public UserBean getItem(int index) {
            return data.get(index);
        }

        @Override
        public Filter getFilter() {
            Filter myFilter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        SearchDao dao = new SearchDao(GlobalContext.getInstance().getSpecialToken(), constraint.toString());

                        try {
                            data = dao.getUserList().getUsers();
                        } catch (Exception e) {
                        }
                        // Now assign the values and count to the FilterResults object
                        filterResults.values = data;
                        filterResults.count = data.size();
                    }
                    return filterResults;
                }

                @Override
                public CharSequence convertResultToString(Object resultValue) {
                    return ((UserBean) resultValue).getScreen_name();
                }

                @Override
                protected void publishResults(CharSequence contraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return myFilter;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            convertView = activity.getLayoutInflater().inflate(R.layout.dm_search_user_dropdown_item_layout, parent, false);

            PerformanceImageView avatar = (PerformanceImageView) convertView.findViewById(R.id.avatar);
            TextView username = (TextView) convertView.findViewById(R.id.username);

            TimeLineBitmapDownloader.getInstance().downloadAvatar(avatar, getItem(position));
            username.setText(getItem(position).getScreen_name());

            return convertView;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            return getDropDownView(position, convertView, parent);
        }
    }

    public static class SelectFriendsListFragment extends AbstractFriendsFanListFragment {


        public SelectFriendsListFragment() {

        }

        public SelectFriendsListFragment(String uid) {
            super(uid);
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setHasOptionsMenu(false);
            setRetainInstance(false);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

        }

        @Override
        protected void buildActionBarSubtitle() {
            //empty
        }

        protected void listViewItemClick(AdapterView parent, View view, int position, long id) {
            Intent intent = new Intent();
            intent.putExtra("user", getList().getUsers().get(position));
            getActivity().setResult(0, intent);
            getActivity().finish();
        }

        @Override
        protected Loader<AsyncTaskLoaderResult<UserListBean>> onCreateNewMsgLoader(int id, Bundle args) {
            String token = GlobalContext.getInstance().getSpecialToken();
            String cursor = String.valueOf(0);
            return new FriendUserLoader(getActivity(), token, uid, cursor);
        }

        @Override
        protected Loader<AsyncTaskLoaderResult<UserListBean>> onCreateOldMsgLoader(int id, Bundle args) {

            if (getList().getUsers().size() > 0 && Integer.valueOf(getList().getNext_cursor()) == 0) {
                return null;
            }


            String token = GlobalContext.getInstance().getSpecialToken();
            String cursor = String.valueOf(bean.getNext_cursor());

            return new FriendUserLoader(getActivity(), token, uid, cursor);
        }
    }
}
