/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.service.servlets;

import it.unive.taw.sun.lingyong.socialhub.models.DBHelper;
import it.unive.taw.sun.lingyong.socialhub.service.Utente;
import it.unive.taw.sun.lingyong.socialhub.service.interfacce.HistoryBean;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author ciao
 */
public class History extends AbsSocialHubServlet {

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
        HttpSession session = request.getSession();
        
        try (PrintWriter out = response.getWriter()) {
            Utente utente = (Utente) session.getAttribute(UTENTE_KEY);
            if(utente == null){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                String resData = String.format(
                        "{\"errors\": true, \"message\": \"%s\"}", 
                        "Non sei loggato"
                );
                out.println(resData);
            } else {
                DBHelper db = new DBHelper();
                HistoryBean history = db.getHistory(utente);
                ObjectMapper mapper = new ObjectMapper();
                String resData = mapper.writeValueAsString(history);
                out.println(resData);
            }
            
        } catch(Exception e){
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
