package myMovieFinder.Views;

import javax.swing.JButton;
import javax.swing.JFrame;

import myMovieFinder.Context;
import myMovieFinder.ViewControllers.FindMoviesController;
import myMovieFinder.ViewControllers.MovieRecommendationController;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;

public class MovieRecommendation {
	private JFrame frame;
	private JTable tblRecommended;
	private JLabel lblMovieRecommendations;
	private Context context;
	private MovieRecommendationController controller;
	private JLabel lblMovieName;
	private JPanel reviewPanel;


	public JTable getTable() {
		if (table == null) {
			System.out.println("getTable() returns a null table!");
		}
		return table;
	}
	
	public static void run(Context context) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println("Run login!");
					MovieRecommendation window = new MovieRecommendation(context);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MovieRecommendation(Context context) {
		this.context = context;
		context.setLaunch(false); //no need to re-launch
		this.controller = new MovieRecommendationController(context, this);
		
    // Main frame to put components.
		frame = new JFrame();
		frame.setBounds(100, 100, 908, 324);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// Movie recommendation label.
		lblMovieRecommendations = new JLabel("Recommended Movies");
		lblMovieRecommendations.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMovieRecommendations.setBounds(223, 11, 646, 32);
		frame.getContentPane().add(lblMovieRecommendations);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(223, 39, 660, 216);
		frame.getContentPane().add(scrollPane);
		tblRecommended = new JTable();
		scrollPane.setViewportView(tblRecommended);
		tblRecommended.addMouseListener(controller);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(controller);
		btnExit.setBounds(56, 148, 125, 32);
		frame.getContentPane().add(btnExit);
		
		// Review Panel
		reviewPanel = new JPanel();
		reviewPanel.setBackground(new Color(248, 248, 255));
		reviewPanel.setBounds(21, 39, 192, 98);
		reviewPanel.setLayout(null);
		//frame.getContentPane().add(reviewPanel);  //hide initially. uncomment for testing. TODO	
		
		lblMovieName = new JLabel("");
		lblMovieName.setHorizontalAlignment(SwingConstants.CENTER);
		lblMovieName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMovieName.setBounds(0, 26, 192, 25);
		reviewPanel.add(lblMovieName);		
		
		JLabel lblReview = new JLabel("Review this movie!");
		lblReview.setHorizontalAlignment(SwingConstants.CENTER);
		lblReview.setBounds(0, 0, 192, 25);
		lblReview.setFont(new Font("Tahoma", Font.PLAIN, 16));
		reviewPanel.add(lblReview);
		
		JButton btnNewReview = new JButton("New Review");
		btnNewReview.addActionListener(controller);
		btnNewReview.setBounds(31, 51, 127, 40);
		reviewPanel.add(btnNewReview);

		controller.getMovieRecommendations();
	}
	
	public JTable getTable() {
		return tblRecommended;
	}
	
	public JLabel getlblMovieRecommendations() {
		return lblMovieRecommendations;
	}
	
	public JFrame getFrame() {
		return frame;
	}

	public JPanel getReviewPanel() {
		return reviewPanel;
	}
	
	public Context getContext() {
		return context;
	}
	
	public JLabel getLblMovieName() {
		return lblMovieName;
	}
}
