package component;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.Tetris;

public class StartMenu extends JFrame {
	private Image screenImage;
	private Graphics screenGraphic;

	private Image tetrisBackground = new ImageIcon(Tetris.class.getResource("../images/tetrisBackground.png"))
			.getImage();

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

	private ImageIcon iconClicked = new ImageIcon(Tetris.class.getResource("../images/tetrisIconClicked.png"));
	private ImageIcon iconBasic = new ImageIcon(Tetris.class.getResource("../images/tetrisIcon.png"));
	private JButton icon = new JButton(iconBasic);

	public StartMenu() {
		// basic
		// setUndecorated(true);
		JPanel imagepanel = new ImagePanel(tetrisBackground);
		add(imagepanel);
		setTitle("Tetris Game");
		setSize(400, 800);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// setBackground(new Color(0, 0, 0, 0));
		setLayout(null);
		imagepanel.add(menuStart);
		imagepanel.add(menuOption);
		imagepanel.add(menuSB);
		imagepanel.add(menuExit);
		imagepanel.add(icon);
		setVisible(true);
		menuStart.requestFocus();
		// start menu
		menuStart.setBounds(130, 425, 130, 40);
		menuStart.setBorderPainted(false);
		menuStart.setContentAreaFilled(false);
		menuStart.setFocusPainted(false);
		menuStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				menuStart.setIcon(menuStartClicked);
				menuOption.setIcon(menuOptionBasic);
				menuSB.setIcon(menuSBBasic);
				menuExit.setIcon(menuExitBasic);
				menuStart.setCursor(new Cursor(HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				menuStart.setIcon(menuStartBasic);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				new Board();
				dispose();
			}
		});
		menuStart.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case 37:
					System.out.println("left");
					menuStart.setIcon(menuStartBasic);
					menuOption.setIcon(menuOptionClicked);
					menuSB.setIcon(menuSBBasic);
					menuExit.setIcon(menuExitBasic);
					break;
				case 38:
					System.out.println("up");
					menuStart.setIcon(menuStartClicked);
					menuOption.setIcon(menuOptionBasic);
					menuSB.setIcon(menuSBBasic);
					menuExit.setIcon(menuExitBasic);
					break;
				case 39:
					setFocusable(true);
					System.out.println("right");
					menuStart.setIcon(menuStartBasic);
					menuOption.setIcon(menuOptionBasic);
					menuSB.setIcon(menuSBClicked);
					menuExit.setIcon(menuExitBasic);
					break;
				case 40:
					System.out.println("down");
					menuStart.setIcon(menuStartBasic);
					menuOption.setIcon(menuOptionBasic);
					menuSB.setIcon(menuSBBasic);
					menuExit.setIcon(menuExitClicked);
					break;
				case KeyEvent.VK_ENTER:
					break;
				case KeyEvent.VK_SPACE:
					break;
				default:
					System.out.println("no!");
					JOptionPane alertNo = new JOptionPane();
					alertNo.showMessageDialog(
							null, "↑ : START 버튼 선택\n" + "← : OPTION 버튼 선택\n" + "→ : SCORE BOARD 버튼 선택\n"
									+ "↓ : EXIT 버튼 선택\n" + "Enter, Space Bar : 선택된 버튼 실행",
							"Key Reminder", JOptionPane.PLAIN_MESSAGE);
					break;
				}

				if ((e.getKeyCode() == KeyEvent.VK_ENTER) || (e.getKeyCode() == KeyEvent.VK_SPACE)) {
					if (menuStart.getIcon() == menuStartClicked) {
						new Board();
						dispose();
					}
					if (menuOption.getIcon() == menuOptionClicked) {
						;
					}
					if (menuSB.getIcon() == menuSBClicked) {
						try {
							new ScoreBoard(-1);
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						dispose();
					}
					if (menuExit.getIcon() == menuExitClicked) {
						System.exit(1);
					}
				}
			}
				
		});

		// options menu
		menuOption.setBounds(25, 500, 130, 40);
		menuOption.setBorderPainted(false);
		menuOption.setContentAreaFilled(false);
		menuOption.setFocusPainted(false);
		menuOption.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				menuStart.setIcon(menuStartBasic);
				menuOption.setIcon(menuOptionClicked);
				menuSB.setIcon(menuSBBasic);
				menuExit.setIcon(menuExitBasic);
				menuOption.setCursor(new Cursor(HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				menuOption.setIcon(menuOptionBasic);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
		});

		// tetrisIcon
		icon.setBounds(175, 500, 40, 40);
		icon.setBorderPainted(false);
		icon.setContentAreaFilled(false);
		icon.setFocusPainted(false);
		icon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				icon.setIcon(iconClicked);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				icon.setIcon(iconBasic);
			}
		});

		// score_board menu
		menuSB.setBounds(235, 500, 130, 40);
		menuSB.setBorderPainted(false);
		menuSB.setContentAreaFilled(false);
		menuSB.setFocusPainted(false);
		menuSB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				menuStart.setIcon(menuStartBasic);
				menuOption.setIcon(menuOptionBasic);
				menuSB.setIcon(menuSBClicked);
				menuExit.setIcon(menuExitBasic);
				menuSB.setCursor(new Cursor(HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				menuSB.setIcon(menuSBBasic);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				try {
					new ScoreBoard(-1);
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
			}

		});

		// exit menu
		menuExit.setBounds(130, 575, 130, 40);
		menuExit.setBorderPainted(false);
		menuExit.setContentAreaFilled(false);
		menuExit.setFocusPainted(false);
		menuExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				menuStart.setIcon(menuStartBasic);
				menuOption.setIcon(menuOptionBasic);
				menuSB.setIcon(menuSBBasic);
				menuExit.setIcon(menuExitClicked);
				menuExit.setCursor(new Cursor(HAND_CURSOR));
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
		screenImage = createImage(400, 800);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) {
		g.drawImage(tetrisBackground, 0, 0, null);
		paintComponents(g);
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
}