/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sulunemre.xmlParsers;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Emre
 */
public class News
{
    private String title;
    private Date date; //Optional
    private String url;

    public News(String title, Date date, String url)
    {
        this.title = title;
        this.date = date;
        this.url = url;
    }

    public News(String title, String url)
    {
        this.title = title;
        this.url = url;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    @Override
    public String toString()
    {
        String result = "";
        if(date != null)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            result += sdf.format(date) + " ";
        }
        
        result += title + "\n";
        result += url + "\n";
        
        return result;
    }
}