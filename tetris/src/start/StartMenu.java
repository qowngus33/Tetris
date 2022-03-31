package start;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import component.Board;
import main.Tetris;

public class StartMenu extends JFrame {

	private Image screenImage;
	private Graphics screenGraphic;

	private Image tetrisBackground = new ImageIcon(Tetris.class.getResource("../Images/tetrisBackground.png"))
			.getImage();

	private ImageIcon menuStartClicked = new ImageIcon(Tetris.class.getResource("../Images/menuStartClicked.png"));
	private ImageIcon menuStartBasic = new ImageIcon(Tetris.class.getResource("../Images/menuStart.png"));
	private JButton menuStart = new JButton(menuStartBasic);

	private ImageIcon menuOptionClicked = new ImageIcon(Tetris.class.getResource("../Images/menuOptionClicked.png"));
	private ImageIcon menuOptionBasic = new ImageIcon(Tetris.class.getResource("../Images/menuOption.png"));
	private JButton menuOption = new JButton(menuOptionBasic);
	
	private ImageIcon menuSBClicked = new ImageIcon(Tetris.class.getResource("../Images/menuSBClicked.png"));
	private ImageIcon menuSBBasic = new ImageIcon(Tetris.class.getResource("../Images/menuSB.png"));
	private JButton menuSB = new JButton(menuSBBasic);
	
	private ImageIcon menuExitClicked = new ImageIcon(Tetris.class.getResource("../Images/menuExitClicked.png"));
	private ImageIcon menuExitBasic = new ImageIcon(Tetris.class.getResource("../Images/menuExit.png"));
	private JButton menuExit = new JButton(menuExitBasic);

	public StartMenu() {
		//basic
		setUndecorated(true);
		setTitle("Tetris Game");
		setSize(380, 800);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setBackground(new Color(0, 0, 0, 0));
		setLayout(null);
 
		//start menu
		menuStart.setBounds(125, 425, 130, 40);
		menuStart.setBorderPainted(false);
		menuStart.setContentAreaFilled(false);
		menuStart.setFocusPainted(false);
		menuStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				menuStart.setIcon(menuStartClicked);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				menuStart.setIcon(menuStartBasic);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Board main = new Board();
				main.setSize(380, 800);
				main.setResizable(false);
				setVisible(false);
				main.setVisible(true);
				
			}
						
		});
		add(menuStart);
		
		//options menu
		menuOption.setBounds(125, 475, 130, 40);
		menuOption.setBorderPainted(false);
		menuOption.setContentAreaFilled(false);
		menuOption.setFocusPainted(false);
		menuOption.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				menuOption.setIcon(menuOptionClicked);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				menuOption.setIcon(menuOptionBasic);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(1);
			}
		});
		add(menuOption);
		
		//score_board menu
		menuSB.setBounds(125, 525, 130, 40);
		menuSB.setBorderPainted(false);
		menuSB.setContentAreaFilled(false);
		menuSB.setFocusPainted(false);
		menuSB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				menuSB.setIcon(menuSBClicked);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				menuSB.setIcon(menuSBBasic);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(1);
			}
		});
		add(menuSB);
		
		//exit menu
		menuExit.setBounds(125, 575, 130, 40);
		menuExit.setBorderPainted(false);
		menuExit.setContentAreaFilled(false);
		menuExit.setFocusPainted(false);
		menuExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				menuExit.setIcon(menuExitClicked);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				menuExit.setIcon(menuExitBasic);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(1);
			}
		});
		add(menuExit);

	}

	public void paint(Graphics g) {
		screenImage = createImage(380, 800);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) {
		g.drawImage(tetrisBackground, 0, 0, null);
		paintComponents(g);
		this.repaint();
	}

}
