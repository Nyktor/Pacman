package graphics;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MainFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	GamePanel gp;
	
	/****************************** MAIN FRAME ******************************/
	public MainFrame() {

		// Creates the main frame
		this.setTitle("Pacman");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		this.gp = new GamePanel();
		this.add(gp, BorderLayout.CENTER);
		
		this.pack();
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		addKeyListener(gp);
		gp.start();
		removeKeyListener(gp);
	}

}
