package myMovieFinder;
import myMovieFinder.Models.Movie;
import myMovieFinder.Models.Review;

import javax.swing.*;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.Random;
import java.util.Date;
import java.util.List;

public class Query {
    public static int createUser(String email, String password) {
        Random rand = new Random();
        int userId = rand.nextInt(999999999);

        String query = "Insert into users (uid, email, password) values ("  
                + userId + ", "
                + "'" + email + "'" + ", "
                + "'" + password + "'"
                + ");";

        Connect.getConnection();
        int rowCount = Connect.update(query);

        if (rowCount != 1) {
            System.err.println("Error occurred while creating user.");
            return -1;
        }

        return userId;
    }


    public static void rateMovie(int userId, int movieId, int rating) {
        Date date = new Date();
    /*MN+20 - ratedby and user_ratemovies are the same? (ratedby is n the origial dataset. 
        String query = "INSERT INTO RatedBy (userId, rtID, date, rating) VALUES ("
                + userId + ", "
                + movieId + ", "
                + "'" + date.toString() + "'" + ", "
                + rating
                + ");";
     */  
        @SuppressWarnings("deprecation")
		String query = "INSERT INTO user_ratedmovies (userId, movieID, rating, date_day, date_month, date_year, date_hour, date_minute, date_second) VALUES ("
                + userId + ", "
                + movieId + ", "
                + rating + ", "
                + "'" + date.getDay() + "'" + ", "
                + "'" + date.getMonth() + "'" + ", "
                + "'" + date.getYear() + "'" + ", "
                + "'" + date.getHours() + "'" + ", "
                + "'" + date.getMinutes() + "'" + ", "
                + "'" + date.getSeconds() + "'"
                + ");";
        
        System.out.println(query);
        Connect.getConnection();
        int rowCount = Connect.update(query);

        if (rowCount != 1) {
            System.err.println("Error occurred while creating rating.");
        }
    }

    public static String[] getSuggestedGenres() {
        String[] genres = new String[20];
        
        //MN++ pull genres from table. 
        String qry = "SELECT DISTINCT genre FROM movie_genres ORDER BY genre;";
    	Connection connection = Connect.getConnection();
    	try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(qry);
            int cnt=0;
            while(resultSet.next()) {
            	genres[cnt] = resultSet.getString(1);
            	cnt++;
            }
            
        } catch(Exception e1) {
            //handle bad data
            JOptionPane.showMessageDialog(null, e1);
        }
        
        return genres;
    }

    public static void likeGenre(int userId, String genreName) {
        System.out.println(userId + " likes " + genreName);

        String qry = "Insert Into likedGenre (userID, genre) values (" +
                userId + ", '" + genreName + "')";
        try {
            Connect.update(qry);
        } catch(Exception e1) {
            //handle bad data
            JOptionPane.showMessageDialog(null, e1);
        }
    }

    public static Movie getSuggestedMovie(int userId) {
        //Get random movie based on one of the user's selected genres
    	int movieId = 0;  //need to prevent the error here. 
    	
    	String genreQry = "SELECT * FROM movie_genres WHERE genre IN (SELECT genre FROM likedgenre WHERE userID = " + userId + " ORDER BY RAND()) ORDER BY RAND() LIMIT 1;";
    	Connection connection = Connect.getConnection();
    	try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(genreQry);
            resultSet.next();
            movieId = resultSet.getInt(1);
            
        } catch(Exception e1) {
            //handle bad data
            JOptionPane.showMessageDialog(null, e1);
        }

        Movie movie = getMovieById(movieId);

        return movie;
    }

    
    public static Movie getMovieById(int movieId) {
        String qry = "Select title, rtPictureURL from movies where movieId = " + movieId;
        Movie movie = new Movie(movieId);

        Connection connection = Connect.getConnection();  //will short circuit in getConnection if we already have it. 

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(qry);

            while (resultSet.next()) {
                movie.setTitle(resultSet.getObject(1).toString());
                String url = resultSet.getObject(2).toString();
                movie.setImgUrl(url);
            }
        } catch(Exception e1) {
            //handle bad data
            JOptionPane.showMessageDialog(null, e1);
        }

        return movie;
    }

    public static List<Review> getReviewsByMovieId(int movieId) {
        String qry = "Select title, rtPictureURL from movies where movieId = " + movieId;

        return null;
    }
    
    /*
     * For movie recommendations, find the user's top three movies, then select movies with thoe genres
     */
    public static Movie getRecommendedMovie(int userId) {
        //Get random movie based on one of the user's selected genres
    	int movieId = 0;  //need to prevent the error here. 
    	
    	String genreQry = "SELECT * FROM movie_genres WHERE genre IN (SELECT genre FROM likedgenre WHERE userID = " + userId + " ORDER BY RAND()) ORDER BY RAND() LIMIT 1;";
    	Connection connection = Connect.getConnection();
    	try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(genreQry);
            resultSet.next();
            movieId = resultSet.getInt(1);
            
        } catch(Exception e1) {
            //handle bad data
            JOptionPane.showMessageDialog(null, e1);
        }

        Movie movie = getMovieById(movieId);

        return movie;
    }

}
