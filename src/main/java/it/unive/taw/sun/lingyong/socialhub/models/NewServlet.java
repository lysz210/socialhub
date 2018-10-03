/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.models;

import it.unive.taw.sun.lingyong.socialhub.service.Utente;
import it.unive.taw.sun.lingyong.socialhub.service.interfacce.HistoryBean;
import it.unive.taw.sun.lingyong.socialhub.service.interfacce.HistoryEntryBean;
import it.unive.taw.sun.lingyong.socialhub.service.interfacce.RisultatiBean;
import it.unive.taw.sun.lingyong.socialhub.service.interfacce.SocialNetwork;
import it.unive.taw.sun.lingyong.socialhub.service.interfacce.Tags;
import it.unive.taw.sun.lingyong.socialhub.service.servlets.AbsSocialHubServlet;
import it.unive.taw.sun.lingyong.socialhub.service.servlets.Utils;
import it.unive.taw.sun.lingyong.socialhub.service.socialnetworks.ServiceProvider;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author ciao
 */
public class NewServlet extends AbsSocialHubServlet {

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
        DBHelper db = new DBHelper();
        try (PrintWriter out = response.getWriter()) {
            ServiceProvider serviceProvider = retrieveServices();
            Set<String> services = serviceProvider.keySet();
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewServlet at " + request.getContextPath() + "</h1>");
            out.println("<pre>");
            out.println(Servizi.getCreateTable());
            out.println(Utenti.getCreateTable());
            out.println(HistoryServices.getCreateTable());
            out.println(History.getCreateTable());
            
            Connection con = db.getDerbyCon();
            
            DBHelper.createAllSocialHubTables(con);
            
            ResultSet r = con.prepareStatement("select * from " + Utenti.TABLE_NAME).executeQuery();
            while(r.next()){
                out.println("<p>" + r.getString(Utenti.id_utente.name()));
            }
            Class yc = Class.forName("it.unive.taw.sun.lingyong.socialhub.service.socialnetworks.YoutubeConn");
            SocialNetwork y = (SocialNetwork) yc.newInstance();
            Properties pts = Utils.getProperties(getServletContext(), ACTIONS_DIR + "youtube-params.xml");
            y.connect(pts);
            Tags tags = new Tags();
            tags.add("ciao");
            y.search(tags);
            Iterator<RisultatiBean> iy = y.iterator();
            RisultatiBean res = iy.next();
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(res);
            
            out.println("<p>" + json);
            
            out.println("ciao");
            
            int count = db.insertServices(services);
            
            out.println("<p>Inseriti " + count);
            
            Utente u = new Utente();
            u.setIdUtente("96897460");
            
            HistoryBean h = db.getHistory(u);
            
            for(HistoryEntryBean _r: h.getRecords()){
                out.println(mapper.writeValueAsString(_r));
                out.println("<p>" + _r.getIdHistory() + ": parole(" + String.join(", ", _r.getParole()) + ") - servizi(" + String.join(", ", _r.getServizi()) + ")" );
            }
            
            
            
            out.println("</pre>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            throw new ServletException(e);
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
