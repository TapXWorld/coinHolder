package org.example;

import org.example.logic.CoinPriceMonitoringLogic;
import org.java_websocket.WebSocket;

import java.net.URI;
import java.net.URISyntaxException;

public class CoinPriceMonitoringJob implements Runnable {

    public void createWebsocketHandler(CoinPriceMonitoringLogic logic) {
        String json = "{\"id\":4,\"method\":\"depth.subscribe\",\"params\":[\"BABYDOGEUSDT\",50,\"0.0000000001\"]}\t";
        String json1 = "{\"id\":7,\"method\":\"server.auth\",\"params\":[\"F5E6259A8938498C8F01E2745B3178C16CAF372B85F8D795424E280139A003F2\",\"web\"]}\t";
        String json2 = "{\"id\":13,\"method\":\"deals.subscribe\",\"params\":[\"BABYDOGEUSDT\",\"CETUSDT\",\"UNIUSDT\",\"XDAGUSDT\"]}";

        logic.send(json);
        logic.send(json1);
        logic.send(json2);
    }

    @Override
    public void run()  {
        try {
            CoinPriceMonitoringLogic logic = new CoinPriceMonitoringLogic(new URI("wss://socket.coinex.com/"));
            logic.connect();

            while (!logic.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                System.out.println("Connecting....");
                Thread.sleep(1000);

                createWebsocketHandler(logic);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
