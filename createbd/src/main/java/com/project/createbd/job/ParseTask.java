package com.project.createbd.job;

//import com.project.createbd.service.CryptocurrencyService;

//import com.project.createbd.model.Сryptocurrency;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

//STEP 1. Import required packages
import java.sql.*;

@Component
public class ParseTask {

    static final String url1 = "jdbc:postgresql://localhost:5433/postgres";
    static final String username="root";
    static final String password="postgres";

    @Scheduled(fixedDelay = 10000)
    public void CryptocurrencyUpdate(){

        String url = "https://www.vbr.ru/crypto/";
        try {
            Document doc = (Document) Jsoup.connect(url).get();
             //Находим все элементы с id "line"
                Elements lines = doc.select("#price-usd");
                Elements lines1 = doc.select("#price-eur");
                Elements lines2 = doc.select("#price-rub");

                int id = 0;
                double[] usdarr = new double[41];
                double[] arreur = new double[41];
                double[] arrrub = new double[41];
                //Обходим каждый найденный элемент
                for  (Element line: lines) {
                    String USDprice = line.ownText().replace(',', '.').replaceAll(" ", "");
                    usdarr[id++] = Double.valueOf(USDprice);
                }
                id = 0;
                //Обходим каждый найденный элемент
                for  (Element line: lines1) {
                    String EURprice = line.ownText().replace(',', '.').replaceAll(" ", "");
                    arreur[id++] = Double.valueOf(EURprice);
                }
                id = 0;
                //Обходим каждый найденный элемент
                for  (Element line: lines2) {
                    String RUBprice = line.ownText().replace(',', '.').replaceAll(" ", "");
                    arrrub[id++] = Double.valueOf(RUBprice);
                }
                for (int i = 0; i < 2; i++){
                    System.out.println(usdarr[i] + " " + arreur[i] + " " + arrrub[i]);
                }
            Connection c = null;
            Statement stmt = null;
            try {
                Class.forName("org.postgresql.Driver");
                c = DriverManager
                        .getConnection(url1,
                                username, password);

                c.setAutoCommit(false);
            for (int i = 0; i < 41; i++) {
                int a = i+1;
                stmt = c.createStatement();
                String sql1 = "UPDATE cryptocurrencypricetable set usdprice = "+ usdarr[i] +" where ID="+a+";";
                String sql2 = "UPDATE cryptocurrencypricetable set eurprice = "+ arreur[i] +" where ID="+a+";";
                String sql3 = "UPDATE cryptocurrencypricetable set rubprice = "+ arrrub[i] +" where ID="+a+";";
                stmt.executeUpdate(sql1);
                stmt.executeUpdate(sql2);
                stmt.executeUpdate(sql3);
            }
                c.commit();
                stmt.close();
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName()+": "+e.getMessage());
                System.exit(0);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
