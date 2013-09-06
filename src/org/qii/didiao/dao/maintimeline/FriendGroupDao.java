package org.qii.didiao.dao.maintimeline;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.qii.didiao.bean.GroupListBean;
import org.qii.didiao.dao.URLHelper;
import org.qii.didiao.support.error.WeiboException;
import org.qii.didiao.support.http.HttpMethod;
import org.qii.didiao.support.http.HttpUtility;
import org.qii.didiao.support.utils.AppLogger;

import java.util.HashMap;
import java.util.Map;

/**
 * User: qii
 * Date: 12-10-17
 */
public class FriendGroupDao {


    public GroupListBean getGroup() throws WeiboException {

        String url = URLHelper.FRIENDSGROUP_INFO;

        Map<String, String> map = new HashMap<String, String>();
        map.put("access_token", access_token);

        String jsonData = HttpUtility.getInstance().executeNormalTask(HttpMethod.Get, url, map);

        Gson gson = new Gson();

        GroupListBean value = null;
        try {
            value = gson.fromJson(jsonData, GroupListBean.class);
        } catch (JsonSyntaxException e) {
            AppLogger.e(e.getMessage());
        }


        return value;
    }


    public FriendGroupDao(String token) {
        this.access_token = token;
    }

    private String access_token;
}
