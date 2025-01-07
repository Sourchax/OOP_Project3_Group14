package dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Movie;

public class MoviesDao extends GenericDao<Movie>{

    public MoviesDao(){
        this.tableName = "movies";
    }

    @Override
    public Movie mapToObj(ResultSet resultSet) throws SQLException {
        Movie movie = new Movie();
        movie.setName(resultSet.getString("name"));
        movie.setGenre(resultSet.getString("genre"));
        movie.setSummary(resultSet.getString("summary"));
        movie.setID(resultSet.getInt("id"));
        movie.setReleaseYear(resultSet.getString("year"));
        movie.setPoster(resultSet.getBlob("poster"));
        return movie;
    }



    
    
}
