package local.htss.apgo.lib.lang;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class Lang {
    private HashMap<String, String> translations;
    private LangMeta meta;
    public Lang(String path, LangSource source) throws IOException {
        InputStream inputStream;
        if(source == LangSource.RESSOURCE_ANY) {
            inputStream = getClass().getResourceAsStream(path);
        } else if(source == LangSource.RESSOURCE_DEF) {
            inputStream = getClass().getResourceAsStream("/lang/" + path + ".properties");
        } else {
            throw new IllegalArgumentException("Undocumented!");
        }
        Properties properties = new Properties();
        properties.load(inputStream);
        translations = new HashMap<>();
        for (Object o : properties.keySet()) {
            translations.put(
                    (String) o,
                    properties.getProperty((String) o)
            );
        }
        properties.clear();
        meta = new LangMeta(translations);
    }

    public String translateFormatted(String key, Object... args) {
        return null;
    }

    public String getTranslation(String key) {
        return translations.getOrDefault(key, "error.translation.notfound");
    }
    public HashMap<String, String> getTranslations() {
        return translations;
    }

    public void setTranslations(HashMap<String, String> translations) {
        this.translations = translations;
    }

    public LangMeta getMeta() {
        return meta;
    }

    public void setMeta(LangMeta meta) {
        this.meta = meta;
    }

    public enum LangSource {
        RESSOURCE_ANY,
        RESSOURCE_DEF,
        UNDOCUMENTED
    }
}
