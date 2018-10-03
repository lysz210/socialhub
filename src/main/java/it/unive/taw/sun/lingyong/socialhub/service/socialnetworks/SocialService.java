/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.service.socialnetworks;

import it.unive.taw.sun.lingyong.socialhub.service.interfacce.SocialNetwork;
import java.util.Properties;

/**
 *
 * @author ciao
 */
public class SocialService {
    Class _class;
    Properties _properties;
    
    public SocialService(Properties pts, Class c){
        this._class = c;
        this._properties = pts;
    }
    
    public Properties getProperties(){
        return this._properties;
    }
    public Class getServiceClass(){
        return this._class;
    }
    public SocialNetwork getServiceInstance() throws InstantiationException, IllegalAccessException{
        return (SocialNetwork) this._class.newInstance();
    }
    public SocialNetwork getConnectedService() throws InstantiationException, IllegalAccessException {
        SocialNetwork sn = this.getServiceInstance();
        sn.connect(this._properties);
        return sn;
    }
}
