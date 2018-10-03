/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.service.socialnetworks;

import it.unive.taw.sun.lingyong.socialhub.service.Post;
import it.unive.taw.sun.lingyong.socialhub.service.Risultati;
import it.unive.taw.sun.lingyong.socialhub.service.interfacce.RisultatiBean;
import it.unive.taw.sun.lingyong.socialhub.service.interfacce.Tags;
import it.unive.taw.sun.lingyong.socialhub.service.interfacce.TwitterConstantInterface;
import it.unive.taw.sun.lingyong.socialhub.service.servlets.ServletConstantInterface;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.MediaEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

/**
 *
 * @author ciao
 */
public class TwitterConn extends AbsSocialNetwork implements Iterator<RisultatiBean>, TwitterConstantInterface {

    public static final String TWITTER_URL_PTN = "https://twitter.com/%s/status/%d";
    
    private Twitter _twitter;
    private Properties _properties;
    
    private Query _query;
    private List<Status> _searchResult;
    
    @Override
    public boolean connect(Properties pts) {
        boolean esito = false;
        this._properties = pts;
        setName(pts.getProperty(ServletConstantInterface.SERVICE_NAME_KEY));
        String consumerKey = pts.getProperty(TWITTER_CONSUMERKEY);
        String consumerSecret = pts.getProperty(TWITTER_CONSUMERSECRET);
        String accessToken = pts.getProperty(TWITTER_ACCESSTOKEN);
        String accessTokenSecret = pts.getProperty(TWITTER_ACCESSTOKENSECRET);
        AccessToken aToken = new AccessToken(accessToken, accessTokenSecret);
        this._twitter = new TwitterFactory().getInstance();
        this._twitter.setOAuthConsumer(consumerKey, consumerSecret);
        this._twitter.setOAuthAccessToken(aToken);
        
        return esito;
    }
    
    private QueryResult _queryResult;
    
    @Override
    public void search(Tags tags){
        if(tags == null || tags.equals(getTags())){
            return;
        }
        super.search(tags);
        this._query = new Query(getSearchString());
        this._query.count(5);
        try {
            this._queryResult = this._twitter.search(this._query);
            this._searchResult = this._queryResult.getTweets();
        } catch (TwitterException ex) {
            Logger.getLogger(TwitterConn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public boolean hasNext() {
        boolean answer = this._searchResult != null || this._searchResult.size() > 0;
        return answer;
    }

    @Override
    public RisultatiBean next() {
        Risultati res = new Risultati();
        for(Status status: this._searchResult){
            try {
                String name = status.getUser().getScreenName();
                String content = status.getText();
                String thumb = null;
                long id = status.getId();
                MediaEntity[] medias = status.getMediaEntities();
                if(medias != null && medias.length > 0){
                    MediaEntity _media = medias[0];
                    thumb = _media.getMediaURL();
                }
                String url = String.format(TWITTER_URL_PTN, name, id);
                Post post = new Post(
                        name,
                        content,
                        (thumb == null || thumb.length() < 1) ? null : new URI(thumb),
                        new URI(url),
                        getName()
                );
                res.addPost(post);
            } catch (URISyntaxException ex) {
                Logger.getLogger(TwitterConn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        res.setWindow(getWindowSize());
        this._searchResult = this._queryResult.getTweets();
        return res;
    }

    
}
