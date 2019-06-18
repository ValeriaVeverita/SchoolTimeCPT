import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import java.awt.Font;

public class SplashScreen {

	private JFrame frmSplashScreen;
	JLabel picIcon ;
	private JLabel picPen2;
	private JLabel lblTitle;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SplashScreen window = new SplashScreen();
					window.frmSplashScreen.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SplashScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSplashScreen = new JFrame();
		frmSplashScreen.setBounds(300, 300, 400, 700);
		frmSplashScreen.getContentPane().setBackground(new Color(1, 60, 83));
		frmSplashScreen.getContentPane().setLayout(null);
		
		picIcon = new JLabel("New label");
		picIcon.setVerticalAlignment(SwingConstants.TOP);
		picIcon.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\ICS4U-CPT\\SchoolTimeCPT\\icon.png"));
		picIcon.setBounds(40, 86, 155, 155);
		
		JLabel picPen1 = new JLabel("");
		picPen1.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\ICS4U-CPT\\SchoolTimeCPT\\pencil.png"));
		picPen1.setBounds(0, 70, 210, 40);
		
		frmSplashScreen.getContentPane().add(picPen1);
		
		picPen2 = new JLabel("");
		picPen2.setBounds(0, 230, 210, 40);
		frmSplashScreen.getContentPane().add(picPen2);
		frmSplashScreen.getContentPane().add(picIcon);
		frmSplashScreen.setTitle("School Time");
		frmSplashScreen.setBounds(100, 100, 246, 366);
		frmSplashScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		//ICON
		//set the image
		ImageIcon imageIcon = new ImageIcon(new ImageIcon("icon.png").getImage().getScaledInstance(155, 155, Image.SCALE_DEFAULT));
		//get the height of the icon
		int height = picIcon.getHeight();
		IconObj icon = new IconObj(imageIcon,picIcon, height);
		
		//PENCIL 1
		//set the image
		ImageIcon imagePen = new ImageIcon(new ImageIcon("pencil.png").getImage().getScaledInstance(230, 130, Image.SCALE_DEFAULT));
		//get x and y position
		int xPos = picPen1.getX();
		int yPos = picPen1.getY();
		PenObj pen1 = new PenObj(imagePen,picPen1, xPos, yPos, 0);
		
		//PENCIL 2
		//get x and y position
		xPos = picPen2.getX();
		int expectY = picPen2.getY();
		yPos = picPen1.getY()+14;
		PenObj pen2 = new PenObj(imagePen,picPen2, xPos, yPos, expectY);
		
		lblTitle = new JLabel("School Time");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 23));
		lblTitle.setForeground(new Color(204, 51, 0));
		lblTitle.setBounds(40, 269, 148, 35);
		lblTitle.setVisible(false);
		frmSplashScreen.getContentPane().add(lblTitle);
		
		//run the functions 500 miliseconds after the app was launched
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		                // your code here	
		            	multiRun(icon, pen2); 
		            	//display the text
		            	lblTitle.setVisible(true);
		            }
		        }, 
		        500 
		);
		
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		                // display the calendar screen	
		            	Calendar calendar = new Calendar();
		            	calendar.setVisible(true);
		            }
		        }, 
		        3000 
		);
		
	}
	
	////////////////////////////////////////////////////////////////////////////////
	//F_U_N_C_T_I_O_N_S
	/////////////////////////////////////////////////////////////////////////////////
	
	//this functions has two threads that will run our two methods simultaneously
	public void multiRun(IconObj icon, PenObj pen) {
		new Thread() {
	    public void run() {
	    	pen.movePencil(5);   
			}
		}.start();
	 
		new Thread() {
	    	public void run() {
	    	icon.increaseSize();   
			}
		}.start();
	 
	}
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//IconObject Class
class IconObj 
{
	//local variables
	static int FINALH;
	int beginHeight = 0;
	JLabel iconPic;
	
	//constructer
	public IconObj (ImageIcon img, JLabel iconPic, int height)
	{
		//set the image of the jLabel and initial height
		this.iconPic = iconPic;
		this.iconPic.setIcon(img);
		this.iconPic.setBounds(35, 86, 155, beginHeight);
		//get and set the variables
		this.FINALH = height;
	}
	
	//this function makes the icon appear over time
	public void increaseSize()
	{
		while (beginHeight < FINALH)
		{
			beginHeight += 5;
			this.iconPic.setBounds(40, 86, 155, beginHeight);
			try {
				Thread.sleep(70);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//IconObject Class
class PenObj 
{
	//local variables
	JLabel penPic;
	int yPos;
	int expectY;
	int xPos;
	
	//constructer
	public PenObj(ImageIcon img, JLabel iconPic, int xPos, int yPos, int expecY)
	{
		//set the image of the jLabel and initial height
		this.penPic = iconPic;
		this.penPic.setIcon(img);
		//set the x and y values
		this.yPos = yPos;
		this.xPos = xPos; 
		this.penPic.setLocation(xPos, yPos);
		
		//get the final position of y
		this.expectY = expecY;
		
	}
	
	//this function will move the pencil 
	public void movePencil(int velocity)
	{
		while (penPic.getY() < expectY) {
			yPos += velocity;
			penPic.setLocation(xPos, yPos);
			try {
				Thread.sleep(70);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
