/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sondakikabot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import xmlParsers.MilliyetSonDakikaParser;
import xmlParsers.News;

/**
 *
 * @author Emre
 */
public class SonDakikaBot extends TelegramLongPollingBot
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
            botsApi.registerBot(new SonDakikaBot());
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
            Logger.getLogger(SonDakikaBot.class.getName()).log(Level.SEVERE, null, ex);
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
                if(message.getText().equals("/sondakika"))
                {
                    try
                    {
                        ArrayList<News> recentNews = MilliyetSonDakikaParser.getRecentNNews(10);
                        
                        String sendText = "";
                        for(News rn:recentNews)
                            sendText += rn.toString() + "\n";
                        
                        SendMessage sm = new SendMessage()
                                .setChatId(update.getMessage().getChatId())
                                .setText(sendText);
                        
                        execute(sm);
                    
                    } catch (IOException ex)
                    {
                        Logger.getLogger(SonDakikaBot.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (TelegramApiException ex)
                    {
                        Logger.getLogger(SonDakikaBot.class.getName()).log(Level.SEVERE, null, ex);
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
