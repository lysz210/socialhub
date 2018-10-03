/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.service.servlets;

/**
 *
 * @author ciao
 */
public interface ProtocolConstantInterface {
    final static String MIME_JSON = "application/json;charset=UTF-8";
    final static String MIME_HTML = "text/html;charset=UTF-8";
    
    final static String ACTION_LOGIN_WEB = "action.login.web";
    final static String ACTION_LOGIN_WEB_HANDLER = "action.login.web.handler";
    
    final static String ACTION_LOGIN_APP = "action.login.app";
    final static String ACTION_LOGIN_APP_HANDLER = "action.login.app.handler";
    final static String ACTION_SERVICES = "action.services";
    final static String ACTION_LOGOUT = "action.logout";
    final static String ACTION_POSTS = "action.posts";
    final static String ACTION_HISTORY = "action.history";
    final static String ACTION_UTENTE = "action.utente";
    final static String ACTION_UTENTE_APPINIT = "action.utente.app-init";
    
    final static int HISTORY_LIMIT = 100;
}
