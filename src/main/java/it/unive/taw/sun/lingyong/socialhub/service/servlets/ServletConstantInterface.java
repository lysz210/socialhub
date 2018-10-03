/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.service.servlets;
import it.unive.taw.sun.lingyong.socialhub.service.interfacce.TwitterConstantInterface;

/**
 *
 * @author ciao
 * 
 * Costanti principali da usare insieme alle servlets
 * la creazione di un'interfaccia per le costanti permette di gestire piu' facilmente
 * 
 */
public interface ServletConstantInterface 
        extends ProtocolConstantInterface,
        TwitterConstantInterface {
    
    final static String CONFIGS_KEY = "it.unive.taw.sun.lingyong.socialhub.service.servlets.configs";
    
    final static String LOGIN_HELPER_KEY = "it.unive.taw.sun.lingyong.socialhub.service.servletslogin.helper";
    
    final static String UTENTE_KEY = "it.unive.taw.sun.lingyong.socialhub.service.servlets.utente";
    final static String GLOBAL_UTENTE_KEY_SUFFIX = "it.unive.taw.sun.lingyong.socialhub.service.servlets.utente.suffix";
    
    final static String OAUTH_TOKEN_KEY = "oauth_token";
    final static String OAUTH_VERIFIER_KEY = "oauth_verifier";
    
    final static String SN_LIST_KEY = "it.unive.taw.sun.lingyong.socialhub.service.servlets.sn_list_key";
    
    final static String LAST_Q_KEY = "it.unive.taw.sun.lingyong.socialhub.service.servlets.last_q_key";
    
    final static String QUERY_STRING_TAGS_KEY = "tags";
    final static String QUERY_STRING_SERVICES_KEY = "services";
    final static String QUERY_STRING_Q_PARAM_KEY = "q";
    
    final static String[] DEFAULT_Q_VALUES = {"news"};
    
    final static String SERVICE_CLASS_KEY = "service.class";
    final static String SERVICE_NAME_KEY = "service.name";
    
    final static String SERVICE_PROVIDER_KEY = "it.unive.taw.sun.lingyong.socialhub.service.servlets.service_provider_key";
    
    final static String WEB_INFO_DIR = "/WEB-INF/";
    
    
    final static String ACTIONS_DIR = WEB_INFO_DIR + "sn_services/";
    
    final static String TWITTER_CONFIGS_FILE = ACTIONS_DIR + "twitter-params.xml";
    
    final static String CONFIGS_FILE_ACTIONS = WEB_INFO_DIR + "actions.xml";

    /**
     * array contenente tutti i path dei config files
     */
    final static String[] CONFIGS_FILE_ALL = {
        CONFIGS_FILE_ACTIONS,
        TWITTER_CONFIGS_FILE
    };
    
    
    
}
