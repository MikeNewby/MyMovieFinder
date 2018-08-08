package myMovieFinder.ViewControllers;

import myMovieFinder.Views.AddReview;
import myMovieFinder.Context;
import myMovieFinder.Views.FindMovies;
import myMovieFinder.Query;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddReviewController implements ActionListener, ChangeListener {
    private Context context;
    private AddReview view;
    private int rating;

    public AddReviewController(Context context, AddReview view) {
        super();
        this.context = context;
        this.view = view;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        rating = view.getSlider().getValue() / 25 + 1; // convert to 1 - 5 scale
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Rate") {
            Query.rateMovie(context.user.getUserId(), context.movie.getMovieId(), rating); //movieID, rating
            context.numMoviesReviewed++;
        }

        view.getFrame().dispose();
        
        //don't re-launch find movies or another add review for other windows
        if(!context.getLaunch())
        	return;

        if (context.numMoviesReviewed > 2) {   //3
            FindMovies findMovies = new FindMovies(context);
            findMovies.run(context);
            return;
        }

        context.movie = Query.getSuggestedMovie(context.user.getUserId()); //parameter 
        AddReview.run(context);
    }
}
