package org.qii.didiao.dao.fav;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.qii.didiao.bean.FavBean;
import org.qii.didiao.bean.FavListBean;
import org.qii.didiao.bean.MessageBean;
import org.qii.didiao.dao.URLHelper;
import org.qii.didiao.support.error.WeiboException;
import org.qii.didiao.support.http.HttpMethod;
import org.qii.didiao.support.http.HttpUtility;
import org.qii.didiao.support.settinghelper.SettingUtility;
import org.qii.didiao.support.utils.AppLogger;
import org.qii.didiao.support.utils.TimeTool;

import java.util.*;

/**
 * User: qii
 * Date: 12-8-18
 */
public class FavListDao {
    private String getMsgListJson() throws WeiboException {
        String url = URLHelper.MYFAV_LIST;

        Map<String, String> map = new HashMap<String, String>();
        map.put("access_token", access_token);
        map.put("count", count);
        map.put("page", page);


        String jsonData = HttpUtility.getInstance().executeNormalTask(HttpMethod.Get, url, map);

        return jsonData;
    }

    public FavListBean getGSONMsgList() throws WeiboException {

        String json = getMsgListJson();
        Gson gson = new Gson();

        FavListBean value = null;
        try {
            value = gson.fromJson(json, FavListBean.class);
        } catch (JsonSyntaxException e) {

            AppLogger.e(e.getMessage());
        }

        if (value != null) {
            List<MessageBean> msgList = new ArrayList<MessageBean>();
            int size = value.getFavorites().size();
            for (int i = 0; i < size; i++) {
                msgList.add(value.getFavorites().get(i).getStatus());
            }

            Iterator<FavBean> iterator = value.getFavorites().iterator();

            while (iterator.hasNext()) {

                FavBean msg = iterator.next();
                if (msg.getStatus().getUser() == null) {
                    iterator.remove();
                } else {
                    msg.getStatus().getListViewSpannableString();
                    TimeTool.dealMills(msg.getStatus());
                }
            }

        }

        return value;
    }


    private String access_token;
    private String count;
    private String page;


    public FavListDao(String access_token) {

        this.access_token = access_token;
        this.count = SettingUtility.getMsgCount();
    }


    public FavListDao setCount(String count) {
        this.count = count;
        return this;
    }

    public FavListDao setPage(String page) {
        this.page = page;
        return this;
    }


}
