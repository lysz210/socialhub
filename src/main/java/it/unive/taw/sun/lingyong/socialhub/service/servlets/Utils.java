/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.service.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

/**
 *
 * @author ciao
 */
public class Utils {
    
    public static Properties getProperties(ServletContext ctx, String[] fnames){
        ArrayList<InputStream> files = new ArrayList<>();
        for(String name: fnames){
            InputStream is = ctx.getResourceAsStream(name);
            files.add(is);
        }
        InputStream[] tmp = new InputStream[1];
        return getProperties(files.toArray(tmp));
    }
    
    public static Properties getProperties(InputStream[] fnames){
        Properties p = new Properties();
        Utils.addProperties(p, fnames);
        return p;
    }
    public static void addProperties( Properties p, InputStream[] fnames){
        for(InputStream in: fnames){
            try {
                p.loadFromXML(in);
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static void addProperties(Properties p, InputStream fname) throws IOException{
        p.loadFromXML(fname);
        
    }
    public static Properties getProperties(ServletContext ctx, String fname) throws IOException{
        InputStream is = ctx.getResourceAsStream(fname);
        Properties p = new Properties();
        Utils.addProperties(p, is);
        return p;
    }
}
