package com.maurilio.simplepingapplication;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maurílio
 */
public class TcpPinger implements Runnable {

    private final String host;
    private final long timeout;
    public static String last;

    public TcpPinger(String host, long timeout) {
        this.host = host;
        this.timeout = timeout;
    }

    @Override
    public void run() {
        URL url;
        try {
            url = new URL("http://".concat(host));
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setConnectTimeout((int) timeout);
            httpCon.setRequestMethod("GET");
            OutputStreamWriter out = new OutputStreamWriter(
                    httpCon.getOutputStream());
            String result = String.valueOf(httpCon.getResponseCode()).concat(" ").concat(httpCon.getResponseMessage());
            last = result;
            Logger.getLogger(TcpPinger.class.getName()).log(Level.INFO, result);
            out.close();
            if(httpCon.getResponseCode() != 200){
                Report.report(host);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(TcpPinger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TcpPinger.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
