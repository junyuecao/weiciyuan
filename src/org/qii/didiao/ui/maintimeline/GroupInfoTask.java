package org.qii.didiao.ui.maintimeline;

import org.qii.didiao.bean.GroupListBean;
import org.qii.didiao.dao.maintimeline.FriendGroupDao;
import org.qii.didiao.support.database.GroupDBTask;
import org.qii.didiao.support.error.WeiboException;
import org.qii.didiao.support.lib.MyAsyncTask;
import org.qii.didiao.support.utils.GlobalContext;

/**
 * User: qii
 * Date: 12-12-28
 */
public class GroupInfoTask extends MyAsyncTask<Void, GroupListBean, GroupListBean> {


    private WeiboException e;

    private String token;

    public GroupInfoTask(String token) {
        this.token = token;
    }

    @Override
    protected GroupListBean doInBackground(Void... params) {
        try {
            return new FriendGroupDao(token).getGroup();
        } catch (WeiboException e) {
            this.e = e;
            cancel(true);
        }
        return null;
    }


    @Override
    protected void onPostExecute(GroupListBean groupListBean) {
        GroupDBTask.update(groupListBean, GlobalContext.getInstance().getCurrentAccountId());
        GlobalContext.getInstance().setGroup(groupListBean);

        super.onPostExecute(groupListBean);
    }

}