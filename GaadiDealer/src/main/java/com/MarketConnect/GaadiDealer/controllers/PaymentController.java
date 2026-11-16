package com.MarketConnect.GaadiDealer.controllers;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping("/verifyPayment")
    public ResponseEntity<?> verifyPaymentSign(@RequestBody Map<String,String> paymentResult) throws RazorpayException
    {
        String orderId=paymentResult.get("razorpay_order_id");
        String payment_id=paymentResult.get("razorpay_payment_id");
        String sign=paymentResult.get("razorpay_signature");

        String generatedSign=Utils.getHash(
                orderId+"|"+payment_id,
                razorpaySecretKey
        );

        if(generatedSign.equals(sign))
        {
            return ResponseEntity.status(HttpStatus.OK).body("Payment sign verified successfully");
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Failed to verify payement signature");
        }
    }
}
