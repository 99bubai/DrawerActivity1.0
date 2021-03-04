package edu.scse.draweractivity.entity;
//实体类，存放home界面显示的笔记标题
public class NoteTitleData {
    private String title;
    private String id;
    private String type;

    public NoteTitleData() {}

    public NoteTitleData(String title,String id,String type){this.title=title;this.id=id;this.type=type;}

    public String getTitle() {
        return title;
    }
    public String getId() {return id;}
    public String getType(){return type;}

    public void setTitle(String content) {
        this.title = content;
    }
    public void setId(String id){this.id=id;}
    public void setType(String type) { this.type = type; }
}
