package com.maurilio.simplepingapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maurílio
 */
public class IcmpPinger implements Runnable{

    private final String host;
    private final long timeout;
    public static String last;
    
    public IcmpPinger(String host, long timeout) {
        this.host = host;
        this.timeout = timeout;
    }

    @Override
    public void run() {
        try {
            Process ping = Runtime.getRuntime().exec(Config.ping.concat(" -w ").concat(String.valueOf(timeout)).concat(" ").concat(host));
            BufferedReader in = new BufferedReader(new InputStreamReader(ping.getInputStream()));
            String a;
            String result = "";
            boolean sameBytes = true;
            while( (a = in.readLine()) != null){
                result = result.concat(a.concat("\n"));
                if (sameBytes){
                    sameBytes = a.contains("bytes=32");
                }
            }
            last = result;
            Logger.getLogger(IcmpPinger.class.getName()).log(Level.INFO, result);
            if (!sameBytes || !result.contains("0%")){
                Report.report(host);
            }
        } catch (IOException ex) {
            Logger.getLogger(IcmpPinger.class.getName()).log(Level.SEVERE, "ERRO", ex);
        }
    }

}
