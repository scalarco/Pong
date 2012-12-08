/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Pong1p;

/**
 *Sean Calarco
 *Pong 1 player
 */

import java.io.*;
import java.net.*;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.swing.JOptionPane;

public class Pong1p extends JFrame implements MouseMotionListener 
{
	//Frame size
	final int WIDTH = 800, HEIGHT = 600;
	final int UP = 0, DOWN=1;
	int p1Direction=2, p2Direction=2;
	
	boolean p1win=false, p2win=false;
	
	Image background, ball, paddle1, paddle2;
	URL back, ballz, paddle;
    
    int p1y=300, p1x=20, p2y=300, p2x=780;
    int p1score=0, p2score=0;
    
    int mouseX, mouseY;
    
    Random Generator=new Random();
    int ballx=400;
    int bally=400;
    int	ballYfactor=Generator.nextInt(10)-5;
    int	ballXfactor=Generator.nextInt(10)-5;
    public Pong1p() 
    {
    	super("Pong");
    	setSize(WIDTH,HEIGHT);
    	setVisible(true);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	addMouseMotionListener(this);
    	try
    	{
    		back=this.getClass().getResource("gameboard2.jpg");
    		ballz=this.getClass().getResource("ball.PNG");
    		paddle=this.getClass().getResource("paddle.PNG");
    	}
    	catch(Exception e){}
    	
    	background=Toolkit.getDefaultToolkit().getImage(back);
    	ball=Toolkit.getDefaultToolkit().getImage(ballz);
    	paddle1=Toolkit.getDefaultToolkit().getImage(paddle);
    	paddle2=Toolkit.getDefaultToolkit().getImage(paddle);
    	MovePaddle2 p2=new MovePaddle2();
    	MoveBall b=new MoveBall();
    	p2.start();
    	b.start();
    }
    
    public void paint(Graphics g)
    {
    	Font scoreboard=new Font("SansSerif",Font.BOLD, 48);
    	g.drawImage(background,0,25,this);
    	g.drawImage(ball,ballx,bally,this);
    	g.drawImage(paddle1,p1x,mouseY,this);
    	g.drawImage(paddle2,p2x,p2y,this);
    	g.setColor(Color.blue);
    	g.setFont(scoreboard);
    	g.drawString("" +p1score,50,100);
    	g.drawString("" +p2score,700,100);
    	if(mouseY > 520)
        {
        	mouseY = 520;
         	repaint(); 
        }
    }
    
    public void mouseMoved(MouseEvent evt)  
    { 
        mouseX = evt.getX(); 
        mouseY = evt.getY(); 
        
    } 
   	
   	public void mouseClicked(MouseEvent evt) 
   	{
   	} 
   		
   	public void mouseDragged(MouseEvent evt)  
    { 
    } 
     	
    private class MovePaddle2 extends Thread 
    {
    	public void run()
    	{	
    		while(true)
    		{
    			try
    			{
    				//refresh screen
    				repaint();
    				
    				if(p2y+50>bally&&ballXfactor>0)
    				{
    					p2y-=28;
    					if(p2y<=25)
    						p2y=25;
    					repaint();
    				}
    				if(p2y+50<bally&&ballXfactor>0)
    				{
     					p2y+=28;
     					repaint();
     					if(p2y>520)
     						p2y=520;
     					repaint();
    				}
    				
    				//Delays refresh rate
    				Thread.sleep(60);
    			}
    			
    			catch(Exception e)
    			{
    				break;
    			}
    		}
    	}	
    }
    
    private class MoveBall extends Thread implements KeyListener
    {
    	public void run()
    	{
    		//wake keyListeners
    		addKeyListener(this);
    		JOptionPane.showMessageDialog(null, "Welcome to Pong.\nUse the mouse to control"
    			+" your paddle.\nScore 5 points to win.");
    		
    		while(true)
    		{
    			try
    			{
    				//refresh screen
    				repaint();
    				
    				//Ball must have a slope
    				if(ballXfactor==0)
    					ballXfactor=Generator.nextInt(10)-5;
    				if(ballYfactor==0)
    					ballYfactor=Generator.nextInt(10)-5;
    				
    				//Move ball
    				if(ballXfactor>0)
    					ballx+=ballXfactor;
    				else if(ballXfactor<0)
    					ballx+=ballXfactor;
    				if(ballYfactor>0)
    					bally+=ballYfactor;
    				else if(ballYfactor<0)
    					bally+=ballYfactor;
    				
    				//If ball collides with top
    				if(bally<=35&&ballXfactor>0)
    				{
    					ballYfactor*=-1;
    				}
    				else if(bally<=35&&ballXfactor<0)
    				{
    					ballYfactor*=-1;
    				}
    				
    				//If ball collides with bottom
    				if(bally>=590&&ballXfactor>0)
    				{
    					ballYfactor*=-1;
    				}
    				else if(bally>=590&&ballXfactor<0)
    				{
    					ballYfactor*=-1;
    				}
    				
    				//If ball collides with paddle 1
    				if(ballx>=p1x&&ballx<=(p1x+10)&&bally>=(mouseY-29)&&bally<=(mouseY+100))
    				{
     					ballXfactor*=-1;
     					if(ballXfactor<10)
     					{   
     						ballXfactor*=1.5;
     						if(ballXfactor>10)
     							ballXfactor=10;
     					}
     					if(ballYfactor<10&&ballYfactor>-10)
     					{
     						ballYfactor*=1.5;
     						if(ballYfactor>10)
     							ballYfactor=10;
     						if(ballYfactor<-10)
     							ballYfactor=-10;
     					}
     					if(ballXfactor<=2&&ballXfactor>=-2)
     						ballXfactor*=2;
     					if(ballYfactor<=2&&ballYfactor>=-2)
     						ballYfactor*=2;
    				}
    				
    				if(((ballx<=p1x+10&&ballx>=p1x)||(ballx+29<=p1x+10&&ballx+29>=p1x))&&(mouseY>=bally+19&&mouseY<=bally+29))
    				{
    					ballYfactor=-5;
    					ballXfactor=-5;
    				}
    				if(((ballx<=p1x+10&&ballx>=p1x)||(ballx+29<=p1x+10&&ballx+29>=p1x))&&(mouseY+100>=bally&&mouseY+100<=bally+10))
    				{
    					ballYfactor=5;
    					ballXfactor=-5;
    				}
    				
    				//If ball collides with paddle 2
    				if((ballx+29)>=p2x&&(ballx+29)<=(p2x+10)&&(bally+29)>=p2y&&bally<=(p2y+105))
    				{
     					ballXfactor*=-1;
     					if(ballXfactor>-10)
     					{
     						ballXfactor*=1.5;
     						if(ballXfactor<-10)
     							ballXfactor=-10;
     					}
     					if(ballYfactor<10&&ballYfactor>-10)
     					{
     						ballYfactor*=1.5;
     						if(ballYfactor>10)
     							ballYfactor=10;
     						if(ballYfactor<-10)
     							ballYfactor=-10;
     					}
         				if(ballXfactor<=2&&ballXfactor>=-2)
     						ballXfactor*=2;
     					if(ballYfactor<=2&&ballYfactor>=-2)
     						ballYfactor*=2;
 
    				}
    				
    				//If player 2 scores
    				if((ballx+29)<=0)
    				{
    					ballx=400;
    					bally=400;
    					ballYfactor=Generator.nextInt(10)-5;
    					ballXfactor=Generator.nextInt(10)-5;
    					p2score++;
    				}
    				
    				//If player 1 scores
    				if(ballx>=800)
    				{
    					ballx=400;
    					bally=400;
    					ballYfactor=Generator.nextInt(10)-5;
    					ballXfactor=Generator.nextInt(10)-5;
    					p1score++;
    				}
    				
    				if(p1score==5)
    				{
    					p1win=true;
    					JOptionPane.showMessageDialog(null, "Player 1 wins");
    					System.exit(0);
    				}
    				if(p2score==5)
    				{
    					p2win=true;
    					JOptionPane.showMessageDialog(null, "Player 2 wins");
    					System.exit(0);
    				}
    				
    				
    				//Delays refresh rate
    				Thread.sleep(19);
    				
    				if(p1win==true)
    					break;
   					if(p2win==true)
    					break;
    					
    			}
    			
    			catch(Exception e)
    			{
    				break;
    			}
    		}
    	}
		public void keyPressed(KeyEvent e)
    	{}
    
   		public void keyReleased(KeyEvent e)
    	{}
    	
    	public void keyTyped(KeyEvent e)
    	{
    		if(e.getKeyChar() == 'h')
    		{
    			ballYfactor*=1.5;
    			ballXfactor*=1.5;
    		}
    		if(e.getKeyChar()=='g')
    		{
    			ballYfactor*=.5;
    			ballXfactor*=.5;
    		}	
    	}
    }
    
    public static void main(String[] args)
    {
    	Pong1p pong=new Pong1p();
    }
    
}