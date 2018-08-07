package myMovieFinder.Views;

import myMovieFinder.Context;
import myMovieFinder.ViewControllers.AddReviewController;

import java.awt.EventQueue;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.*;

import java.awt.*;

public class AddReview {
	private JFrame frame;
	private JSlider slider;
	private Context context;

	/**
	 * Launch the application.
	 */
	public static void run(Context context) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddReview window = new AddReview(context);
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
	public AddReview(Context context) {
		this.context = context;
		AddReviewController controller = new AddReviewController(this.context, this);

		int width = 600;
		int height = 600;

		frame = new JFrame();
		frame.setBounds(100, 100, width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel title = new JLabel(context.movie.getTitle(), SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.PLAIN, 22));
		title.setBounds((width - 400) / 2, 30, 400, 36);
		frame.getContentPane().add(title);

		//show image from rotten tomatoes
		URL imgPath = null;
		try {
			String url = context.movie.getImgUrl();
			imgPath = new URL(context.movie.getImgUrl());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			try {
				imgPath = new URL("http://content7.flixster.com/movie/10/93/63/10936393_det.jpg");
			} catch (MalformedURLException e2) {
				e.printStackTrace();
			}
		}

		ImageIcon img = new ImageIcon(imgPath);
		JLabel lblMovieImage = new JLabel("");
		lblMovieImage.setIcon(img);
		lblMovieImage.setBounds((width - 180) / 2, 100, 180, height / 2);
		frame.getContentPane().add(lblMovieImage);

		slider = new JSlider();
		slider.setBounds((width - 200) / 2, height - (height / 3), 200, 20);
		slider.setMajorTickSpacing(25);
		slider.setPaintTicks(true);
		slider.setSnapToTicks(true);
		slider.addChangeListener(controller);
		frame.getContentPane().add(slider);

		JButton addReviewButton = new JButton("Rate");
		addReviewButton.addActionListener(controller);
		addReviewButton.setBounds((width - 175) / 2, height - (height / 4), 80, 50);
		frame.getContentPane().add(addReviewButton);

		JButton addSkipButton = new JButton("Skip"); 
		addSkipButton.addActionListener(controller);
		addSkipButton.setBounds((width + 25) / 2, height - (height / 4), 80, 50);
		frame.getContentPane().add(addSkipButton);
	}

	public JFrame getFrame() {
		return frame;
	}

	public JSlider getSlider() {
		return slider;
	}

}
