/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.service.socialnetworks;

import it.unive.taw.sun.lingyong.socialhub.service.interfacce.Tags;
import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photos.SearchParameters;
import it.unive.taw.sun.lingyong.socialhub.service.Post;
import it.unive.taw.sun.lingyong.socialhub.service.Risultati;
import it.unive.taw.sun.lingyong.socialhub.service.interfacce.FlickrConstantInterface;
import it.unive.taw.sun.lingyong.socialhub.service.interfacce.RisultatiBean;
import it.unive.taw.sun.lingyong.socialhub.service.servlets.ServletConstantInterface;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ciao
 */
public class FlickrConn extends AbsSocialNetwork implements Iterator<RisultatiBean>, FlickrConstantInterface{

    private Properties _properties;
    private Flickr _flickr;
    private PhotosInterface _photoInterface;
    private SearchParameters _params = new SearchParameters();
    private int _next = 1;
    @Override
    public boolean connect(Properties pts) {
        this._properties = pts;
        setName(pts.getProperty(ServletConstantInterface.SERVICE_NAME_KEY));
        String flickrKey = pts.getProperty(FLICKR_CONSUMERKEY);
        String flickrSecret = pts.getProperty(FLICKR_CONSUMERSECRET);
        this._flickr = new Flickr(flickrKey, flickrSecret, new REST());
        this._photoInterface = this._flickr.getPhotosInterface();
        return true;
    }
    
    @Override
    public void search(Tags tags){
        if(tags == null || tags.equals(getTags())){
            return;
        }
        super.search(tags);
        this._next = 1;
        this._params.setTags(tags.toArray(new String[1]));
        try {
            executeQ();
        } catch (FlickrException ex) {
            Logger.getLogger(FlickrConn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private PhotoList<Photo> _photoList;
    private void executeQ() throws FlickrException{
        this._photoList = this._photoInterface.search(_params, getWindowSize(), _next);
        
        this._next += 1;
    }
    
    @Override
    public boolean hasNext() {
        return this._photoList != null;
    }

    @Override
    public RisultatiBean next() {
        RisultatiBean res = new Risultati();
        for(Photo p: this._photoList){
            try {
                String m = p.getMedium640Url();
                String u = p.getUrl();
                Post post = new Post(
                        p.getTitle(),
                        p.getDescription(),
                        new URI(m),
                        new URI(u),
                        getName()
                );
                res.addPost(post);
            } catch (URISyntaxException ex) {
                Logger.getLogger(FlickrConn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        res.setWindow(getWindowSize());
        try {
            executeQ();
        } catch (FlickrException ex) {
            Logger.getLogger(FlickrConn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    
    
}
