/*
 * Created by Brian Green on 2014.12.07  * 
 * Copyright © 2014 Brian Green. All rights reserved. * 
 */
package com.group4.jsfclassespackage;

/**
 *
 * @author Brian
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;
import org.primefaces.event.CaptureEvent;
import javax.faces.application.FacesMessage;
import java.io.OutputStream;

@ManagedBean
@ViewScoped
public class PhotoCamView {

    private String filename;

    private String getRandomImageName() {
        int i = (int) (Math.random() * 10000000);

        return String.valueOf(i);
    }

    public String getFilename() {
        System.out.println("The filename is: " + filename);
        return filename;
    }

//    public void oncapture(CaptureEvent captureEvent) {
//        filename = getRandomImageName();
//        byte[] data = captureEvent.getData();
//        String userPID = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(),"#{str}", String.class);
//        userPID = userPID.replace("/", "");
//        filename = userPID;
//        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
//        String tempFileName = servletContext.getRealPath("") + File.separator + userPID;
//        String newFileName = servletContext.getRealPath("") + File.separator + userPID + File.separator + filename;
//        System.out.println(newFileName);
//        FacesMessage msg = new FacesMessage("Succesful", filename + " is uploaded.");
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//        FileImageOutputStream imageOutput;
//        try {
//            File myFile = new File(tempFileName);
//            myFile.mkdirs();
//            File actualFile = new File(newFileName);
//            FileOutputStream fos = new FileOutputStream(actualFile);
//            fos.write(data, 0, data.length);
//            fos.close();
//        }
//        catch(IOException e) {
//            throw new FacesException("Error in writing captured image.", e);
//        }
//    }
//    public void oncapture(CaptureEvent captureEvent) {
//        filename = getRandomImageName();
//        byte[] data = captureEvent.getData();
//         
//        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
//        String newFileName = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "demo" +
//                                    File.separator + "images" + File.separator + "photocam" + File.separator + filename + ".png";
//         
//        FileImageOutputStream imageOutput;
//        try {
//            imageOutput = new FileImageOutputStream(new File(newFileName));
//            imageOutput.write(data, 0, data.length);
//            imageOutput.close();
//        }
//        catch(IOException e) {
//            throw new FacesException("Error in writing captured image.", e);
//        }
//    }

    public void oncapture(CaptureEvent captureEvent) {
        filename = getRandomImageName();
        byte[] data = captureEvent.getData();
        String userPID = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{user.pid}", String.class);
        userPID = userPID.replace("/", "");
        filename = userPID;
        System.out.println("USER PID IS:" + userPID);
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String tempFileName = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "images";
        String newFileName = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "images" + File.separator + filename + ".png";
        FacesMessage msg = new FacesMessage("Succesful", filename + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        FileImageOutputStream imageOutput;
        try {
            //File myFile = new File(tempFileName);
            //myFile.mkdirs();
            File myActualFile = new File(newFileName);
            imageOutput = new FileImageOutputStream(myActualFile);
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
        } catch (IOException e) {
            throw new FacesException("Error in writing captured image.", e);
        }
    }

}
