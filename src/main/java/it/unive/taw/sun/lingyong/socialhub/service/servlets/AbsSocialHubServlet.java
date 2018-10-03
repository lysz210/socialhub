/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.service.servlets;

import static it.unive.taw.sun.lingyong.socialhub.service.servlets.ServletConstantInterface.ACTIONS_DIR;
import static it.unive.taw.sun.lingyong.socialhub.service.servlets.ServletConstantInterface.CONFIGS_FILE_ALL;
import static it.unive.taw.sun.lingyong.socialhub.service.servlets.ServletConstantInterface.CONFIGS_KEY;
import static it.unive.taw.sun.lingyong.socialhub.service.servlets.ServletConstantInterface.SERVICE_CLASS_KEY;
import static it.unive.taw.sun.lingyong.socialhub.service.servlets.ServletConstantInterface.SERVICE_NAME_KEY;
import it.unive.taw.sun.lingyong.socialhub.service.socialnetworks.ServiceProvider;
import it.unive.taw.sun.lingyong.socialhub.service.socialnetworks.SocialService;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author ciao
 */
public abstract class AbsSocialHubServlet extends HttpServlet implements ServletConstantInterface {
    protected ServletContext application;
    protected Properties appProperties;
    
    @Override
    public void init()throws ServletException {
        this.application = getServletContext();
        
        appProperties = Utils.getProperties(application, CONFIGS_FILE_ALL);
        application.setAttribute(CONFIGS_KEY, appProperties);
    }
    
    
    protected ServiceProvider retrieveServices() throws IOException, ClassNotFoundException {
        Set<String> services_files = application.getResourcePaths(ACTIONS_DIR);
        ServiceProvider services = new ServiceProvider();
        for (String file : services_files) {
            Properties p = Utils.getProperties(application, file);
            String name = p.getProperty(SERVICE_NAME_KEY);
            String c = p.getProperty(SERVICE_CLASS_KEY);
            Class cls = Class.forName(c);
            SocialService ss = new SocialService(p, cls);
            services.put(name, ss);
        }
        
        return services;
    }
    
    protected String jsonStringify(Object obj) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
    
    protected String getAppProperty(String key){
        return appProperties.getProperty(key);
    }
    
    protected String getAppProperty(String key, String def){
        return appProperties.getProperty(key, def);
    }
}
