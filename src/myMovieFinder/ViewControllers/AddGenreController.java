package myMovieFinder.ViewControllers;

import myMovieFinder.Views.AddGenre;
import myMovieFinder.Views.AddReview;
import myMovieFinder.Context;
import myMovieFinder.Query;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddGenreController implements ActionListener, ListSelectionListener {
    private String[] genres;
    private AddGenre view;
    private Context context;
    private List<Integer> selected;
    private int count;

    public AddGenreController(Context context, AddGenre view) {
        super();
        genres = Query.getSuggestedGenres();
        this.context = context;
        this.view = view;
        count = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // save selected user - genre relationship on select
        for (Integer selection: selected) {
            Query.likeGenre(context.user.getUserId(), genres[selection]); 
            count++;
        }
        if (count > 2) {
            context.movie = Query.getSuggestedMovie(context.user.getUserId()); //maybe 
            AddReview.run(context);
            view.getFrame().dispose();;
        }
    }
    @Override
    public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel lsm = (ListSelectionModel)e.getSource();
        List<Integer> selected = new ArrayList<>();

        if (!lsm.isSelectionEmpty()) {
            // Find out which indexes are selected.
            int minIndex = lsm.getMinSelectionIndex();
            int maxIndex = lsm.getMaxSelectionIndex();
            for (int i = minIndex; i <= maxIndex; i++) {
                if (lsm.isSelectedIndex(i)) {
                    selected.add(i);
                }
            }
        }

        this.selected = selected;
    }

    public String[] getGenres() {
        return genres;
    }
}
