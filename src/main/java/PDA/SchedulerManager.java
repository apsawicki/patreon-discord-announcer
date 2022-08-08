package PDA;

import PDA.selenium.PatreonThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerManager {

    @Autowired
    PatreonThread patreonThread;

    @Scheduled(fixedRate = 60000)
    public void patreonThread() { // TODO: for threading maybe get all guilds, then create x amount of threads for y guilds then feed patreonThread List of size z guilds to parse
        patreonThread.run();
    }
}
