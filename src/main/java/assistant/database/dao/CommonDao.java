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
import com.j256.ormlite.support.ConnectionSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static assistant.Utils.Utils.getResourceBundle;

public class CommonDao {
    /*
    This file contains universal generics for each type of data stored in the database.
    Any of them can be used to perform basic operations (create, update, delete) on any of the tables.
     */

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonDao.class);
    protected final ConnectionSource connection;

    public CommonDao() {
        this.connection = DatabaseHandler.getConnectionSource();
    }

    private void closeConnection() throws ApplicationException {
        // method using to close current connection with database
        try {
            connection.close();
        } catch (IOException e) {
            LOGGER.warn(e.getCause().getMessage());
            throw new ApplicationException(getResourceBundle().getString("exception.getDao"));
        }
    }

    // T - type, I- integer
    public <T extends BaseModel, I> Dao<T, I> getDao(Class<T> cls) throws ApplicationException {
        try {
            return DaoManager.createDao(connection, cls);
        } catch (SQLException e) {
            LOGGER.warn(e.getCause().getMessage());
            throw new ApplicationException(getResourceBundle().getString("exception.getDao"));
        } finally {
            closeConnection();
        }
    }

    public <T extends BaseModel, I> void createOrUpdate(BaseModel baseModel) throws ApplicationException {
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

    public <T extends BaseModel, I> void deleteById(Class<T> cls, Integer id) throws ApplicationException {
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

    public <T extends BaseModel, I> T findById(Class<T> cls, Integer id) throws ApplicationException {
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

    public <T extends BaseModel, I> void refresh(BaseModel baseModel) throws ApplicationException {
        try {
            Dao<T, I> dao = getDao((Class<T>) baseModel.getClass());
            dao.refresh((T) baseModel);
        } catch (SQLException e) {
            LOGGER.warn(e.getCause().getMessage());
            throw new ApplicationException(getResourceBundle().getString("exception.refresh"));
        } finally {
            closeConnection();
        }
    }

    public <T extends BaseModel> void deleteByColumnName(Class<T> cls, String columnName, int id) throws ApplicationException, SQLException {
        Dao<T, Object> dao = getDao(cls);
        DeleteBuilder<T, Object> deleteBuilder = dao.deleteBuilder();
        deleteBuilder.where().eq(columnName, id);
        dao.delete(deleteBuilder.prepare());
    }

    public int isLogin(String username, String password) throws ApplicationException {
        Dao<User, Object> dao = getDao(User.class);
        try {
            GenericRawResults<User> rawResults = dao.queryRaw("SELECT * FROM USERS where username = '" + username + "' and password = '" + password + "'", dao.getRawRowMapper());
            for (User user : rawResults) {
                return user.getId();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
