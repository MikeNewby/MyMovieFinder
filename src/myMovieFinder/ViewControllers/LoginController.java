package myMovieFinder.ViewControllers;

import myMovieFinder.Connect;
import myMovieFinder.Context;
import myMovieFinder.Models.User;
import myMovieFinder.Views.LogIn;
import myMovieFinder.Views.FindMovies;
import myMovieFinder.Views.MainGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener {
    private LogIn view;
    private Context context;

    public LoginController(Context context, LogIn view) {
        this.view = view;
        this.context = context;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Log In") {
            String email = view.getTxtUserName().getText();
            String password = new String(view.getPasswordField().getPassword());
            try {
                System.out.println("email: " + email + ", password: " + password);
                // TODO: Update Query for user ID
                String userQry = "SELECT * from users WHERE email='" + email + "' and password='" + password + "'";
                System.out.println(userQry);
                int userId = Connect.checkUser(userQry);
                if(userId > 0) {
                    System.out.println("User " + email + " found!");
                    view.getFrame().dispose();

                    User user = new User();
                    user.setUserId(userId);
                    user.setEmail(email);
                    context.user = user;
                    FindMovies findMovies = new FindMovies(context);
                    findMovies.run(context);
                } 
                else {
                		String strResult = "Invalid User ID and Password Combo.\n Please try again.";
                		System.out.println(strResult);
                		view.getLblLogin().setText(strResult);
                	}

            }catch(Exception e1) {
                // handle bad data
                JOptionPane.showMessageDialog(null, e1);
            }
        }

        if (e.getActionCommand() == "Cancel") {
            view.getFrame().dispose();
            MainGUI.main(null);
        }
    }
}
