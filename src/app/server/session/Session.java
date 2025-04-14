package app.server.session;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Session {
    private final Map<String, Object> map = new ConcurrentHashMap<>();
    public static final String USERNAME = "username";
    public static final String LAST_ACTIVITY_TIME = "death_time";

    public void put(String key, Object value) {
        map.put(key, value);
    }

    public LocalDateTime getLastActivityTime(){
        return (LocalDateTime)map.get(LAST_ACTIVITY_TIME);
    }

    public String getUsername(){
        return USERNAME;
    }

    public Object get(String key) {
        return map.get(key);
    }

    public String getString(String key) {
        return (String) get(key);
    }

    @Override
    public String toString() {
        return "Session{" +
                "map=" + map +
                '}';
    }
}
