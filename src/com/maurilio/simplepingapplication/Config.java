package com.maurilio.simplepingapplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maurílio
 */
public class Config {

    public static long intervalTcp;
    public static long timeoutTcp;
    public static long intervalIcmp;
    public static long timeoutIcmp;
    public static long intervalTrace;
    public static List<String> hosts;
    public static String reportUrl;
    public static String ping;
    public static String trace;

    public static void setLoggerHandle() {
        try {
            FileHandler fileHandler = new FileHandler(System.getProperty("user.dir") + File.separator + "pinger.log", false);    
            Logger.getLogger(IcmpPinger.class.getName()).addHandler(fileHandler);
            Logger.getLogger(TcpPinger.class.getName()).addHandler(fileHandler);
            Logger.getLogger(TracePinger.class.getName()).addHandler(fileHandler);
            Logger.getLogger(Report.class.getName()).addHandler(fileHandler);
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void readConfig() {
        File f = new File(System.getProperty("user.dir") + File.separator + "pinger.properties");
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(f);
            // load a properties file
            prop.load(input);
            // get the property value and print it out
            hosts = Arrays.asList(prop.getProperty("HOSTS").split(";"));
            timeoutIcmp = Long.valueOf(prop.getProperty("TIMEOUT_ICMP"));
            intervalIcmp = Long.valueOf(prop.getProperty("DELAY_ICMP"));
            timeoutTcp = Long.valueOf(prop.getProperty("TIMEOUT_TCP"));
            intervalTcp = Long.valueOf(prop.getProperty("DELAY_TCP"));
            intervalTrace = Long.valueOf(prop.getProperty("DELAY_TRACE"));
            reportUrl = prop.getProperty("REPORT_URL");
            ping = prop.getProperty("PING");
            trace = prop.getProperty("TRACE");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
