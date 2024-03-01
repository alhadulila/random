package local.htss.apgo.net.protocol;

public class ErrorCodes {
    public static class KeySearchCodes {
        public static final String FULL = "key.search.found.profile";
        public static final String FOUND = "key.search.found.key";
        public static final String NONE = "key.search.found.not";
        public static final String INVALID = "key.search.invalid.request";
        //key found, but profile cannot be returned because it is a special system account
        public static final String SYSTEM = "key.search.found.system";
        //key found, but profile cannot be returned because it is a hidden system account
        public static final String HIDDEN = "key.search.found.hidden";
        public static final String DENIED = "key.search.invalid.denied";
    }
}
