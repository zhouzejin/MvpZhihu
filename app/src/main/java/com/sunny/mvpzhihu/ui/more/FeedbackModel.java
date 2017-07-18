package com.sunny.mvpzhihu.ui.more;

import cn.bmob.v3.BmobObject;

/**
 * The type Feedback model.
 * Created by Zhou Zejin on 2017/7/18.
 */
public class FeedbackModel extends BmobObject {

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;

}
