package org.qii.didiao.ui.loader;

import android.content.Context;
import org.qii.didiao.bean.CommentListBean;
import org.qii.didiao.dao.maintimeline.CommentsTimeLineByMeDao;
import org.qii.didiao.support.error.WeiboException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * User: qii
 * Date: 13-4-18
 */
public class CommentsByMeMsgLoader extends AbstractAsyncNetRequestTaskLoader<CommentListBean> {

    private static Lock lock = new ReentrantLock();


    private String token;
    private String sinceId;
    private String maxId;
    private String accountId;

    public CommentsByMeMsgLoader(Context context, String accountId, String token, String sinceId, String maxId) {
        super(context);
        this.token = token;
        this.sinceId = sinceId;
        this.maxId = maxId;
        this.accountId = accountId;
    }


    public CommentListBean loadData() throws WeiboException {
        CommentsTimeLineByMeDao dao = new CommentsTimeLineByMeDao(token);
        dao.setSince_id(sinceId);
        dao.setMax_id(maxId);
        CommentListBean result = null;
        lock.lock();

        try {
            result = dao.getGSONMsgList();
        } finally {
            lock.unlock();
        }

        return result;
    }

}
