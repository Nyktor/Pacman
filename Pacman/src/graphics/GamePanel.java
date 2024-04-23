package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.Direction;
import game.Game;
import objects.entities.Ball;
import objects.entities.Entity;
import objects.entities.characters.Pacman;
import objects.entities.characters.ghost.Ghost;
import objects.entities.fruits.Fruit;

public class GamePanel extends JPanel implements KeyListener{

	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int SQUARES_PER_SECOND = 20;
	
	Game game;
	
	BufferedImage wall;
	BufferedImage wallBlinking;
	BufferedImage floor;
	BufferedImage normalBall;
	BufferedImage bigBall;
	
	BufferedImage cherry;
	BufferedImage strawberry;
	BufferedImage peach;
	BufferedImage apple;
	BufferedImage grapes;
	BufferedImage galaxianFlagship;
	BufferedImage bell;
	BufferedImage key;
	
	BufferedImage pacmanUP;
	BufferedImage pacmanLEFT;
	BufferedImage pacmanRIGHT;
	BufferedImage pacmanDOWN;
	BufferedImage pacmanMouthClosed;
	
	BufferedImage blinky;

	JLabel[][] gridLabels;
	JLabel pointsLabel = new JLabel();
	JLabel levelLabel = new JLabel();
	JLabel lives = new JLabel();
	
	JPanel topBar = new JPanel();
	
	JPanel board = new JPanel();
	
	
	public GamePanel() {
		
		loadIcons();
		
		game = new Game();
		game.initializeGame();
		
		gridLabels = new JLabel[game.getMap().HEIGHT][game.getMap().WIDTH];
		
		levelLabel.setText("LEVEL "+game.getLevel());
		levelLabel.setFont(new Font("Consolas", Font.BOLD, 20));
		levelLabel.setForeground(Color.white);
		levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		pointsLabel.setText("POINTS: "+game.getPoints());
		pointsLabel.setFont(new Font("Consolas", Font.BOLD, 20));
		pointsLabel.setForeground(Color.white);
		pointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		lives.setText("LIVES: "+game.getPacman().getLives());
		lives.setFont(new Font("Consolas", Font.BOLD, 20));
		lives.setForeground(Color.white);
		lives.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		this.setLayout(new BorderLayout());
		
		board.setLayout(new GridLayout(game.getMap().HEIGHT, game.getMap().WIDTH));
		topBar.setLayout(new GridLayout());

		topBar.add(levelLabel);
		topBar.add(pointsLabel);
		topBar.add(lives);
		
		topBar.setBackground(Color.black);
		
		this.add(topBar, BorderLayout.NORTH);
		this.add(board, BorderLayout.CENTER);
		
		printMainMap();
		printPacman((byte)1);
	}
	
	/* MAIN METHOD */
	public void start() {
		byte frame = 0;
		int seconds = 0, bigBallDurationFinish = -1, fruitDurationFinish  = -1;
		do {
			while(!game.isWon() && !game.getPacman().isDead()) {
				
				// Move the Pacman
				movePacman(frame);
				//moveBlinky(game.getBlinky());
				
				// Update score
				pointsLabel.setText("SCORE: "+game.getPoints());	
				
				// Wait a few ms
				freezeGame(squares_per_second_to_milliseconds()); 	
				frame++;
				
				// Counts the seconds depending on the frames
				if(frame % SQUARES_PER_SECOND == 0) {
					seconds++;
					frame = 1;
				}
				
				
				/* FRUIT RELATED CHECKS */
				// It's time to spawn a fruit
				if(game.getBallsEaten() == game.BALLS_FOR_FIRST_FRUIT) {
					game.spawnFruit();
					printFruit();
					fruitDurationFinish = seconds + 10;
				
				}else if(game.getBallsEaten() == game.BALLS_FOR_SECOND_FRUIT && !game.isFruitPresent()) {
					game.spawnFruit();
					printFruit();
					fruitDurationFinish = seconds + 10;

				// Fruit time is up
				}else if(fruitDurationFinish == seconds && game.isFruitPresent()) {
					game.despawnFruit();
					printFruit();
					fruitDurationFinish = -1;
				
				// If fruit time is still going, but fruit has been eaten
				}else if(fruitDurationFinish > 0 && !game.isFruitPresent()) {
					fruitDurationFinish = -1;
				}
				
				// TODO
				// Manages if pacman has eaten an energizer recently AND the duration has finishd
				/*if(game.hasEatenBigBallRecently() && seconds == bigBallDurationFinish) {
					game.resetBigBallEaten();
					bigBallDurationFinish = -1;
				}
				/*if(game.hasEatenBigBallRecently()) {
					bigBallDurationFinish = seconds+10;
				}*/
			}
			
			/* IF PACMAN EATS ALL THE BALLS */
			if(game.isWon()) {

				freezeGame(1000);
				blinkMap();
				loadNextLevel();
				
				frame = 0;

			/* IF PACMAN COLLIDES WITH A GHOST */
			}else {

				freezeGame(1000);
				
			}
			
			
		}while(!game.isLost());
		
		topBar.remove(pointsLabel);
		topBar.remove(lives);
		levelLabel.setText("G A M E   O V E R");
	}
	
	private void checkFruit() {
		
	}
	
	
	private void movePacman(byte frame) {
		int previousPacRow = game.getPacman().getRow();
		int previousPacCol = game.getPacman().getColumn();
		game.moveCharacter(game.getPacman());
		printPacman((byte)(frame%2));
		if(previousPacCol != game.getPacman().getColumn() || previousPacRow != game.getPacman().getRow()) {
			setIcon(previousPacCol, previousPacRow, floor);
		}
		
	}
	
	private void moveGhost(Ghost ghost) {
		int previousGhostRow = ghost.getRow();
		int previousGhostCol = ghost.getColumn();
		
		// If Blinky's movement fails
		if(!game.moveCharacter(ghost)) {
			ghost.setDirection(Direction.getRandomDirection());
		}
		
		printGhost(ghost);
		
		if(previousGhostCol != ghost.getColumn() || previousGhostRow != ghost.getRow()) {
			
			Entity entityBeneath = ghost.getEntityUnderGhost();
			
			if(entityBeneath instanceof Ball) {
				
				setIcon(previousGhostCol, previousGhostRow, ((Ball)entityBeneath).isBig() ? bigBall : normalBall);
				
			}else if(entityBeneath instanceof Fruit) {
				
				printFruit();
				
				// TODO manage collision ghost to ghost
			}else {
				setIcon(previousGhostCol, previousGhostRow, floor);
			}
		}
	}
	
	
	private void loadNextLevel() {
		reallocatePacmanOnRestart();
		
		/* REFILL MAP */
		game.getMap().refillMap();
		game.resetBallsEaten(); // sets eaten balls again to 0
		reprintBalls();
		
		/* ADD ONE LEVEL */
		game.levelUp();
		levelLabel.setText("LEVEL "+game.getLevel());
		
		freezeGame(3000);
	}
	
	
	/* PUT PACMAN BACK AT THE INITIAL POSITION */
	private void reallocatePacmanOnRestart() {
		game.getPacman().setColumn(game.getMap().PACMAN_INITIAL_ROW); //IDK WHY
		game.getPacman().setRow(game.getMap().PACMAN_INITIAL_COLUMN); //COLUMN and ROW are SWAPPED!?!
		game.getPacman().setOrientation(Direction.LEFT);
		printPacman((byte)1);
		
	}

/*************************************************************PRINTING METHODS*************************************************************/
	private void printPacman(byte frame) {
		Pacman pac = game.getPacman();
		
		if(frame == 0) setIcon(pac.getColumn(), pac.getRow(), pacmanMouthClosed);
		else {
			BufferedImage orientation;
			
			switch(pac.getOrientation()) {
			case DOWN:
				orientation = pacmanDOWN;
				break;
			case LEFT:
				orientation = pacmanLEFT;
				break;
			case RIGHT:
				orientation = pacmanRIGHT;
				break;
			case UP:
				orientation = pacmanUP;
				break;
			default:
				orientation = pacmanLEFT;
				break;
			}
			setIcon(pac.getColumn(), pac.getRow(), orientation);
		}
	}
	
	private void printGhost(Ghost ghost) {
		BufferedImage orientation = blinky;
		
		switch(ghost.getName()) {
		// TODO make different sprites
			case BLINKY ->{
				switch(ghost.getOrientation()) {
				case DOWN:
					orientation = blinky;
					break;
				case LEFT:
					orientation = blinky;
					break;
				case RIGHT:
					orientation = blinky;
					break;
				case UP:
					orientation = blinky;
					break;
				default:
					orientation = blinky;
					break;
				}
			}
			
			case PINKY ->{
				
			}
			
			case INKY ->{
				
			}
			
			case CLYDE ->{
				
			}
		}
		
		setIcon(ghost.getColumn(), ghost.getRow(), orientation);
		
	}
	
	private void printFruit() {
		int column = game.getMap().getFruitCoords()[1];
		int row = game.getMap().getFruitCoords()[0];
		
		if(!game.isFruitPresent()) {
			setIcon(row, column, floor);
		}else {
			setIcon(row, column, switch(game.getLevel()) {
				case 1 ->{
					yield cherry;
				}
				case 2 ->{
					yield strawberry;
				}
				case 3,4 ->{
					yield peach;
				}
				case 5,6->{
					yield apple;
				}
				case 7,8->{
					yield grapes;
				}
				case 9,10 ->{
					yield galaxianFlagship;
				}
				case 11, 12 ->{
					yield bell;
				}
				default ->{
					yield key;
				}
			});
		}
	}
	
	private void blinkMap() {
		for(int t = 0; t < 6; t++) {
			for(int i = 0; i < game.getMap().HEIGHT; i++) {
				for(int j = 0; j < game.getMap().WIDTH; j++) {
					switch(game.getMap().BITMAP[i][j]) {
						case 0 ->{
							setIcon(i,j, t%2==0?wallBlinking:wall);
						}
						default ->{
							continue;
						}
					}
				}
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void reprintBalls() {
		for(int i = 0; i < game.getMap().HEIGHT; i++) {
			for(int j = 0; j < game.getMap().WIDTH; j++) {
				switch(game.getMap().BITMAP[i][j]) {
					case 1 ->{
						setIcon(i,j,normalBall);
					}
					case 4 ->{
						setIcon(i,j,bigBall);
					}
					default ->{
						continue;
					}
				}
			}
		}
	}
	
	private void printMainMap() {
		for(int i = 0; i < game.getMap().HEIGHT; i++) {
			for(int j = 0; j < game.getMap().WIDTH; j++) {
				gridLabels[i][j] = new JLabel();
				switch(game.getMap().BITMAP[i][j]) {
					case 0 ->{
						setIcon(i,j, wall);
					}
					case 1 ->{
						setIcon(i,j,normalBall);
					}
					case 4 ->{
						setIcon(i,j,bigBall);
					}
					default ->{
						setIcon(i,j,floor);
					}
				}
				board.add(gridLabels[i][j]);
			}
		}
	}
	
	private void freezeGame(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

	// Self-explanatory, done at the beginning
	private void loadIcons() {
		try{
			wall = ImageIO.read(new File("src/textures/wall.png"));
			wallBlinking = ImageIO.read(new File("src/textures/wallBlinking.png"));
			floor = ImageIO.read(new File("src/textures/floor.png"));
			normalBall = ImageIO.read(new File("src/textures/normalBall.png"));
			bigBall = ImageIO.read(new File("src/textures/bigBall.png"));
			
			cherry = ImageIO.read(new File("src/textures/cherry.png"));
			strawberry = ImageIO.read(new File("src/textures/strawberry.png"));
			peach = ImageIO.read(new File("src/textures/peach.png"));
			apple = ImageIO.read(new File("src/textures/apple.png"));
			grapes = ImageIO.read(new File("src/textures/grapes.png"));
			galaxianFlagship = ImageIO.read(new File("src/textures/galaxianFlagship.png"));
			bell = ImageIO.read(new File("src/textures/bell.png"));
			key = ImageIO.read(new File("src/textures/key.png"));
			
			pacmanUP = ImageIO.read(new File("src/textures/pacmanUP.png"));
			pacmanDOWN = ImageIO.read(new File("src/textures/pacmanDOWN.png"));
			pacmanLEFT = ImageIO.read(new File("src/textures/pacmanLEFT.png"));
			pacmanRIGHT = ImageIO.read(new File("src/textures/pacmanRIGHT.png"));
			pacmanMouthClosed = ImageIO.read(new File("src/textures/pacmanMouthClosed.png"));

			blinky = ImageIO.read(new File("src/textures/blinky.png"));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	// Generic method to set an icon somewhere
	private void setIcon(int y, int x, BufferedImage img) {
		gridLabels[y][x].setIcon(new ImageIcon(img));
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(!game.isWon() || !game.getPacman().isDead()) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_UP -> {
					game.getPacman().setOrientation(Direction.UP);
				}
				case KeyEvent.VK_LEFT -> {
					game.getPacman().setOrientation(Direction.LEFT);
				}
				case KeyEvent.VK_RIGHT -> {
					game.getPacman().setOrientation(Direction.RIGHT);
				}
				case KeyEvent.VK_DOWN -> {
					game.getPacman().setOrientation(Direction.DOWN);
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
	public long squares_per_second_to_milliseconds() {
		return (long)(1000/SQUARES_PER_SECOND);
	}

}
