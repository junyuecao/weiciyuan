package org.qii.didiao.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableString;
import android.text.TextUtils;
import org.qii.didiao.support.utils.ListViewTool;
import org.qii.didiao.support.utils.ObjectToStringUtility;
import org.qii.didiao.support.utils.TimeTool;

/**
 * User: Jiang Qi
 * Date: 12-8-2
 */
public class CommentBean extends ItemBean implements Parcelable {
    private String created_at;
    private long id;
    private String idstr;
    private String text;
    private String source;
    private String mid;
    private long mills;

    private UserBean user;
    private MessageBean status;
    private CommentBean reply_comment;

    private transient SpannableString listViewSpannableString;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(created_at);
        dest.writeLong(id);

        dest.writeString(idstr);
        dest.writeString(text);
        dest.writeString(source);
        dest.writeString(mid);
        dest.writeLong(mills);


        dest.writeParcelable(user, flags);
        dest.writeParcelable(status, flags);
        dest.writeParcelable(reply_comment, flags);

    }

    public static final Parcelable.Creator<CommentBean> CREATOR =
            new Parcelable.Creator<CommentBean>() {
                public CommentBean createFromParcel(Parcel in) {
                    CommentBean commentBean = new CommentBean();
                    commentBean.created_at = in.readString();
                    commentBean.id = in.readLong();
                    commentBean.idstr = in.readString();
                    commentBean.text = in.readString();
                    commentBean.source = in.readString();
                    commentBean.mid = in.readString();

                    commentBean.mills = in.readLong();

                    commentBean.user = in.readParcelable(UserBean.class.getClassLoader());
                    commentBean.status = in.readParcelable(MessageBean.class.getClassLoader());
                    commentBean.reply_comment = in.readParcelable(CommentBean.class.getClassLoader());

                    return commentBean;
                }

                public CommentBean[] newArray(int size) {
                    return new CommentBean[size];
                }
            };


    public CommentBean getReply_comment() {
        return reply_comment;
    }

    public void setReply_comment(CommentBean reply_comment) {
        this.reply_comment = reply_comment;
    }


    //comment timeline show comment
    public SpannableString getListViewSpannableString() {
        if (!TextUtils.isEmpty(listViewSpannableString)) {
            return listViewSpannableString;
        } else {
            ListViewTool.addJustHighLightLinks(this);
            return listViewSpannableString;
        }
    }

    public void setListViewSpannableString(SpannableString listViewSpannableString) {
        this.listViewSpannableString = listViewSpannableString;
    }


    public long getMills() {
        if (mills == 0L) {
            TimeTool.dealMills(this);
        }
        return mills;
    }

    public void setMills(long mills) {
        this.mills = mills;
    }

    public String getCreated_at() {

        return created_at;
    }

    public String getListviewItemShowTime() {
        return TimeTool.getListTime(this);
    }


    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return idstr;
    }

    public void setId(String id) {
        this.idstr = id;
    }

    public long getIdLong() {
        return this.id;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public MessageBean getStatus() {
        return status;
    }

    public void setStatus(MessageBean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return ObjectToStringUtility.toString(this);
    }
}
