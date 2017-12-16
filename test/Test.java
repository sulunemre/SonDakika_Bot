
import java.io.IOException;
import java.util.ArrayList;
import xmlParsers.GoogleGundemParser;
import xmlParsers.News;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Emre
 */
public class Test
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        ArrayList<News> recentNews = GoogleGundemParser.getPopularNews(10);

        String sendText = "";
        for (News rn : recentNews)
        {
            sendText += rn.toString();
        }
        
        System.out.println(sendText);
    }

}
