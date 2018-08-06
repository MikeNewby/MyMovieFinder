package myMovieFinder.Views;

import myMovieFinder.Context;
import myMovieFinder.ViewControllers.CreateUserController;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JPasswordField;

public class CreateUser {
	private JFrame frame;
	private JTextField emailTextField;
	private JPasswordField passwordTextField;
	private Context context;

	public JFrame getFrame() {
		return frame;
	}


	public JTextField getEmailTextField() {
		return emailTextField;
	}


	public JPasswordField getPasswordTextField() {
		return passwordTextField;
	}

	/**
	 * Launch the application.
	 */
	public static void run(Context context) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateUser window = new CreateUser(context);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CreateUser(Context context) {
		this.context = context;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		CreateUserController controller = new CreateUserController(this.context, this);

		frame = new JFrame();
		frame.setBounds(100, 100, 250, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel title = new JLabel("My Movie Finder");
		title.setFont(new Font("Tahoma", Font.BOLD, 16));
		title.setBounds(25, 11, 186, 27);
		frame.getContentPane().add(title);

		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		emailLabel.setBounds(25, 69, 80, 20);
		frame.getContentPane().add(emailLabel);

		emailTextField = new JTextField();
		emailTextField.setForeground(new Color(0, 0, 0));
		emailTextField.setBounds(104, 71, 107, 20);
		frame.getContentPane().add(emailTextField);
		emailTextField.setColumns(10);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		passwordLabel.setBounds(25, 100, 69, 20);
		frame.getContentPane().add(passwordLabel);

		passwordTextField = new JPasswordField();
		passwordTextField.setBounds(104, 102, 107, 20);
		frame.getContentPane().add(passwordTextField);

		JButton createButton = new JButton("Create");
		createButton.addActionListener(controller);

		createButton.setBounds(25, 164, 88, 41);
		frame.getContentPane().add(createButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(controller);

		cancelButton.setBounds(123, 164, 88, 41);
		frame.getContentPane().add(cancelButton);
	}
}
