package dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import entities.Session;

/**
 * Database Access Object to provide database functionalities for {@link entities.Session} entity
 * <p>
 * This class extends the abstract definitions of {@link dataAccess.GenericDao} class.
 * </p>
 */
public class SessionDao extends GenericDao<Session>{
    
    /**
     * Constructor for initializing with the table name.
     */
    public SessionDao(){
        this.tableName = "sessions";
    }

    @Override
    public Session mapToObj(ResultSet resultSet) throws SQLException {
        Session session = new Session();
        session.setId(resultSet.getInt("id"));
        session.setHall(resultSet.getString("hall"));
        session.setSeats(resultSet.getLong("seats"));
        session.setMovie(resultSet.getString("movie"));
        session.setSessionDate(resultSet.getDate("sessionDate").toLocalDate());
        session.setSessionTime(resultSet.getTime("sessionTime").toLocalTime());
        return session;
    }

}
