package org.qii.didiao.dao.dm;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.qii.didiao.bean.DMUserBean;
import org.qii.didiao.bean.DMUserListBean;
import org.qii.didiao.dao.URLHelper;
import org.qii.didiao.support.error.WeiboException;
import org.qii.didiao.support.http.HttpMethod;
import org.qii.didiao.support.http.HttpUtility;
import org.qii.didiao.support.settinghelper.SettingUtility;
import org.qii.didiao.support.utils.AppLogger;

import java.util.HashMap;
import java.util.Map;

/**
 * User: qii
 * Date: 12-11-14
 */
public class DMDao {

    private String access_token;
    private String cursor = "0";
    private String count ;

    public DMDao(String token) {
        this.access_token = token;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
        this.count = SettingUtility.getMsgCount();
    }

    public DMUserListBean getUserList() throws WeiboException {
        String url = URLHelper.DM_USERLIST;
        Map<String, String> map = new HashMap<String, String>();
        map.put("access_token", access_token);
        map.put("count", count);
        map.put("cursor", cursor);

        String jsonData = HttpUtility.getInstance().executeNormalTask(HttpMethod.Get, url, map);
        DMUserListBean value = null;
        try {
            value = new Gson().fromJson(jsonData, DMUserListBean.class);
            for (DMUserBean b : value.getItemList()) {
                b.getListViewSpannableString();
                b.getListviewItemShowTime();
            }
        } catch (JsonSyntaxException e) {

            AppLogger.e(e.getMessage());

        }
        return value;
    }

}
