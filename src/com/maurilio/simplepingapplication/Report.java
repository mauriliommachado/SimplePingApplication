package com.maurilio.simplepingapplication;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.SequenceInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Maurílio
 */
class Report {

    static void report(String host) {
        URL url;
        String icmp = IcmpPinger.last;
        String ping = TcpPinger.last;
        String trace = TracePinger.last;
        try {
            url = new URL("http://".concat(Config.reportUrl));
            String postJsonData = "{\"host\":" + host + ",\"icmp_ping\":" + icmp + ",\"tcp_ping\":" + ping + ",\"trace\":" + trace + "}";
            Logger.getLogger(Report.class.getName()).log(Level.INFO, postJsonData);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setRequestMethod("POST");
            httpCon.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(httpCon.getOutputStream());
            wr.writeBytes(postJsonData);
            wr.flush();
            wr.close();
            OutputStreamWriter out = new OutputStreamWriter(
                    httpCon.getOutputStream());
            out.close();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, ex.getMessage());
        } catch (ProtocolException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
    }
}
