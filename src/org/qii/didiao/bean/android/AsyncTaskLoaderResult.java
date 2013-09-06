package org.qii.didiao.bean.android;

import android.os.Bundle;
import org.qii.didiao.support.error.WeiboException;

/**
 * User: qii
 * Date: 13-4-16
 */
public class AsyncTaskLoaderResult<E> {
    public E data;
    public WeiboException exception;
    public Bundle args;
}
