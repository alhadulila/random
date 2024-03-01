package local.htss.apgo.lib.lang;

import java.util.HashMap;

public class LangMeta {
    private String code;
    private String author;
    private String name;
    private String localName;

    public LangMeta(HashMap<String, String> hashMap) {
        code = hashMap.get("meta.code");
        author = hashMap.get("meta.author");
        name = hashMap.get("meta.name.english");
        localName = hashMap.get("meta.name.local");
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }
}
