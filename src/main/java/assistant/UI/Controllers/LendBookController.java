package assistant.UI.Controllers;

import assistant.FXModels.CategoryFXModel;
import assistant.Utils.converters.CategoryConverter;
import assistant.Utils.exceptions.ApplicationException;
import assistant.database.dao.CommonDao;
import assistant.database.models.Category;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LendBookController implements Initializable {
    @FXML
    private TreeView<String> treeView;

    private final ObservableList<Category> categoryList = FXCollections.observableArrayList();
    private final TreeItem<String> root = new TreeItem<>();

    @Override

    public void initialize(URL location, ResourceBundle resources) {
        CommonDao dao = new CommonDao();
        try {
            List<Category> categories = dao.queryForAll(Category.class);
            initCategoryList(categories);
            initRoot(categories);
        } catch (ApplicationException e) {
            System.out.println(e.getMessage());
        }

        treeView.setRoot(root);
    }

    private void initRoot(List<Category> categories) {
        root.getChildren().clear();
        categories.forEach(c -> {
            TreeItem<String> categoryItem = new TreeItem<>(c.getName());
            c.getBooks().forEach(b -> {
                        if (b.getLibrary().getId() == LoginController.library.getId()) {
                            categoryItem.getChildren().add(new TreeItem<>(b.getTitle()));
                        }
                    }
            );
            root.getChildren().add(categoryItem);
        });
    }

    private void initCategoryList(List<Category> categories) {
        categoryList.clear();
        categoryList.addAll(categories);
    }
}
