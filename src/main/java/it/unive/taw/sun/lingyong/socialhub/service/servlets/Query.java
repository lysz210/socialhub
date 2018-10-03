/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.service.servlets;

import static it.unive.taw.sun.lingyong.socialhub.service.servlets.ServletConstantInterface.QUERY_STRING_SERVICES_KEY;
import static it.unive.taw.sun.lingyong.socialhub.service.servlets.ServletConstantInterface.QUERY_STRING_TAGS_KEY;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author ciao
 */
public class Query {
    Set<String> tags;
    Set<String> services;
    
    public Set<String> getTags(){
        return this.tags;
    }
    public void setTags(Set<String> tags){
        this.tags = tags;
    }
    
    public Set<String> getServices() {
        return this.services;
    }
    public void setServices(Set<String> services){
        this.services = services;
    }
    
    public static Query fromString(String q) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        Query json = new Query();
        if(q == null){
            return json;
        }
        JsonNode root = mapper.readValue(q, JsonNode.class);
        Set<String> tags = new HashSet<>();
        JsonNode array = root.get(QUERY_STRING_TAGS_KEY);
        for(int i = 0, len = array.size(); i < len; i+=1){
            tags.add(array.get(i).asText());
        }
        if(tags.size() > 0){
            json.setTags(tags);
        }
        Set<String> services = new HashSet<>();
        array = root.get(QUERY_STRING_SERVICES_KEY);
        for(int i = 0, len = array.size(); i < len; i+=1){
            services.add(array.get(i).asText());
        }
        if(tags.size() > 0){
            json.setServices(services);
        }
        
        return json;
    }
    
    public boolean equals(Query query){
        if(query == null) {
            return false;
        }
        if(query.tags.size() != tags.size() || query.services.size() != services.size()) {
            return false;
        }
        boolean tequal = false;
        
        if(tags == null){
            tequal = query.tags == null;
        } else {
            tequal = tags.equals(query.tags);
        }
        
        boolean sequal = false;
        
        if(services == null){
            sequal = query.services == null;
        } else {
            sequal = services.equals(query.services);
        }
        
        return sequal && tequal;
    }
}
