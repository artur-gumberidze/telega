import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyBot extends TelegramLongPollingBot {

    private static final String TOKEN = "1869793882:AAF7gORg89JfOM4U1gEKOKdYz44ebXqNpCI";
    private static final String USERNAME = "wrhejerj_bot";
    ArrayList<Crypto> arrayList;
    InlineKeyboardMarkup but =new InlineKeyboardMarkup();


    //ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    // Connection connection;


    public MyBot(DefaultBotOptions options, ArrayList<Crypto> arrayList) throws SQLException {
        super(options);
        this.arrayList = arrayList;
    }


    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {

        String message = update.getMessage().getText();
        sendMsg(update.getMessage().getChatId().toString(), message);
    }

    /**
     * Метод для настройки сообщения и его отправки.
     *
     * @param chatId id чата
     * @param s      Строка, которую необходимот отправить в качестве сообщения.
     */
    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        if (isContinue(s).length() > 0) {
            //sendMessage.setReplyMarkup(but);
            // sendMessage = sendInlineKeyBoardMessage(Long.parseLong(chatId), isContinue(s), sendMessage);
            sendInlineKeyBoardMessage();
            sendMessage.setText(isContinue(s));
            sendMessage.setReplyMarkup(but);



        } else if (s.equals("All")) {
            String res = "";
            for (int i = 0; i < arrayList.size(); i++) {
                res = res + arrayList.get(i).toString() + "\n";
            }
            sendMessage.setText(res);
        } else {
            sendMessage.setText("Выбери, что ты хочешь сделать:" + "\n" +
                    "1.Вывести список всех криптовалют(All)" + "\n" + "2.Вывести определенную криптовалюту(Пр: Вывести BTC Bitcoin)");
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public synchronized String isContinue(String s) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).title.equals(s)) {
                return arrayList.get(i).toString();
            }
        }
        return "";
    }

    public void sendInlineKeyBoardMessage() {
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Подписаться");
        inlineKeyboardButton1.setCallbackData("Button \"Тык\" has been pressed");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        but.setKeyboard(rowList);


    }



   /* public String getMessage(String msg){
        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardRowfirst = new KeyboardRow();
        KeyboardRow keyboardRowsecond = new KeyboardRow();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        if(msg.equals("Меню") || msg.equals("Привет")){
            keyboard.clear();
            keyboardRowfirst.add("Показать курс всех криптовалют");
            keyboardRowfirst.add("Показать подписки");
            keyboardRowsecond.add("Закрыть");
            keyboard.add(keyboardRowfirst);
            keyboard.add(keyboardRowsecond);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Выбрать...";

        }
        return "Hey";
    } */
}



/*
// sm.setReplyMarkup(replyKeyboardMarkup);
            sm.setChatId(chatId);
            if (message.equals("tra")) {

                String text = "SELECT * FROM PUBLIC.\"cryptocurrencypricetable\"";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(text);

                while (resultSet.next()) {
                    String a = resultSet.getInt("id") + resultSet.getString("title") +
                            resultSet.getDouble("usdprice") +
                            resultSet.getDouble("eurprice") + resultSet.getDouble("rubprice");

                    sm.setText(a);


                }
                resultSet.close();



            } else {
                sm.setText("false");
            }
            execute(sm);





           /* try {
                execute(sm);
            } catch (TelegramApiException e) {
                //todo add logging to the project.
                e.printStackTrace();
            }
        }
 */