package com.superafroman.mangopay;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Calendar;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.superafroman.mangopay.domain.User;

public class Request {

    public static void main(String[] args) {
        Request request = new Request();
        request.createUser();
    }


    private DefaultHttpClient httpClient = new DefaultHttpClient();

    private ObjectMapper objectMapper;

    public Request() {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
        httpClient.setRedirectStrategy(new LaxRedirectStrategy());
    }

    public User createUser() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1985);
        calendar.set(Calendar.MONTH, 5);
        calendar.set(Calendar.DATE, 2);

        User user = new User("Max", "Stewart", "max@dashdit.com", "NZ", calendar.getTime());

        long timestamp = System.currentTimeMillis();

        String host = "http://api-preprod.leetchi.com";
        String method = "POST";
        String url = "/v1/partner/dashdit/users?ts=" + timestamp;
        String body = null;
        try {
            body = objectMapper.writeValueAsString(user);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        HttpPost request = new HttpPost(host + url);
        try {
            request.setEntity(new StringEntity(body, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        String signature;
        try {
            signature = calculateSignature(method, url, body);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        request.addHeader("Content-Type", "application/json");
        request.addHeader("X-Leetchi-Signature", signature);

        try {
            HttpResponse response = httpClient.execute(request);
            System.out.println("response status - " + response.getStatusLine().getStatusCode());
            System.out.println(response);
            if (response.getStatusLine().getStatusCode() == 200) {
                InputStream content = response.getEntity().getContent();

//                byte[] bytes = new byte[1024];
//                while (content.read(bytes) > 0) {
//                    System.out.print(new String(bytes));
//                }

                User newUser = objectMapper.readValue(content, User.class);
                System.out.println(newUser);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    private String calculateSignature(String method, String url, String body) throws Exception {

        File f = new File(this.getClass().getResource("/mangopay").toURI());
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        byte[] keyBytes = new byte[(int) f.length()];
        dis.readFully(keyBytes);
        dis.close();

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory factory = KeyFactory.getInstance("RSA" /*, bouncy castle */);
        PrivateKey privateKey = factory.generatePrivate(keySpec);

        String message = method + "|" + url + "|" + body + "|";

        Signature sign = Signature.getInstance("SHA1withRSA");
        sign.initSign(privateKey);
        sign.update(message.getBytes("UTF-8"));
        return new String(Base64.encodeBase64(sign.sign()), "UTF-8");
    }
}
