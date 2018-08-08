package myMovieFinder.Views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JTextField;

import myMovieFinder.Context;
import myMovieFinder.ViewControllers.FindMoviesController;
import javax.swing.JSlider;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;

public class FindMovies {
	private FindMoviesController controller;
	private static ResultSet resultSet = null;
	private Context context;
	private JFrame frame;
	private JTable table;
	private JLabel lblMovieName;
	private JPanel reviewPanel;
	private JTextField textTitle;
	private JTextField textDirector;
	private JTextField textField_2;
	private JTextField genre;
	private JTextField actor;

	private int allCriticsRating;
	private int topCriticsRating;
	private int audienceRating;

	public void run(Context context) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FindMovies window = new FindMovies(context);
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
	public FindMovies(Context context) {
		this.context = context;
		context.setLaunch(false); //no need to re-launch
		this.controller = new FindMoviesController(this.context, this);

		frame = new JFrame();
		frame.setBounds(100, 100, 958, 613);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// Review Panel
		reviewPanel = new JPanel();
		reviewPanel.setBackground(new Color(248, 248, 255));
		reviewPanel.setBounds(10, 397, 192, 98);
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

		//main 
		JLabel lblFindMovies = new JLabel("Find Movies!");
		lblFindMovies.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblFindMovies.setBounds(20, 8, 182, 31);
		frame.getContentPane().add(lblFindMovies);
		
		JButton btnGetRec = new JButton("Recommended Movies");
		btnGetRec.setBounds(10, 507, 192, 40);
		frame.getContentPane().add(btnGetRec);
		btnGetRec.addActionListener(controller);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(223, 19, 660, 528);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.addMouseListener(controller);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(controller);
		btnExit.setBounds(119, 349, 89, 23);
		frame.getContentPane().add(btnExit);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTitle.setBounds(20, 53, 57, 20);
		frame.getContentPane().add(lblTitle);
		
		textTitle = new JTextField();
		textTitle.setBounds(74, 53, 98, 20);
		frame.getContentPane().add(textTitle);
		textTitle.setColumns(10);
		
		textDirector = new JTextField();
		textDirector.setColumns(10);
		textDirector.setBounds(74, 84, 98, 20);
		frame.getContentPane().add(textDirector);
		
		JLabel lblDirector = new JLabel("Director");
		lblDirector.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDirector.setBounds(10, 82, 57, 20);
		frame.getContentPane().add(lblDirector);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(74, 115, 98, 20);
		frame.getContentPane().add(textField_2);
		
		JLabel lblYear = new JLabel("Year");
		lblYear.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblYear.setBounds(20, 115, 57, 20);
		frame.getContentPane().add(lblYear);
		
		genre = new JTextField();
		genre.setColumns(10);
		genre.setBounds(74, 146, 98, 20);
		frame.getContentPane().add(genre);
		
		JLabel lblGenre = new JLabel("Genre");
		lblGenre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGenre.setBounds(20, 146, 57, 20);
		frame.getContentPane().add(lblGenre);
		
		actor = new JTextField();
		actor.setColumns(10);
		actor.setBounds(74, 177, 98, 20);
		frame.getContentPane().add(actor);
		
		JLabel lblActor = new JLabel("Actor");
		lblActor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblActor.setBounds(20, 177, 57, 20);
		frame.getContentPane().add(lblActor);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(controller);
		btnSearch.setBounds(20, 349, 89, 23);
		frame.getContentPane().add(btnSearch);
		
		JLabel lblAverageRating = new JLabel("Average Rating:");
		lblAverageRating.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAverageRating.setBounds(20, 208, 115, 23);
		frame.getContentPane().add(lblAverageRating);
		
		JLabel lblAllCritics = new JLabel("All Critics");
		lblAllCritics.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAllCritics.setBounds(20, 236, 57, 26);
		frame.getContentPane().add(lblAllCritics);
		
		JLabel lblTopCritics = new JLabel("Top Critics");
		lblTopCritics.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTopCritics.setBounds(10, 270, 57, 23);
		frame.getContentPane().add(lblTopCritics);
		
		JLabel lblAudience = new JLabel("Audience");
		lblAudience.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAudience.setBounds(10, 304, 57, 20);
		frame.getContentPane().add(lblAudience);
		
		
		JSlider slider = new JSlider();
		slider.setValue(0);
		slider.setName("All Critics");
		slider.setMaximum(10);
		slider.setBounds(74, 236, 98, 26);
		frame.getContentPane().add(slider);
		slider.addChangeListener(controller);
		
		JSlider slider_1 = new JSlider();
		slider_1.setValue(0);
		slider_1.setName("Top Critics");
		slider_1.setMaximum(10);
		slider_1.setBounds(74, 267, 98, 26);
		frame.getContentPane().add(slider_1);
		slider_1.addChangeListener(controller);
		
		JSlider slider_2 = new JSlider();
		slider_2.setValue(0);
		slider_2.setName("Audience");
		slider_2.setMaximum(5);
		slider_2.setBounds(74, 301, 98, 26);
		frame.getContentPane().add(slider_2);
		slider_2.addChangeListener(controller);
		
		

	}

	public static void setResultSet(ResultSet resultSet) {
		FindMovies.resultSet = resultSet;
	}

	public static ResultSet getResultSet() {
		return resultSet;
	}


	public JFrame getFrame() {
		return frame;
	}


	public JTable getTable() {
		return table;
	}

	public JLabel getLblMovieName() {
		return lblMovieName;
	}

	public JPanel getReviewPanel() {
		return reviewPanel;
	}


	public JTextField getTextTitle() {
		return textTitle;
	}

	public JTextField getTextDirector() {
		return textDirector;
	}

	public JTextField getTextField_2() {
		return textField_2;
	}


	public JTextField getGenre() {
		return genre;
	}

	public JTextField getActor() {
		return actor;
	}


	public void setAllCriticsRating(int allCriticsRating) {
		this.allCriticsRating = allCriticsRating;
	}

	public int getAllCriticsRating() {
		return allCriticsRating;
	}

	public void setTopCriticsRating(int topCriticsRating) {
		this.topCriticsRating = topCriticsRating;
	}

	public int getTopCriticsRating() {
		return topCriticsRating;
	}

	public void setAudienceRating(int audienceRating) {
		this.audienceRating = audienceRating;
	}

	public int getAudienceRating() {
		return audienceRating;
	}

	public Context getContext() {
		return context;
	}
}
