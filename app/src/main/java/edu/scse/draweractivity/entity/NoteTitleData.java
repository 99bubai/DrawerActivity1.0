package edu.scse.draweractivity.entity;
//实体类，存放home界面显示的笔记标题
public class NoteTitleData {
    private int imgId;
    private String content;

    public NoteTitleData() {}

    public NoteTitleData(int imgId,String content) {
        this.content=content;
        this.imgId=imgId;
    }

    public int getImgId() {
        return imgId;
    }

    public String getContent() {
        return content;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
