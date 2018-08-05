package com.example.whc.qqcomment;

public class PersonItem {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String name;
    private String toName;
    private String content;

    public PersonItem(String name, String toName, String content) {
        this.name = name;
        this.toName = toName;
        this.content = content;
    }

    public PersonItem() {
    }
}
