package app.server.session;

import java.time.LocalDateTime;
import java.util.Map;

public class WatchDog extends Thread {
    private SessionService sessionService;
    private Map<Token, Session> map;

    public WatchDog(SessionService sessionService) {
        this.sessionService = sessionService;
    }
    @Override
    public void run(){
        map = sessionService.getMap();
           while (true){
               LocalDateTime time = LocalDateTime.now();
               for (Map.Entry<Token, Session> entry : map.entrySet()) {
                   var session = entry.getValue();
                   if(time.isBefore(session.getLastActivityTime())){
                       sessionService.remove(entry.getKey());
                   }
               }
           }
    }
}

