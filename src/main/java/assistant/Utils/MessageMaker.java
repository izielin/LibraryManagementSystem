package assistant.Utils;

import assistant.database.models.Message;
import assistant.database.models.User;

import java.sql.SQLOutput;

public class MessageMaker {
    public static Message bookCreationMessage(String bookTitle, User user, String date) {
        Message outputMessage = new Message();
        outputMessage.setTitle("A new book has been added!");
        String details = String.format("User: %s (%s %s) added a new book with the title \"%s\"",
                user.getUsername(), user.getFirstName(),
                user.getLastName(), bookTitle);
        outputMessage.setDetails(details);
        outputMessage.setUserID(user.getId());
        outputMessage.setDate(date);
        System.out.println("done");
        return outputMessage;
    }

//    public static Message bookDeletionMessage(String bookTitle, User user, String date) {
//        Message outputMessage = new Message();
//        outputMessage.setTitle("A new book has been added!");
//        String details = String.format("User: %s (%s %s) added a new book with the title \"%s\"",
//                user.getUsername(), user.getFirstName(),
//                user.getLastName(), bookTitle);
//        outputMessage.setDetails(details);
//        outputMessage.setUserID(user.getId());
//        outputMessage.setDate(date);
//        System.out.println("done");
//        return outputMessage;
//    }
//
//    public static Message bookBorrowMessage(String bookTitle, User user, String date) {
//        Message outputMessage = new Message();
//        outputMessage.setTitle("A new book has been added!");
//        String details = String.format("User: %s (%s %s) added a new book with the title \"%s\"",
//                user.getUsername(), user.getFirstName(),
//                user.getLastName(), bookTitle);
//        outputMessage.setDetails(details);
//        outputMessage.setUserID(user.getId());
//        outputMessage.setDate(date);
//        System.out.println("done");
//        return outputMessage;
//    }
//
//    public static Message bookUpdateMessage(String bookTitle, User user, String date) {
//        Message outputMessage = new Message();
//        outputMessage.setTitle("A new book has been added!");
//        String details = String.format("User: %s (%s %s) added a new book with the title \"%s\"",
//                user.getUsername(), user.getFirstName(),
//                user.getLastName(), bookTitle);
//        outputMessage.setDetails(details);
//        outputMessage.setUserID(user.getId());
//        outputMessage.setDate(date);
//        System.out.println("done");
//        return outputMessage;
//    }
//
//    public static Message bookReturnMessage(String bookTitle, User user, String date) {
//        Message outputMessage = new Message();
//        outputMessage.setTitle("A new book has been added!");
//        String details = String.format("User: %s (%s %s) added a new book with the title \"%s\"",
//                user.getUsername(), user.getFirstName(),
//                user.getLastName(), bookTitle);
//        outputMessage.setDetails(details);
//        outputMessage.setUserID(user.getId());
//        outputMessage.setDate(date);
//        System.out.println("done");
//        return outputMessage;
//    }

}
