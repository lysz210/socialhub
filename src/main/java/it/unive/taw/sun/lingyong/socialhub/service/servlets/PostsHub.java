/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.service.servlets;

import it.unive.taw.sun.lingyong.socialhub.models.DBHelper;
import it.unive.taw.sun.lingyong.socialhub.service.Risultati;
import it.unive.taw.sun.lingyong.socialhub.service.Utente;
import it.unive.taw.sun.lingyong.socialhub.service.interfacce.PostBean;
import it.unive.taw.sun.lingyong.socialhub.service.interfacce.RisultatiBean;
import it.unive.taw.sun.lingyong.socialhub.service.interfacce.SocialNetwork;
import it.unive.taw.sun.lingyong.socialhub.service.interfacce.Tags;
import it.unive.taw.sun.lingyong.socialhub.service.socialnetworks.ServiceProvider;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ciao
 */
public class PostsHub extends AbsSocialHubServlet {
    
    protected ServiceProvider serviceProvider;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(MIME_JSON);
        String q = request.getParameter(QUERY_STRING_Q_PARAM_KEY);
        RisultatiBean res = new Risultati();
        HttpSession session = request.getSession(true);
        int sum = 0;
        
        Query query = Query.fromString(q);
        if(query.services == null || query.services.isEmpty()){
            query.setServices(serviceProvider.keySet());
        }
        if(query.tags == null || query.tags.isEmpty()){
            query.tags.addAll(Arrays.asList(DEFAULT_Q_VALUES));
        }
        try{
            historyLog(session, query);
        } catch (Exception e){
            throw new ServletException(e);
        }
        
        Tags tags = new Tags();
        tags.addAll(query.tags);
        
        
        Map<String, SocialNetwork> socialNetworks = (Map<String, SocialNetwork>) session.getAttribute(SN_LIST_KEY);
        // SocialNetwork socialNetworks = null;
        if (socialNetworks == null) {
            socialNetworks = new HashMap<>();
            session.setAttribute(SN_LIST_KEY, socialNetworks);
        }
        
        for(String service: query.services){
            SocialNetwork sn = socialNetworks.get(service);
            if(sn == null){
                try {
                    sn = serviceProvider.get(service).getConnectedService();
                } catch (InstantiationException ex) {
                    Logger.getLogger(PostsHub.class.getName()).log(Level.SEVERE, null, ex);
                    throw new ServletException(ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(PostsHub.class.getName()).log(Level.SEVERE, null, ex);
                    throw new ServletException(ex);
                }
                socialNetworks.put(service, sn);
            }
            sn.search(tags);
            Iterator<RisultatiBean> iter = sn.iterator();
            if (iter.hasNext()) {
                RisultatiBean _res = iter.next();
                sum += _res.getWindow();
                res.setWindow(sum);
                for (PostBean p : _res.getPosts()) {
                    res.addPost(p);
                }
            }
        }
        String json = jsonStringify(res);

        try (PrintWriter out = response.getWriter()) {
            out.println(json);
        }
    }
    
    @Override
    public void init()throws ServletException {
        super.init();
        try {
            serviceProvider = retrieveServices();
            application.setAttribute(SERVICE_PROVIDER_KEY, serviceProvider);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PostsHub.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        } catch (IOException ex) {
            Logger.getLogger(AbsSocialHubServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }
    }
    
    private void historyLog(HttpSession session, Query query) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        
        Query lastQuery = (Query)session.getAttribute(LAST_Q_KEY);
        
        if(!query.equals(lastQuery)){
            // query diversa dalla precedente, inserire in db
            Utente utente = (Utente) session.getAttribute(UTENTE_KEY);
            if(utente != null){
                DBHelper db = new DBHelper();
                db.registerHistoryEntry(query, utente);
            }
            session.setAttribute(LAST_Q_KEY, query);
        }
    }


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
