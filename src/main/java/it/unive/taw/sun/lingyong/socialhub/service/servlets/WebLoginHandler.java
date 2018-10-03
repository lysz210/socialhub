/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.service.servlets;

import it.unive.taw.sun.lingyong.socialhub.models.DBHelper;
import it.unive.taw.sun.lingyong.socialhub.service.Utente;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.RequestToken;

/**
 *
 * @author ciao
 */
public class WebLoginHandler extends AbsSocialHubServlet {

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
        String tokenKey = request.getParameter("oauth_token");
        String verifier = request.getParameter("oauth_verifier");
        
        HttpSession session = request.getSession(true);
        RequestToken token = (RequestToken) session.getAttribute(tokenKey);
        
        Twitter twitter = new TwitterFactory().getInstance();
        
        session.removeAttribute(tokenKey);
        
        try {
            String key = getAppProperty(TWITTER_CONSUMERKEY);
            String secret = getAppProperty(TWITTER_CONSUMERSECRET);
            
            twitter.setOAuthConsumer(key, secret);
            twitter.getOAuthAccessToken(token, verifier);
            
            Long id = twitter.getId();
            User utenteTwitter = twitter.showUser(id);
            URI foto = new URI(utenteTwitter.getProfileImageURL());
            Utente utente = new Utente(
                    id.toString(),
                    utenteTwitter.getScreenName(),
                    foto
            );
            session.setAttribute(UTENTE_KEY, utente);
            
            DBHelper db = new DBHelper();
            db.insertUtente(utente);
        } catch (Exception ex) {
            Logger.getLogger(WebLoginHandler.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }
        response.sendRedirect("/");
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
