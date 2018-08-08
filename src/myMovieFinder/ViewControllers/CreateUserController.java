package myMovieFinder.ViewControllers;

import myMovieFinder.*;
import myMovieFinder.Models.User;
import myMovieFinder.Views.AddGenre;
import myMovieFinder.Views.CreateUser;
import myMovieFinder.Views.MainGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateUserController implements ActionListener {
    private Context context;
    private CreateUser view;

    public CreateUserController(Context context, CreateUser view) {
        super();
        this.context = context;
        context.setLaunch(true); //launch find movies module after rating
        this.view = view;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Cancel") {
            view.getFrame().dispose();
            MainGUI.main(null);
        }

        if (e.getActionCommand() == "Create") {
            User user = new User();
            try {
                String email = view.getEmailTextField().getText();
                String password = new String(view.getPasswordTextField().getPassword());
                int id = Query.createUser(email, password);

                user.setUserId(id);
                user.setEmail(email);
                user.setPassword(password);
                context.setUser(user);

                AddGenre.runAddGenre(context);
                //Add_Review.main(context);
                view.getFrame().dispose();
            } catch (Exception e1) {
                //handle bad data
                JOptionPane.showMessageDialog(null, e1);
            }
        }
    }
}
