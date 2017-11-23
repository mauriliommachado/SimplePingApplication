package com.maurilio.simplepingapplication;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maurílio
 */
public class ThreadController implements Runnable {

    private final long interval;
    private final String host;
    private final long timeout;
    public final int type;

    public ThreadController(long interval, long timeout, String host,int type) {
        this.interval = interval;
        this.host = host;
        this.timeout = timeout;
        this.type = type;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(interval);
                switch (type) {
                    case Type.ICMP:
                        new Thread(new IcmpPinger(host, timeout)).start();
                        break;
                    case Type.TCP:
                        new Thread(new TcpPinger(host, timeout)).start();
                        break;
                    default:
                        new Thread(new TracePinger(host)).start();
                        break;
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
