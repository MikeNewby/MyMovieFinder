package myMovieFinder.ViewControllers;


import myMovieFinder.Connect;
import myMovieFinder.Context;
import myMovieFinder.Models.Movie;
import myMovieFinder.Views.AddReview;
import myMovieFinder.Views.MovieRecommendation;
import myMovieFinder.Query;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;

public class MovieRecommendationController implements ActionListener, MouseListener{
    private Context context;
    private MovieRecommendation view;
    private Connection connection;

    public MovieRecommendationController(Context context, MovieRecommendation view) {
        this.context = context;
        this.view = view;
        this.connection = Connect.getConnection();
    }

    public void getMovieRecommendations() {
        String qry = buildQueryString();
        try {
            if (connection == null) {
                System.out.println("connection is null!!!");
            }
            
            Statement statement = connection.createStatement();
            System.out.println("Query: " + qry);
            ResultSet resultSet = statement.executeQuery(qry);    
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

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "New Review") {
            AddReview.run(context);
        }

        if (e.getActionCommand() == "Exit") {
            view.getFrame().dispose();
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    	 view.getFrame().getContentPane().add(view.getReviewPanel());
         view.getFrame().repaint();
         view.getFrame().revalidate();
         //reviewPanel.add(lblReview);
         int rowIndex = view.getTable().getSelectedRow();
         int movieId = (int) view.getTable().getValueAt(rowIndex, 0);
         view.getContext().setMovie(Query.getMovieById(movieId));
         String movieName = (String) view.getTable().getValueAt(rowIndex, 1);
         movieName = movieId + " - " + movieName;
         view.getLblMovieName().setText(movieName);
    };

    public void mouseEntered(MouseEvent e) {
    };

    @Override
    public void mouseReleased(MouseEvent e) {
    };

    @Override
    public void mouseExited(MouseEvent e) {
    };

    @Override
    public void mousePressed(MouseEvent e) {
    };

    private String buildQueryString() {
        //build query string systematically using all possible input data.
        String qry = "Select * from movies where movieID IN (select distinct movieID from user_ratedmovies where rating > 3 and movieID IN "

                + "(select distinct movieID from movie_genres where genre IN "
                + "(select genre from likedGenre where userID = " + context.user.getUserId() +")))"
                + " order by rtAudienceRating desc limit 10;";
        
        return qry;
        
    }
}