package myMovieFinder.ViewControllers;


import myMovieFinder.Connect;
import myMovieFinder.Context;
import myMovieFinder.Views.MovieRecommendation;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;

public class MovieRecommendationController {
    private Context context;
    private MovieRecommendation view;

    public MovieRecommendationController(Context context, MovieRecommendation view) {
        this.context = context;
        this.view = view;
    }

    public void getMovieRecommendations() {
        Connection connection = Connect.getConnection();

        try {
            if (connection == null) {
                System.out.println("connection is null!!!");
            }
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(buildQueryString());
            if (view.getTable() == null) {
            	  	System.out.println("view is null!!!");
            } else {
            		System.out.println("view is not null!!!");
            }
            view.getTable().setModel(DbUtils.resultSetToTableModel(resultSet));
        } catch(Exception e1) {
            //handle bad data
            JOptionPane.showMessageDialog(null, e1);
        }
        Connect.runQuery(buildQueryString());
    }


    private String buildQueryString() {
        //build query string systematically using all possible input data.
        String qry = "Select title from movies where movieID IN (select distinct movieID from user_ratedmovies where rating = 5 and movieID IN "
                + "(select distinct movieID from movie_genres where genre IN "
                + "(select genre from likedGenre where userID = " + context.user.getUserId() +")))"
                + " order by rtAudienceRating desc limit 10";
        System.out.println("Query: " + qry);
        return qry;
    }
}