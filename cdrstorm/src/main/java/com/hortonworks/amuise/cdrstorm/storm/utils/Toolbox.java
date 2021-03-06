/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hortonworks.amuise.cdrstorm.storm.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author adammuise
 */
public class Toolbox {

    public static ArrayList<String> extractURLfromString(String input) {

        ArrayList<String> urls = new ArrayList<String>();
        // Pattern for recognizing a URL, based off RFC 3986
        Pattern urlPattern = Pattern.compile(
                "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

        Matcher matcher = urlPattern.matcher(input);
        while (matcher.find()) {
            int matchStart = matcher.start(1);
            int matchEnd = matcher.end();
            urls.add(input.substring(matchStart, matchEnd));
        }

        return urls;
    }

    public static String stripProtocolPrefix(String input) {

        try {
            URI uri = new URI(input.toLowerCase().trim());
            String domain = uri.getHost();
            return domain.startsWith("www.") ? domain.substring(4) : domain;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String fullURL(String anyURL) throws IOException {
        URL url = new URL(anyURL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
        httpURLConnection.setInstanceFollowRedirects(false);
        String finalURL = httpURLConnection.getHeaderField("Location");
        httpURLConnection.disconnect();

        return finalURL;
    }



}
