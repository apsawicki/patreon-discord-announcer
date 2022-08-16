package PDA;

import PDA.beans.GuildBean;
import PDA.jpa.Guilds;
import PDA.selenium.PatreonThread;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SchedulerManager {

    @Autowired
    Guilds guilds;

    @Autowired
    ApplicationContext context;

    Logger log;

    public SchedulerManager() {
        this.log = (Logger) LoggerFactory.getLogger(this.getClass().getSimpleName());
    }

    /* TODO: threading, webdriver, guilds problem:

    I want to try and create threads during startup, then check every X amount of time to see if i should create more threads
    based off of how many guilds there are -- patreonThreads created shouldn't have to re-instantiate the webdriver each
    run of webscraping

    I want to give these threads a (kinda) producer consumer problem where the product are the GuildBean objects created when
    i do "guilds.getAllGuilds()" then the threads will consume a guild at a time until there are no more GuildBeans to consume.
    Then the threads will wait X amount of time for patreonThread() function below to call
     */

    @Scheduled(fixedDelay = 60000)
    public void patreonThread() throws InterruptedException { // currently 1 thread per guild

        List<GuildBean> gbList = guilds.getAllGuilds();

        Thread[] threads = new Thread[gbList.size()];

        // separate the counts because i might change the ratio of threads : guilds
        log.info("Current thread/guild count: {}/{}", threads.length, gbList.size());

        // create and run new threads
        for (int i = 0 ; i < gbList.size(); i++) {
            PatreonThread patreonThread = context.getBean("patreonThread", PatreonThread.class);
            patreonThread.setup(gbList.get(i));

            threads[i] = new Thread(patreonThread);
        }

        // run the threads
        for (Thread t : threads) {
            t.start();
        }

        // combine threads once done with their job
        for (Thread t : threads) {
            t.join();
            log.info("Thread '{}' joined", t.getId());
        }
    }
}
