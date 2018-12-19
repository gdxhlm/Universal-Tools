package cn.gdxhlm.universaltools.db;

public class ZgjmSetGet {
    private String title;
    private String desc;
    private String list;

   public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ZgjmSetGet{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", list='" + list + '\'' +
                '}';
    }
}
