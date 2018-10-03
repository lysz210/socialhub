/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.service.servlets;

import it.unive.taw.sun.lingyong.socialhub.models.DBHelper;
import it.unive.taw.sun.lingyong.socialhub.service.Utente;
import static it.unive.taw.sun.lingyong.socialhub.service.interfacce.TwitterConstantInterface.TWITTER_CONSUMERKEY;
import static it.unive.taw.sun.lingyong.socialhub.service.interfacce.TwitterConstantInterface.TWITTER_CONSUMERSECRET;
import static it.unive.taw.sun.lingyong.socialhub.service.servlets.ServletConstantInterface.UTENTE_KEY;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.RequestToken;

/**
 *
 * @author ciao
 */
public class AndyLoginHandler extends AbsSocialHubServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        
        String tokenKey = request.getParameter(OAUTH_TOKEN_KEY);
        String verifier = request.getParameter(OAUTH_VERIFIER_KEY);
        
        RequestToken token = (RequestToken) application.getAttribute(tokenKey);
        Twitter twitter = new TwitterFactory().getInstance();
        String globalUtenteKey = UTENTE_KEY + tokenKey;
        application.removeAttribute(tokenKey);
        Utente utente;
        try {
            String key = getAppProperty(TWITTER_CONSUMERKEY);
            String secret = getAppProperty(TWITTER_CONSUMERSECRET);
            
            twitter.setOAuthConsumer(key, secret);
            twitter.getOAuthAccessToken(token, verifier);
            Long id = twitter.getId();
            User utenteTwitter = twitter.showUser(id);
            URI foto = new URI(utenteTwitter.getProfileImageURL());
            utente = new Utente(
                    id.toString(),
                    utenteTwitter.getScreenName(),
                    foto
            );
            
            application.setAttribute(globalUtenteKey, utente);
            
            DBHelper db = new DBHelper();
            db.insertUtente(utente);
        } catch (Exception ex) {
            Logger.getLogger(WebLoginHandler.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Login success</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Grazie " + utente.getNome() + " per aver effettuato il login ora puo' usare l'applicazione con il tuo account twitter</h1>");
            out.println("</body>");
            out.println("</html>");
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
