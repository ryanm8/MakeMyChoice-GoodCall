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

    @Schedule(dayOfWeek = "*", month = "*", hour = "14", dayOfMonth = "*", year = "*", minute = "10", second = "0", persistent = false)
    public void myTimer() {
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate1 = df1.format(new Date());
        List<Question> questionList = (List<Question>) em.
                createNamedQuery("Question.natfindByDueDate").setParameter(1, 
                formattedDate1 + " 00:00:00").getResultList();
        for(Question q : questionList)
        {
            System.out.println("Sending Text");
            User user = (User) em.createNamedQuery("user.findById").setParameter(1, q.getAskerID()).getSingleResult();
            sendText(user, q);
            System.out.println("Text Sent");
        }
    }

    /**
     * Sends text message to a specific user when a Due date has passed
     * @param poster - The user who posted the question
     * @param answer - The result of voting.
     */
    public void sendText(User poster, Question answer) {
    
        try{
        URL url = new URL("https://app.eztexting.com/api/sending/");
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("user", "MakeMyChoice");
        params.put("pass", "monkeymonkey123");
        params.put("phonenumber", poster.getCellNumber());
        params.put("subject","MMC");
        String message = "";
        if(answer.getNumberLeftVotes() > answer.getNumberRightVotes())
        {
            // Left > Right
            message = "Hi! \"" + 
                    answer.getTitle() + "\" has been answered." +
                    " Your results are " + answer.getNumberLeftVotes() + "-" + answer.getNumberRightVotes() +
                    ". In favor of the Left Option.";
        }
        else if (answer.getNumberLeftVotes() < answer.getNumberRightVotes())
        {
            // Left < Right
            message = "Hi! \"" + 
                    answer.getTitle() + "\" has been answered." +
                    " Your results are " + answer.getNumberLeftVotes() + "-" + answer.getNumberRightVotes() +
                    ". In favor of the Right Option.";
        }
        else
        {
            // Left = Right
            message = "Hi! \"" + 
                    answer.getTitle() + "\" has been answered." +
                    " Your results are " + answer.getNumberLeftVotes() + "-" + answer.getNumberRightVotes() +
                    ". A tie.";
        }
        params.put("message", message);
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
