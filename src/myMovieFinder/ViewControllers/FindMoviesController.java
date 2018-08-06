package myMovieFinder.ViewControllers;

import myMovieFinder.*;
import myMovieFinder.Views.AddReview;
import myMovieFinder.Views.FindMovies;
import myMovieFinder.Views.MovieRecommendation;
import myMovieFinder.Views.ReadReview;
import net.proteanit.sql.DbUtils;
import java.sql.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.util.StringJoiner;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FindMoviesController implements ActionListener, MouseListener, ChangeListener {
    private FindMovies view;
    private Context context;
    private Connection connection;

    public FindMoviesController(Context context, FindMovies view) {
        this.view = view;
        this.context = context;
        this.connection = Connect.getConnection();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Recommendations") {
            MovieRecommendation.run(context);
        }

        if (e.getActionCommand() == "New Review") {
            AddReview.run(context);
        }

        if (e.getActionCommand() == "Read Reviews") {
            ReadReview.run(context);
        }

        if (e.getActionCommand() == "Exit") {
            view.getFrame().dispose();
        }

        if (e.getActionCommand() == "Search") {
            String title = view.getTextTitle().getText();
            String director = view.getTextDirector().getText();
            String Field_2 = view.getTextField_2().getText();
            int year = Integer.parseInt("0" + Field_2);
            String genre2 = view.getGenre().getText();
            String actor2 = view.getActor().getText();

            String qry = buildQueryString(title, director, year, genre2, actor2, view.getAllCriticsRating(), view.getTopCriticsRating(), view.getAudienceRating());
            try {
                Statement statement = connection.createStatement();
                view.setResultSet(statement.executeQuery(qry));
                view.getTable().setModel(DbUtils.resultSetToTableModel(view.getResultSet()));

            }catch(Exception e1) {
                //handle bad data
                JOptionPane.showMessageDialog(null, e1);
            }

            Connect.runQuery(qry);
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

    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        String name = slider.getName();

        if (!slider.getValueIsAdjusting()) {
            int value = slider.getValue();
            if (name == "All Critics") {
                view.setAllCriticsRating(value);
            }

            if (name == "Top Critics") {
                view.setTopCriticsRating(value);
            }

            if (name == "Audience") {
                view.setAudienceRating(value);
            }
        }
    }

    @Override
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


    protected String buildQueryString(String title, String director, int year, String genre, String actor, int allCritics, int topCritics, int audienceRating) {
        //build query string systematically using all possible input data.
        String qry = "";
        
       /*
        qry = "SELECT movieID,title,year,rtAllCriticsRating,rtTopCriticsRating,rtAudienceRating from movies"
                + " WHERE title LIKE '%" + title + "%'" + " and movieID IN (select d.movieID from movie_directors d where d.directorName LIKE '%" + director + "%'"
                + " and year >= " + String.valueOf(year)
                + " and movieID IN (select g.movieID from movie_genres g where g.genre LIKE '%" + genre2 + "%')"
                + " and movieID IN (select a.movieID from movie_actors a where a.actorName LIKE '%"+ actor2 + "%') "
                + "and rtAllCriticsRating >= " + val1 + " and rtTopCriticsRating >= " + val2 + " and rtAudienceRating>= " + val3 +  ")";
         */
        
        //dynamically build query
        //base query, select all movies if nothing is filled in:
      	qry = "SELECT movieID,title,year,rtAllCriticsRating,rtTopCriticsRating,rtAudienceRating FROM movies m";
      	StringJoiner qryRestrictionJoiner = new StringJoiner(" AND "); 
      		 
      	//build query string
      	//Title (simple)
      	if(title.length() > 0) {
      		qryRestrictionJoiner.add("m.title LIKE '%" + title + "%'");
      	}
      	//Year  (simple)
      	if(year > 0) {
      		qryRestrictionJoiner.add("m.year = " + year);
      	}
      	
        //Sliders for ratings - in movie table, so these are still simple queries.
      	//All Critics
      	if(allCritics > 0) {
      		qryRestrictionJoiner.add("m.rtAllCriticsRating > " + allCritics);
      	}
      	//Top Critics
      	if(topCritics > 0) {
      		qryRestrictionJoiner.add("m.rtTopCriticsRating > " + topCritics);
      	}
      	//Audience
      	if(audienceRating > 0) {
      		qryRestrictionJoiner.add("m.rtAudienceRating > " + audienceRating);
      	}
      	
      	//Director - link out to movie_directors
      	if(director.length() > 0) {
      		qryRestrictionJoiner.add("movieID IN (select d.movieID from movie_directors d where d.directorName LIKE '%" + director + "%')");
      	}
      	
      	//Genre - link out to genre 
      	if(genre.length() > 0) {  		
      		qryRestrictionJoiner.add("movieID IN (select g.movieID from movie_genres g where g.genre LIKE '%" + genre + "%')");

      	}
      	
      	//Actor
      	if(actor.length() > 0) {
      		qryRestrictionJoiner.add("movieID IN (select a.movieID from movie_actors a where a.actorName LIKE '%"+ actor + "%') ");
      	}
      		
      	
      	
      	//tack on the Where piece of the statement if we have it. 
      	if(qryRestrictionJoiner.length() > 0)
      		qry = qry + " WHERE " + qryRestrictionJoiner.toString();
      		
        System.out.println("Query: " + qry);
        return qry;
    }
}
