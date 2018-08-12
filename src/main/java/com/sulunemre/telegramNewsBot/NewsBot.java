/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sulunemre.telegramNewsBot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.sulunemre.xmlParsers.GoogleNewsParser;
import com.sulunemre.xmlParsers.MilliyetLatestNewsParser;
import com.sulunemre.xmlParsers.News;
import java.io.IOException;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author Emre
 */
public class NewsBot extends TelegramLongPollingBot
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // Initialize Api Context
        ApiContextInitializer.init();

        // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();

        // Register our bot
        try
        {
            botsApi.registerBot(new NewsBot());
        } catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotToken()
    {
        Scanner fileIn = null;
        try
        {
            //Get apiKey from a non-public file
            fileIn = new Scanner(new File("../apiKey.txt"));

        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(NewsBot.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileIn.next();
    }

    @Override
    public void onUpdateReceived(Update update)
    {
        if (update.hasMessage())
        {
            Message message = update.getMessage();
            if(message.hasText())
            {
                String messageText = message.getText();
                String messageTextLowerText = messageText.toLowerCase();
                
                if(messageText.startsWith("/sondakika"))
                {
                    try
                    {
                        ArrayList<News> recentNews = MilliyetLatestNewsParser.getRecentNNews(10);
                        
                        String sendText = "";
                        for(News rn:recentNews)
                            sendText += rn.toString() + "\n";
                        
                        SendMessage sm = new SendMessage()
                                .setChatId(update.getMessage().getChatId())
                                .setText(sendText);
                        
                        execute(sm);
                    
                    } catch (TelegramApiException ex)
                    {
                        Logger.getLogger(NewsBot.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(NewsBot.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                else if(messageText.startsWith("/gundem"))
                {
                     try
                    {
                        ArrayList<News> popularNews = GoogleNewsParser.getPopularNews(10);
                        
                        String sendText = "";
                        for(News pn:popularNews)
                            sendText += pn.toString() + "\n";
                        
                        SendMessage sm = new SendMessage()
                                .setChatId(update.getMessage().getChatId())
                                .setText(sendText);
                        
                        execute(sm);
                    
                    } catch (TelegramApiException ex)
                    {
                        Logger.getLogger(NewsBot.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(NewsBot.class.getName()).log(Level.SEVERE, null, ex);
                    }                   
                }
            }
        }
    }

    @Override
    public String getBotUsername()
    {
        return "sonDakikaBot";
    }

}
