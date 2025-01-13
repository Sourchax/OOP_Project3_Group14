package dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Movie;

/**
 * Database Access Object to provide database functionalities for {@link entities.Movie} entity
 * <p>
 * This class extends the abstract definitions of {@link dataAccess.GenericDao} class.
 * </p>
 */
public class MoviesDao extends GenericDao<Movie>{

    /**
     * Constructor for initializing with the table name.
     */
    public MoviesDao(){
        this.tableName = "movies";
    }

    @Override
    public Movie mapToObj(ResultSet resultSet) throws SQLException {
        Movie movie = new Movie();
        movie.setName(resultSet.getString("name"));
        movie.setGenre(resultSet.getString("genre"));
        movie.setSummary(resultSet.getString("summary"));
        movie.setId(resultSet.getInt("id"));
        movie.setYear(resultSet.getString("year"));
        movie.setPoster(resultSet.getBlob("poster"));
        return movie;
    }



    
    
}
