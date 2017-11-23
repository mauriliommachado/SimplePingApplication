/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maurilio.simplepingapplication;

/**
 *
 * @author Maurílio
 */
public class SimplePingApplication {

    public static void main(String[] args) {
        Config.readConfig();
        Config.setLoggerHandle();
        Config.hosts.stream().forEach(h -> {
            new Thread(new ThreadController(Config.intervalIcmp, Config.timeoutIcmp, h,Type.ICMP)).start();
        });
        Config.hosts.stream().forEach(h -> {
            new Thread(new ThreadController(Config.intervalTcp, Config.timeoutTcp, h,Type.TCP)).start();
        });
        Config.hosts.stream().forEach(h -> {
            new Thread(new ThreadController(Config.intervalTrace, 0, h,Type.TRACE)).start();
        });
    }

}
