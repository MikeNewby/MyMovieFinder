package myMovieFinder;

import myMovieFinder.Models.Movie;
import myMovieFinder.Models.User;

public class Context {
    //public int userId;
    public User user;
    public Movie movie;
    public boolean launchFindMovies;  //used to track open windows
    public int numMoviesReviewed;
    
    public void setNumMoviesReviewed(int numMoviesReviewed) {
        this.numMoviesReviewed = numMoviesReviewed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Context() {
        numMoviesReviewed = 0;
    };
    
    public boolean getLaunch() {
        return launchFindMovies;
    }

    public void setLaunch(boolean launch) {
        this.launchFindMovies = launch;
    }

}
