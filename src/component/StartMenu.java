package component;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Tetris;

public class StartMenu extends JFrame {

	private Image screenImage;
	private Graphics screenGraphic;

	private Image tetrisBackground = new ImageIcon(Tetris.class.getResource("../images/tetrisBackground.png")).getImage();

	private ImageIcon menuStartClicked = new ImageIcon(Tetris.class.getResource("../images/menuStartClicked.png"));
	private ImageIcon menuStartBasic = new ImageIcon(Tetris.class.getResource("../images/menuStart.png"));
	private JButton menuStart = new JButton(menuStartBasic);

	private ImageIcon menuOptionClicked = new ImageIcon(Tetris.class.getResource("../images/menuOptionClicked.png"));
	private ImageIcon menuOptionBasic = new ImageIcon(Tetris.class.getResource("../images/menuOption.png"));
	private JButton menuOption = new JButton(menuOptionBasic);

	private ImageIcon menuSBClicked = new ImageIcon(Tetris.class.getResource("../images/menuSBClicked.png"));
	private ImageIcon menuSBBasic = new ImageIcon(Tetris.class.getResource("../images/menuSB.png"));
	private JButton menuSB = new JButton(menuSBBasic);

	private ImageIcon menuExitClicked = new ImageIcon(Tetris.class.getResource("../images/menuExitClicked.png"));
	private ImageIcon menuExitBasic = new ImageIcon(Tetris.class.getResource("../images/menuExit.png"));
	private JButton menuExit = new JButton(menuExitBasic);

	public StartMenu() {
		//basic
		//setUndecorated(true);
		JPanel imagepanel = new ImagePanel(tetrisBackground);
		add(imagepanel);
		setTitle("Tetris Game");
		setSize(380, 800);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		//setBackground(new Color(0, 0, 0, 0));
		setLayout(null);
		imagepanel.add(menuStart);
		imagepanel.add(menuOption);
		imagepanel.add(menuSB);
		imagepanel.add(menuExit);
		
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
				setVisible(false);
		        Tetris.start();
			}
		});
		
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
				setVisible(false);
		        Tetris.showSettingMenu();
			}
		});
		
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
				setVisible(false);
				Tetris.showScoreBoard();
			}
		});

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
	}
}

class ImagePanel extends JPanel {
	  private Image img;
	  
	  public ImagePanel(Image img) {
	      this.img = img;
	      setSize(new Dimension(img.getWidth(null), img.getHeight(null)));
	      setPreferredSize(new Dimension(img.getWidth(null), img.getHeight(null)));
	      setLayout(null);
	  }
	  
	  public void paintComponent(Graphics g) {
	      g.drawImage(img, 3, 0, null);
	  }
	}