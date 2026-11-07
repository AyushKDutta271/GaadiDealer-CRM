package com.MarketConnect.GaadiDealer.controllers;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Value("${razorpay.api}")
    private String razorpayKey;

    @Value("${razorpay.secretkey}")
    private String razorpaySecretKey;
    @PostMapping("/makePayment")
    public Map<String,Object> createOrder(@RequestBody Map<String,Object> data) throws Exception
    {
        Double amt=Double.parseDouble(data.get("amount").toString());
        RazorpayClient client=new RazorpayClient(razorpayKey,razorpaySecretKey);
        JSONObject orderRequest=new JSONObject();
        orderRequest.put("amount",amt*100);
        orderRequest.put("currency","INR");
        orderRequest.put("receipt","txn12345");
        try{
            //create order on razorpay and razorpay return
            Order order=client.orders.create(orderRequest);
            //we are sending response to frontend
            Map<String,Object> response=new HashMap<>();
            response.put("id",order.get("id"));
            response.put("amount",order.get("amount"));
            return response;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException("error creating razor order:"+e.getMessage());
        }
    }
}
