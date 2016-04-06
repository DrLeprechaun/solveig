/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leprechaun.solveig.http;

/**
 *
 * @author v_emelyanov
 */
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import com.leprechaun.solveig.FXMLController;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import com.leprechaun.solveig.entities.ResponseEntity;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.http.entity.ByteArrayEntity;

public class RequestExecutor {

    public static ResponseEntity simpleExecutor(String host, String port, String query) {

        ResponseEntity responseEntity = new ResponseEntity();

        HttpClient client = new DefaultHttpClient();

        String url = "http://" + host + ":" + port + "/query?q=" + query;

        System.out.println(url);

        HttpGet get = new HttpGet(url);

        try {
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            System.out.println(responseString);

            responseEntity.setCode(response.getStatusLine().toString());
            responseEntity.setBody(responseString);
        } catch (IOException e) {
            System.out.println(e.getMessage());

            responseEntity.setCode("ERROR");
            responseEntity.setBody(e.getMessage());
        }

        return responseEntity;
    }

    public static ResponseEntity intermediateExcutor(String host, String port, String dbName, String username, String password, String query) throws Exception {

        ResponseEntity responseEntity = new ResponseEntity();

        //INSERT
        
        if (query.toLowerCase().contains("insert")) { 
            
            //query.replaceFirst("insert", "");
            
            query = query.substring(7);

            HttpClient client = new DefaultHttpClient();
            String url = "http://" + host + ":" + port + "/write?";

            if (!dbName.isEmpty() || dbName == null) {
                url += "db=" + dbName + "&";
            }

            if (!username.isEmpty() || username == null) {
                url += "u=" + username + "&";
            }
            if (!password.isEmpty() || password == null) {
                url += "u=" + password + "&";
            }
            
            if (url.toCharArray()[url.toCharArray().length - 1] == '&') {
                url = url.substring(0, url.toCharArray().length - 1);
            }

            //String body = requestFormatter(query);
            HttpPost post = new HttpPost(url);
            
            System.out.println(query);

            HttpEntity httpEntity = new ByteArrayEntity(query.getBytes("UTF-8"));
            post.setEntity(httpEntity);
            
            try {
                HttpResponse response = client.execute(post);
                HttpEntity entity = response.getEntity();
                /*String responseString = EntityUtils.toString(entity, "UTF-8");
                System.out.println(responseString);

                responseEntity.setCode(response.getStatusLine().toString());
                responseEntity.setBody(responseString);*/
                responseEntity.setCode("OK");
                responseEntity.setBody("");
            } catch (IOException e) {
                System.out.println(e.getMessage());

                responseEntity.setCode("ERROR");
                responseEntity.setBody(e.getMessage());
            }

        } //SELECT
        else {

            HttpClient client = new DefaultHttpClient();

            String url = "http://" + host + ":" + port + "/query?";

            if (!dbName.isEmpty() || dbName == null) {
                url += "db=" + dbName + "&";
            }

            if (!username.isEmpty() || username == null) {
                url += "u=" + username + "&";
            }
            if (!password.isEmpty() || password == null) {
                url += "u=" + password + "&";
            }

            url += "q=" + requestFormatter(query);

            HttpGet get = new HttpGet(url);

            try {
                HttpResponse response = client.execute(get);
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity, "UTF-8");
                System.out.println(responseString);

                responseEntity.setCode(response.getStatusLine().toString());
                responseEntity.setBody(responseString);
            } catch (IOException e) {
                System.out.println(e.getMessage());

                responseEntity.setCode("ERROR");
                responseEntity.setBody(e.getMessage());
            }
        }

        return responseEntity;
    }

    private static String requestFormatter(String primordialRequest) {

        String formattedRequest = new String();

        try {

            formattedRequest = URLEncoder.encode(primordialRequest, "UTF-8");
        } catch (UnsupportedEncodingException ignored) {
            //
        }

        return formattedRequest;
    }
}
