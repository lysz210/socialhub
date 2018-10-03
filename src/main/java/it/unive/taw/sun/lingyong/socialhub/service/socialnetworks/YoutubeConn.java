/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.service.socialnetworks;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.samples.youtube.cmdline.Auth;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import it.unive.taw.sun.lingyong.socialhub.service.Post;
import it.unive.taw.sun.lingyong.socialhub.service.Risultati;
import it.unive.taw.sun.lingyong.socialhub.service.interfacce.RisultatiBean;
import it.unive.taw.sun.lingyong.socialhub.service.interfacce.Tags;
import it.unive.taw.sun.lingyong.socialhub.service.interfacce.YoutubeConstantInterface;
import it.unive.taw.sun.lingyong.socialhub.service.servlets.ServletConstantInterface;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ciao
 */
public class YoutubeConn 
        extends AbsSocialNetwork 
        implements YoutubeConstantInterface {
    private YouTube _youtube;
    // assicurarsi che _next valga null solo quanto terminano le finestre
    private String _next = "";
    private String _prev;
    private YouTube.Search.List _search;
    private boolean isConnected = false;
    private Properties _properties;
    
    public static final String SEARCH_URL_BASE = "https://www.youtube.com/watch?v=";
    
    private String[] _searchItemFields = {
                "id/kind",
                "id/videoId",
                "snippet/title",
                "snippet/thumbnails/default/url",
                "snippet/description"
    };
    
    private String[] _searchFields = {
                "items(" + String.join(",", _searchItemFields) + ")",
                "nextPageToken",
                "prevPageToken",
                "pageInfo",
    };

    @Override
    public boolean connect(Properties pts) {
        boolean esito = false;
        this._properties = pts;
        setName(pts.getProperty(ServletConstantInterface.SERVICE_NAME_KEY));
        try{
            _youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest request)throws IOException { }
            }).setApplicationName("social-hub-youtube").build();
            esito = true;
        } catch (Exception e){
            esito = false;
            throw e;
        }
        try {
            this.initSearch();
        } catch (IOException ex) {
            Logger.getLogger(YoutubeConn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return esito;
    }
    
    @Override
    public void search(Tags tags){
        if(tags == null || tags.equals(getTags())){
            return;
        }
        super.search(tags);
        this._next = "";
        this._prev = "";
        this._searchResultList = null;
        try {
            executeQ();
        } catch (IOException ex) {
            Logger.getLogger(YoutubeConn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private List<SearchResult> _searchResultList = null;
    private void executeQ() throws IOException{
        this._search.setQ(getSearchString());
        if(this._next != null && this._next.length() > 0){
            this._search.setPageToken(this._next);
        }
        SearchListResponse searchResponse = this._search.execute();
        this._searchResultList = searchResponse.getItems();
        this._next = searchResponse.getNextPageToken();
        this._prev = searchResponse.getPrevPageToken();
    }

    @Override
    public boolean hasNext() {
        return this._searchResultList != null;
    }

    @Override
    public RisultatiBean next() {
        RisultatiBean res = new Risultati();
        List<SearchResult> sr = this._searchResultList;
        try {
            executeQ();
        } catch (IOException ex) {
            Logger.getLogger(YoutubeConn.class.getName()).log(Level.SEVERE, null, ex);
        }
        res.setWindow(getWindowSize());
        for(SearchResult r: sr){
            try {
                Post _post = new Post(
                        r.getSnippet().getTitle(),
                        r.getSnippet().getDescription(),
                        new URI(r.getSnippet().getThumbnails().getDefault().getUrl()),
                        new URI(SEARCH_URL_BASE + r.getId().getVideoId()),
                        getName()
                );
                res.addPost(_post);
            } catch (URISyntaxException ex) {
                Logger.getLogger(YoutubeConn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return res;
    }
    
    private void initSearch() throws IOException{
       this._search = this._youtube.search().list("id,snippet");
       String apiKey = this._properties.getProperty(YOUTUBE_APIKEY);
       this._search.setKey(apiKey);
       this._search.setType("video");
       this._search.setMaxResults(new Long(super.getWindowSize()));
       this._search.setFields(String.join(",", this._searchFields));
    }
    
}
