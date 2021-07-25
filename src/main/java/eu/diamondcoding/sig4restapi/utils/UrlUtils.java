package eu.diamondcoding.sig4restapi.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class UrlUtils {

    private final String url;

    /**
     * Inits the UrlUtils
     * @param url the url as you would send a rest call with, so invalid stuff should be uri encoded once
     */
    public UrlUtils(String url) {
        this.url = url;
    }

    public String extractCanonicalURI() {
        String canonicalURI = url; //init canonicalURI string
        //get string until ?
        int questionMarkIndex = canonicalURI.lastIndexOf("?"); //get index of the ?
        if(questionMarkIndex != -1) { //if it has a ?
            canonicalURI = canonicalURI.substring(0, questionMarkIndex); //remove everything until the ?
        }
        //remove stuff except Canonical URI
        canonicalURI = canonicalURI.replaceFirst("://", ""); //remove "://"
        int slashIndex = canonicalURI.indexOf("/");
        if(slashIndex != -1) { //if it has a /
            canonicalURI = canonicalURI.substring(slashIndex); //remove every thing to it, but not the / self
        }
        //second uri encode path parts
        StringBuilder secondEncoded = new StringBuilder("/");
        for (String pathPart : canonicalURI.split("/")) {
            if(pathPart.isEmpty()) continue;
            secondEncoded.append(uriEncode(pathPart)).append("/");
        }
        return secondEncoded.toString();
    }

    public String extractCanonicalQueryString() {
        String queryString = url; //init queryString with url
        int questionMarkIndex = queryString.indexOf("?"); //get index of ?
        queryString = queryString.substring(questionMarkIndex + 1); //get everything from the ? without the ? self
        //order parameters
        StringBuilder orderedQueryStringBuild = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : //for loop
                getQueryParameters(queryString) //get parameters
                        .entrySet().stream() //stream them
                        .sorted(Map.Entry.comparingByKey()) //sort them
                        .collect(Collectors.toList())) { //collect them as list
            String key = entry.getKey(); //get the key
            for (String value : entry.getValue() //for loop it's values
                    .stream() //stream them
                    .sorted(String::compareTo) //sort them
                    .collect(Collectors.toList())) { //collect them as a list
                if (orderedQueryStringBuild.length() > 0) { //if it's not empty
                    orderedQueryStringBuild.append("&"); //add a &
                }
                orderedQueryStringBuild.append(key).append("=").append(value); //then add the key and value
            }
        }
        //remove last &
        String orderQueryString = orderedQueryStringBuild.toString();
        orderQueryString = orderQueryString.substring(0, orderQueryString.length()-1);
        return orderQueryString;
    }

    protected Map<String, List<String>> getQueryParameters(String queryString) {
        Map<String, List<String>> result = new HashMap<>();
        for (String param : queryString.split("&")) {
            String[] entry = param.split("=");
            if(!result.containsKey(entry[0])) {
                result.put(entry[0], new ArrayList<>());
            }
            if (entry.length > 1) {
                result.get(entry[0]).add(entry[1]);
            } else {
                result.get(entry[0]).add("");
            }
        }
        return result;
    }

    private String uriEncode(String string) {
        try {
            return URLEncoder.encode(string, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
