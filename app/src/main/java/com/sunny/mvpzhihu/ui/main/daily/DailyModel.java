package com.sunny.mvpzhihu.ui.main.daily;

import com.sunny.mvpzhihu.data.model.bean.Story;

public class DailyModel {

    private Story mStory;
    private Boolean mIsRead;
    private String mDate;

    public DailyModel(Story story, Boolean isRead, String date) {
        mStory = story;
        mIsRead = isRead;
        mDate = date;
    }

    public Story getStory() {
        return mStory;
    }

    public void setStory(Story story) {
        mStory = story;
    }

    public Boolean getRead() {
        return mIsRead;
    }

    public void setRead(Boolean read) {
        mIsRead = read;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

}
