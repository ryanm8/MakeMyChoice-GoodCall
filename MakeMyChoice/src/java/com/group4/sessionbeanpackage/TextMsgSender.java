/*
 * Created by Chris Hoffman on 2014.12.05  * 
 * Copyright © 2014 Chris Hoffman. All rights reserved. * 
 */

package com.group4.sessionbeanpackage;

import com.group4.entitypackage.Question;
import com.group4.entitypackage.User;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.net.ssl.HttpsURLConnection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Chris
 */
@Singleton
public class TextMsgSender {
    
    @PersistenceContext 
    private EntityManager em;

    @Schedule(dayOfWeek = "*", month = "*", hour = "23", dayOfMonth = "*", year = "*", minute = "45", second = "0", persistent = false)
    
    public void myTimer() {
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate1 = df1.format(new Date());
        //System.out.println("Timer event: " + new Date());
        List<Question> questionList = (List<Question>) em.
                createNamedQuery("Question.natfindByDueDate").setParameter(1, 
                formattedDate1 + " 00:00:00").getResultList();
        //sendText(null, null);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public void sendText(User poster, Question answer) {
    
        try{
        URL url = new URL("https://app.eztexting.com/api/sending/");
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("user", "MakeMyChoice");
        params.put("pass", "monkeymonkey123");
        params.put("phonenumber", "5406218866");
        params.put("subject","Subject");
        params.put("message", "IT WORKS WOOT");
        params.put("express","1");

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        for (int c; (c = in.read()) >= 0; System.out.print((char)c));
        
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
}
