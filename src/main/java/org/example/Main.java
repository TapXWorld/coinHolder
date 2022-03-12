package org.example;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Hello world!
 */
public class Main
{
    public static void main( String[] args ) throws URISyntaxException {

        new Thread(new CoinPriceMonitoringJob()).start();

        System.out.println("main ---> " + Thread.currentThread().getName());
        while(true) {

        }
    }
}
