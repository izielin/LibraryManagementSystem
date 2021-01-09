package assistant.database.dao;

import assistant.Utils.exceptions.ApplicationException;
import assistant.database.DatabaseHandler;
import assistant.database.models.BaseModel;
import assistant.database.models.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static assistant.Utils.ProjectTools.getResourceBundle;

public class DataAccessObject {
    /*
    This file contains universal generics for each type of data stored in the database.
    Any of them can be used to perform basic operations (create, update, delete) on any of the tables.
     */

    private static final Logger LOGGER = LoggerFactory.getLogger(DataAccessObject.class);
    protected final ConnectionSource connection;

    public DataAccessObject() {
        this.connection = DatabaseHandler.getConnectionSource();
    }

    private void closeConnection() throws ApplicationException {
        // method used to closing current connection with database
        try {
            connection.close();
        } catch (IOException e) {
            LOGGER.warn(e.getCause().getMessage());
            throw new ApplicationException(getResourceBundle().getString("exception.getDao"));
        }
    }

    // T - type, I- integer
    public <T extends BaseModel, I> Dao<T, I> getDao(Class<T> cls) throws ApplicationException {
        // method used to getting Dao object
        try {
            return DaoManager.createDao(connection, cls);
        } catch (SQLException e) {
            LOGGER.warn(e.getCause().getMessage());
            throw new ApplicationException(getResourceBundle().getString("exception.getDao"));
        } finally {
            closeConnection();
        }
    }

    /*
     Creation Methods
     */
    public <T extends BaseModel, I> void createOrUpdate(BaseModel baseModel) throws ApplicationException {
        // method used to creating new record or update existing one
        Dao<T, I> dao = getDao((Class<T>) baseModel.getClass());
        try {
            dao.createOrUpdate((T) baseModel);
        } catch (SQLException e) {
            LOGGER.warn(e.getCause().getMessage());
            throw new ApplicationException(getResourceBundle().getString("exception.createUpdate"));
        } finally {
            closeConnection();
        }
    }

    /*
    Deletion Methods
     */

    public <T extends BaseModel, I> void deleteById(Class<T> cls, Integer id) throws ApplicationException {
        // method for deleting a record with the given id
        try {
            Dao<T, I> dao = getDao(cls);
            dao.deleteById((I) id);
        } catch (SQLException e) {
            LOGGER.warn(e.getCause().getMessage());
            throw new ApplicationException(getResourceBundle().getString("exception.delete"));
        } finally {
            closeConnection();
        }
    }

    public <T extends BaseModel> void deleteByColumnName(Class<T> cls, String columnName, int id) throws ApplicationException, SQLException {
        // method for deleting a record when the object associated with the foreign key is deleted
        Dao<T, Object> dao = getDao(cls);
        DeleteBuilder<T, Object> deleteBuilder = dao.deleteBuilder();
        deleteBuilder.where().eq(columnName, id);
        dao.delete(deleteBuilder.prepare());
    }


    /*
    Selection Methods
     */
    public <T extends BaseModel, I> T findById(Class<T> cls, Integer id) throws ApplicationException {
        // method for selecting a record with the given id
        try {
            Dao<T, I> dao = getDao(cls);
            return dao.queryForId((I) id);
        } catch (SQLException e) {
            LOGGER.warn(e.getCause().getMessage());
            throw new ApplicationException(getResourceBundle().getString("exception.notFound"));
        } finally {
            closeConnection();
        }
    }

    public <T extends BaseModel, I> List<T> queryForAll(Class<T> cls) throws ApplicationException {
        // method for selecting all records in table
        try {
            Dao<T, I> dao = getDao(cls);
            return dao.queryForAll();
        } catch (SQLException e) {
            LOGGER.warn(e.getCause().getMessage());
            throw new ApplicationException(getResourceBundle().getString("exception.notFoundAll"));
        } finally {
            closeConnection();
        }
    }

    public <T extends BaseModel> List<T> findByColumnName(Class<T> cls, String columnName, int id) throws ApplicationException, SQLException {
        // method for selecting a record associated with the given foreign key value
        Dao<T, Object> dao = getDao(cls);
        QueryBuilder<T, Object> queryBuilder = dao.queryBuilder();
        queryBuilder.where().eq(columnName, id);
        return dao.query(queryBuilder.prepare());
    }

    /*
     Other Methods
     */

    public User isLogin(String username, String password) throws ApplicationException {
        // method used to validate the entered credentials
        Dao<User, Object> dao = getDao(User.class);
        QueryBuilder<User, Object> queryBuilder = dao.queryBuilder();
        try {
            queryBuilder.setWhere(queryBuilder.where().eq("USERNAME", username).and().eq("PASSWORD", password));
            List<User> userList = queryBuilder.query();
            return userList.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T extends BaseModel> String countRecords(Class<T> cls, String query) throws ApplicationException, SQLException {
        // method used to count the records that match the query provided by the user
        Dao<T, Object> dao = getDao(cls);
        GenericRawResults<String[]> rawResults = dao.queryRaw(query);
        for (String[] s : rawResults) {
            return s[0];
        }
        return null;
    }

    public <T extends BaseModel> List<String[]> executeRawQuery(Class<T> cls, String query) throws ApplicationException, SQLException {
        Dao<T, Object> dao = getDao(cls);
        GenericRawResults<String[]> rawResults = dao.queryRaw(query);
        return rawResults.getResults();
    }

}
