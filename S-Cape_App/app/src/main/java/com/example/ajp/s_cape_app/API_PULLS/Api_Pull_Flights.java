package com.example.ajp.s_cape_app.API_PULLS;

import com.google.api.client.http.HttpResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * Created by AJP on 5/8/17.
 */

public class Api_Pull_Flights {

    public static org.apache.http.HttpResponse callGetMethod(String airport, String departDate, String returnDate, int price) {
        org.apache.http.HttpResponse response = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.addHeader("Authorization", "Bearer T1RLAQLpVmQj2TSNTjVLKgabeTK1nElTJRArge+UyXbIqHROHvhwr2QnAADAqv40vRTFfai0zSEFVpLAukKTB6wKgvtnJzAcm1uPEtC+LSGzu7e4F8kVcQSGHjha11crwIPAehStUAsuKDpADZxKyJA2sqS9W5LBuThhx9a6KRzuOYprta7eq6NbiW4aF/RsCsA24at19YzLY8gQYPFOZd/fk0b9VXzMVQ3Bycu7MeGwJOA13EezLfhMQ/BGUJvR7Anu2Yu7/64eoUgq3EBVzEdCRlS8eyCINGA9HFAoGaioQrn9xqL/uYt0IwVS");
            request.setURI(new URI("https://api.test.sabre.com/v2/shop/flights/fares?origin="+airport+"&departuredate="+ departDate +"&returndate="+ returnDate +"&maxfare="+ String.valueOf(price) +"&pointofsalecountry=US&region=NORTH+AMERICA"));
            response = client.execute(request);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return response;
    }

}
