package com.example.ajp.s_cape_app;


import com.studioidan.httpagent.HttpAgent;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by AJP on 4/26/17.
 */

public class Flights {

    String destination;
    String origin;
    String date; // yyyy-mm-dd

    public Flights(String destination, String origin, String date) {
        this.destination = destination;
        this.origin = origin;
        this.date = date;
    }

    public static void pullFlightInfo() throws IOException {

        String request = "https://api.test.sabre.com/v2/shop/flights/fares?origin=JFK&departuredate=2017-05-25&returndate=2017-05-27&pointofsalecountry=US&region=NORTH+AMERICA";
        URL url = new URL(request);
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.setDoOutput( true );
        conn.setInstanceFollowRedirects( false );
        conn.setRequestMethod( "GET" );
        conn.setRequestProperty( "Authorization", "Bearer T1RLAQIoJv0RMALf0YjCWh2hvspfJpk13hCeANhro9K26ztidBC/spmzAADAVNa9a7/ndotErQC7sTUrmoCZliddLQjWzECqblYEKLqRQHcK+CDYxE4d5W5mdIFGwoi9cBEKH1/oLko0jGHYf7r5CSKLnKGVnwG4D5ega1kWP9FgycozRAPvIR2biPBW9dA2lNeD68QybLCB1PgqnPC29AKX3Rj9MR7UrVCLEYwemvpAbDxeMEXyky8W8ef3HJH6GeJA6tDCHmfPpZIBCY7Q/pITbb2JgYIfAHOFNwT+y1pLnNrUoxvH6PYbxEPU\n");



//        String urlParameters = "apiKey=st712913487489412973204751572385&country=BR&currency=BRL&locale=pt-BR&originplace=SDU&destinationplace=GRU&outbounddate=2016-09-23&locationschema=Iata&adults=1";
//        byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
//        int    postDataLength = postData.length;

        //conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));

        conn.setUseCaches( false );
        try{
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            //wr.write(postData);
            wr.flush();
            wr.close();

            int responseCode = conn.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            //System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);
            System.out.println("Header Fields : " + conn.getHeaderFields());
        } catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }

    }
}
