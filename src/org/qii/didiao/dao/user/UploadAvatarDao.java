package org.qii.didiao.dao.user;

import org.qii.didiao.dao.URLHelper;
import org.qii.didiao.support.error.WeiboException;
import org.qii.didiao.support.http.HttpUtility;

import java.util.HashMap;
import java.util.Map;

/**
 * User: qii
 * Date: 13-3-2
 * http://open.weibo.com/wiki/2/account/avatar/upload
 */
public class UploadAvatarDao {


    public boolean upload() throws WeiboException {
        String url = URLHelper.AVATAR_UPLOAD;
        Map<String, String> map = new HashMap<String, String>();
        map.put("access_token", access_token);
        return HttpUtility.getInstance().executeUploadTask(url, map, image, "image", null);
    }

    private String access_token;
    private String image;

    public UploadAvatarDao(String token, String image) {
        this.access_token = token;
        this.image = image;
    }
}
