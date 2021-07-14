import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.sql.*;
import java.util.ArrayList;

public class Main {
    private static final String DBUser = "root";
    private static final String DBPass = "postgres";
    private static final String DBUrl = "jdbc:postgresql://localhost:5433/postgres";
    public static void main(String[] args) throws TelegramApiException, SQLException {
        ArrayList<Crypto> arrayList = new ArrayList<>();
        Connection connection = DriverManager.getConnection(DBUrl, DBUser,DBPass);
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return;
        }

        System.out.println("PostgreSQL JDBC Driver successfully connected");

        String query = "SELECT * FROM PUBLIC.\"cryptocurrencypricetable\"";
        Statement statement1 = connection.createStatement();
        ResultSet result = statement1.executeQuery(query);
        while (result.next()){
            int id = result.getInt("id");
            String title = result.getString("title");
            Double usdprice = result.getDouble("usdprice");
            Crypto crypto = new Crypto(id,title,usdprice);
            arrayList.add(crypto);
        }

        DefaultBotOptions botOptions = new DefaultBotOptions();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        MyBot bot = new MyBot(botOptions, arrayList);


        try {
            telegramBotsApi.registerBot(bot);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
