/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sondakikabot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

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
            fileIn = new Scanner(new File("..\\apiKey.txt"));

        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(SonDakikaBot.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileIn.next();
    }

    @Override
    public void onUpdateReceived(Update update)
    {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText())
        {
            // Set variables
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            SendMessage message = new SendMessage() // Create a message object object
                    .setChatId(chat_id)
                    .setText(message_text);
            try
            {
                execute(message); // Sending our message object to user
            } catch (TelegramApiException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername()
    {
        return "sonDakikaBot";
    }

}
