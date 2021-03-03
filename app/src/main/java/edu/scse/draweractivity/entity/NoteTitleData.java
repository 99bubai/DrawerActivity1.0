package edu.scse.draweractivity.entity;
//实体类，存放home界面显示的笔记标题
public class NoteTitleData {
    private String title;

    public NoteTitleData() {}

    public NoteTitleData(String title) {
        this.title=title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String content) {
        this.title = content;
    }

}
