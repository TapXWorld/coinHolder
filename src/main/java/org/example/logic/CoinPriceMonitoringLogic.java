package org.example.logic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class CoinPriceMonitoringLogic extends WebSocketClient {

    public static HashMap<String,Double> holdCoins = null;

    public static HashMap<String,Double> newestPrice = null;

    {
        holdCoins = new HashMap<>();
        holdCoins.put("BABYDOGEUSDT", 585609920463.03996383);
        holdCoins.put("XDAGUSDT", 750d);
        holdCoins.put("UNIUSDT", 28d);
        holdCoins.put("CETUSDT", 110000d);

        newestPrice = new HashMap<>();
    }

    public CoinPriceMonitoringLogic(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println(">>>>>>>>>>>    open ");
    }

    @Override
    public void onMessage(String s) {
        JSONObject jsonObject = JSONObject.parseObject(s);

        if("deals.update".equals(jsonObject.getString("method"))) {
            JSONArray params = jsonObject.getJSONArray("params");
            newestPrice.put(params.getString(0), Double.parseDouble(params.getJSONArray(1).getJSONObject(0).getString("price")));
        }

        double price = 0;
        System.out.println();
        System.out.println("\033[31m---------------------------------------------------\033[0m");
        for(Map.Entry<String,Double> entry : newestPrice.entrySet()) {
            double temp = entry.getValue() * holdCoins.get(entry.getKey());
            price+= temp;
            System.out.format("%-15s  ----------------  %15f\n", entry.getKey(), temp);
        }

        System.out.format("\033[31m-------------------\033[0m  \033[32m%f\033[0m \033[31m-----------------\033[0m\n", price);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println(">>>>>>>>>>>     websocket close");
    }

    @Override
    public void onError(Exception e) {
        System.out.println(">>>>>>>>>>>     websocket error");
    }
}
