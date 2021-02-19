package assistant.Utils;

import assistant.database.dao.DataAccessObject;
import assistant.database.models.Book;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CreateSets {
    private static List<String[]> titleList;
    private static final List<String> possibleTitleItems = new ArrayList<>();

    public static Set<String> createTitleSet(String query) {
        DataAccessObject dao = new DataAccessObject();
        try {
            titleList = dao.executeRawQuery(Book.class, query);
        } catch (ApplicationException | SQLException e) {
            e.printStackTrace();
        }
        titleList.forEach(item -> possibleTitleItems.add(item[0]));
        return new HashSet<>(possibleTitleItems);
    }
}
