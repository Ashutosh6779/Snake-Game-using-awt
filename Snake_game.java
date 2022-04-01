package snake_game;

import java.awt.EventQueue;
import javax.swing.JFrame;


public class Snake_game extends JFrame
{
    public Snake_game()
    {
        initUI();
    }
    private void initUI() 
    {
        add(new Board());
        setResizable(true);
        pack();
        
        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) 
    {
        EventQueue.invokeLater(()->{
            JFrame ex=new Snake_game();
            ex.setVisible(true);
        });
    }

   
}
