package org.qii.didiao.dao.fav;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.qii.didiao.bean.FavBean;
import org.qii.didiao.dao.URLHelper;
import org.qii.didiao.support.error.WeiboException;
import org.qii.didiao.support.http.HttpMethod;
import org.qii.didiao.support.http.HttpUtility;
import org.qii.didiao.support.utils.AppLogger;

import java.util.HashMap;
import java.util.Map;

/**
 * User: qii
 * Date: 12-8-22
 */
public class FavDao {
    private String access_token;
    private String id;

    public FavDao(String token, String id) {
        this.access_token = token;
        this.id = id;
    }

    public FavBean favIt() throws WeiboException {

        String url = URLHelper.FAV_CREATE;
        return executeTask(url);
    }

    public FavBean unFavIt() throws WeiboException {

        String url = URLHelper.FAV_DESTROY;
        return executeTask(url);

    }

    private FavBean executeTask(String url) throws WeiboException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("access_token", access_token);
        map.put("id", id);

        String jsonData = HttpUtility.getInstance().executeNormalTask(HttpMethod.Post, url, map);

        try {
            FavBean value = new Gson().fromJson(jsonData, FavBean.class);
            if (value != null)
                return value;
        } catch (JsonSyntaxException e) {
            AppLogger.e(e.getMessage());
        }

        return null;
    }
}
