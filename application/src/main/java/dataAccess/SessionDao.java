package dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Session;

public class SessionDao extends GenericDao<Session>{
    
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
        session.setSessionDate(resultSet.getDate("sessionDate"));
        session.setSessionTime(resultSet.getTime("sessionTime"));
        return session;
    }

}
