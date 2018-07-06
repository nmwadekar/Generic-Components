package nmw.core.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.JApplet;
import javax.swing.JFrame;

public class Bounce extends JApplet implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int x=300,y=500, j=1, bgX, bgY;
	
	Image backGround, nom;

	@Override
	public void paint(Graphics g) {
		
	super.paint(g);
//		while(true){
		
			/*try {
				
			URL imageURL = this.getClass().getResource("nom.jpg");
				
			Image nom = getImage(imageURL,"nom.jpg");
				
			} catch (Exception e1) {
				e1.printStackTrace();
			} */
			
//			g.draw3DRect(x, y-((j%20)*10), 50, 50, true);
//			g.fill3DRect(x, y-((j%20)*10), 50, 50, true);
		
			g.clearRect(0, 0, 500, 500);
		
			g.drawRoundRect(x, y, 50, 50, 50, 50);
			
			g.drawLine(0, 300+50, 500, 300+50);
			
			URL imageURL = this.getClass().getResource("mario_background.jpg");
			
			backGround = getImage(imageURL,"mario_background.jpg");
			
			g.drawImage(backGround,bgX,0,800, 400,this); 
			
			g.drawImage(backGround,(800+bgX),0,800+bgX, 400,this); 
			
//			g.clearRect(0, 0, 500, 500);
			
			imageURL = this.getClass().getResource("mario.jpg");
			
			nom = getImage(imageURL,"mario.jpg");
			
			g.drawImage(nom, x, y, 50, 50, this);

			
			/*try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
			
			
			
//			++j;
//		}
			
	}

	@Override
	public void init() {
		
//		super.init();
		
		setBackground(Color.WHITE);
		setForeground(Color.BLACK);
		
		setSize(500, 500);
		
		Thread t = new Thread(this);
		
		t.start();
		
		addMouseListener(new Jump());
		
		/*addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				System.out.println("############### MOUSE CLICKED #########");
				// TODO Auto-generated method stub
				
				jump();
			}
		});*/
	}

	private class Jump implements MouseListener, Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			boolean upward = true;
			
			int o = 1;
			
			for(int k=0; k<20; k++){
				
//				y = 300-((j%20)*10);
				
				y = 300 - Math.abs((j%20))*10;
				
//				z = Math.abs(10-(j%20));
				
				System.out.println(x +" = x, y = "+ y );
				
				System.out.println();
				
//				bgX = -((o%20)*10);
				
				repaint();
				
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				if(o==10)
					upward = false;
				
				if(upward)
					++o;
				else
					--o;
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("############### MOUSE CLICKED #########");
			// TODO Auto-generated method stub
			
			Jump j = new Jump();
			
			Thread t = new Thread(j);
			
			t.start();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

	public static void main(String[] args) {
		
		JFrame f = new JFrame ("BOUNCE");
		JApplet applet = new Bounce();
		applet.setSize(new Dimension(400,400));
         
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        f.getContentPane().add("Center", applet);
        applet.init();
        f.pack();
        f.setSize(new Dimension(400,400));
        f.setVisible(true);

	}
	
	private void jump(){
		
		for(int k=0; k<10; k++){
			
			y = 300-((j%20)*10);
			
//			y = 300 - Math.abs(10-(j%20))*10;
			
//			z = Math.abs(10-(j%20));
			
			System.out.println(x +" = x, y = "+ y );
			
			System.out.println();
			
//			bgX = -((j%20)*10);
			
			repaint();
			
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			++j;
		}
	}

	@Override
	public void run() {
		
//		int z = 0;
		
		while(true){
			
//			y = 300-((j%20)*10); //---this not uncomment
			
//			y = 300 - Math.abs(10-(j%20))*10; 	//this is correct
			
//			z = Math.abs(10-(j%20));
			
			System.out.println(x +" = x, y = "+ y );
			
			System.out.println();
			
			bgX = -((j%20)*10);
			
			repaint();
			
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			++j;
		}
		
	}

}
