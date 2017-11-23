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
public class TracePinger implements Runnable{

    private final String host;
    public static String last;
    
    public TracePinger(String host) {
        this.host = host;
    }

    @Override
    public void run() {
        try {
            Process ping = Runtime.getRuntime().exec(Config.trace.concat(" ").concat(host));
            BufferedReader in = new BufferedReader(new InputStreamReader(ping.getInputStream()));
            String a;
            String result = "";
            while( (a = in.readLine()) != null){
                result = result.concat(a.concat("\n"));
            }
            last = result;
            Logger.getLogger(TracePinger.class.getName()).log(Level.INFO, result);
        } catch (IOException ex) {
            Logger.getLogger(TracePinger.class.getName()).log(Level.SEVERE, "ERRO", ex);
        }
    }
}
