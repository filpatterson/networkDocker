package Utils;

import java.util.Map;

public class BaseData {
    private String msg;
    private Map<String, String> link;
    private String data;
    private String mime_type;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    Map<String, String> getLink() {
        return link;
    }

    public void setLink(Map<String, String> link) {
        this.link = link;
    }

    String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    String getMime_type() {
        return mime_type;
    }

    public void setMime_type(String mime_type) {
        this.mime_type = mime_type;
    }
}
