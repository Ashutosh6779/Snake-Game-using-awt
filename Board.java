
package snake_game;

import java.awt.Color;                      //Used for colors
import java.awt.Dimension;                  //it contains height and width of the component in integer form
import java.awt.Font;                       //Used for displayig various kinds of fonts in the program
import java.awt.FontMetrics;                //Used to initialize the size of the fonts
import java.awt.Graphics;                   //Used to Draw something on console
import java.awt.Image;                      //Used for manipulation of image
import java.awt.event.ActionListener;       //Used for handling action events like when user clicks...
import java.awt.event.ActionEvent;          //Used for creating the event
import java.awt.event.KeyAdapter;           //Used for taking inputs from keyboard
import java.awt.event.KeyEvent;             //Used to indicate if the key has been pressed
import javax.swing.ImageIcon;               //Used to load images
import javax.swing.JPanel;                  //Used  for Organizing components and various layouts
import javax.swing.Timer;                   //Used for providing delay in between the multiple action event

public class Board extends JPanel implements ActionListener
{
    //Diemensions
    private final int height=300;
    private final int breadth=300;
    private final int dot_size=10;
    private final int all_dot=900;
    private final int rand_pos=29;
    private final int delay=140;
    private final int arr1[]=new int[all_dot];
    private final int arr2[]=new int[all_dot];
    
    private int dots;
    private int apple_x;
    private int apple_y;
    
    //Direction
    private boolean Left_pos=false;
    private boolean Right_pos=true;
    private boolean up_pos=false;
    private boolean down_pos=false;
    private boolean inGame=true;
    
    //Images
    private Timer timer;
    private Image apple;
    private Image ball;
    private Image head;
    
    //Setting Board
    public Board()
    {
        initBoard();
    }
    private void initBoard()
    {
        addKeyListener(new TAdapter());
        setBackground(Color.blue);
        setFocusable(true);
        
        setPreferredSize(new Dimension(height,breadth));
        loadImages();
        initGame();
    }
    
    //Loading of all images wes see in screen 
    private void loadImages()
    {
        ImageIcon apple_img=new ImageIcon("C:\\Users\\HP\\Pictures\\Saved Pictures\\apple.png");
        ImageIcon dot_img=new ImageIcon("C:\\Users\\HP\\Pictures\\Saved Pictures\\dot.png");
        ImageIcon head_img=new ImageIcon("C:\\Users\\HP\\Pictures\\Saved Pictures\\head.png");
        apple=apple_img.getImage();
        ball=dot_img.getImage();
        head=head_img.getImage();
    }
    
    //creating initial 3 dots of snake in screen
    private void initGame()
    {
        dots=3;
        for(int z=0;z<dots;z++)
        {
            arr1[z]=50-z*10;
            arr2[z]=50;
        }
        locateApple();
        timer=new Timer(delay,this);
        timer.start();
    }
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        doDrawing(g);   
    }
    
    
    public void doDrawing(Graphics g)
    {
        if(inGame)
        {
            g.drawImage(apple,apple_x, apple_y, this);
            for(int z=0;z<dots;z++)
            {
                if(z==0)
                {
                    g.drawImage(head, arr1[z],arr2[z], this);
                }
                else
                {
                    g.drawImage(ball, arr1[z], arr2[z], this);
                }
            }
        }
        else
        {
            gameOver(g);
        }
    }
    public void gameOver(Graphics g)
    {
        String msg="Game Over";
        Font small=new Font("Helvetica",Font.BOLD,14);
        FontMetrics metr=getFontMetrics(small);
        
        g.setColor(Color.red);
        g.setFont(small);
        g.drawString(msg,(breadth-metr.stringWidth(msg))/2,height/2);
    }
    private void checkApple()
    {
        if((arr1[0]==apple_x )&& (arr2[0]==apple_y))
        {
            dots++;
            locateApple();
        }
    }
    private void move()
    {
        for(int z=dots;z>0;z--)
        {
            arr1[z]=arr1[(z-1)];
            arr2[z]=arr2[(z-1)];
        }
        if(Left_pos)
        {
            arr1[0]-=dot_size;
        }
        if(Right_pos)
        {
            arr1[0]+=dot_size;
        }
        if(up_pos)
        {
            arr2[0]-=dot_size;
        }
        if(down_pos)
        {
            arr2[0]+=dot_size;
        }
    }
    private void checkCollision()
    {
        for(int z=dots;z>0;z--)
        {
            if((z>4)&&(arr1[0]==arr1[z])&&(arr2[0]==arr2[z]))
            {
                inGame=false;
            }
            if(arr2[0]>=height)
            {
                inGame=false;
            }
            if(arr2[0]<0)
            {
                inGame=false;
            }
            if(arr1[0]>=breadth)
            {
                inGame=false;
            }
            if(arr1[0]<0)
            {
                inGame=false;
            }
            if(!inGame)
            {
                timer.stop();
            }
        }    
    }
    
    //positions the apple at random places
    private void locateApple()
    {
        int r=(int)(Math.random()*rand_pos);
        apple_x=((r*dot_size));
        
        r=(int)(Math.random()*rand_pos);
        apple_y=((r*dot_size));
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(inGame)
        {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }
    private class TAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            int key=e.getKeyCode();
            
            if((key==KeyEvent.VK_LEFT)&&(!Right_pos))
            {
                Left_pos=true;
                up_pos=false;
                down_pos=false;
            }
            if((key==KeyEvent.VK_RIGHT)&&(!Left_pos))
            {
                Right_pos=true;
                up_pos=false;
                down_pos=false;
            }
            if((key==KeyEvent.VK_DOWN)&&(!up_pos))
            {
                Left_pos=false;
                Right_pos=false;
                down_pos=true;
            }
            if((key==KeyEvent.VK_UP)&&(!down_pos))
            {
                Left_pos=false;
                up_pos=true;
                Right_pos=false;
            }
        }
    }
}
