/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sulunemre.xmlParsers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

/**
 *
 * @author Emre
 */
public class GoogleNewsParser
{
    private final static String XML_LINK = "https://news.google.com/news/rss/?hl=tr&gl=TR&ned=tr_tr";
   
    public static ArrayList<News> getPopularNews(int n) throws MalformedURLException, IOException
    {
        ArrayList<News> popularNews = new ArrayList<>();
        
        Document doc = Jsoup.parse(new URL(XML_LINK).openStream(), "UTF-8", "", Parser.xmlParser());
        Elements newsItems = doc.getElementsByTag("item");
        for(int i=0; i<n; i++)
        {
            String title = newsItems.get(i).getElementsByTag("title").text();
            String linkEncoded = newsItems.get(i).getElementsByTag("link").text();
            String link = URLDecoder.decode(linkEncoded, "UTF-8");
            
            News newNews = new News(title, link); //LOL
            //TODO: date kismi eklenecek 
            popularNews.add(newNews);
        }
        
        return popularNews;
    } 
}
