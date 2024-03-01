package local.htss.apgo.lib.log;

import java.util.Calendar;
import java.util.TimeZone;

public class Logger {
    private String name;

    public Logger(String name) {
        this.name = name;
    }

    public void msg(Level level, String subName, String data, boolean file, boolean console) {
        if(subName == null || subName.isEmpty()) {
            subName = "DEFAULT";
        }
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        String output = calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH)
                + " "
                + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND)
                + " " +
                level.getDisplay() + " [" + getName() + "/" + subName + "] " + data
                + "\n";
        if(console) {
            System.out.print(output);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public enum Level {
        ERROR("ERROR")
        ;
        private String display;

        Level(String display) {
            this.display = display;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }
    }
}
