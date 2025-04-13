package snakegame;

import javax.swing.JFrame;

public class SnakeGame extends JFrame{
    public SnakeGame() {
        super("Snake Game");
        add(new Board());
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        
    }
    
    public static void main(String[] args) {
       new SnakeGame().setVisible(true);
    }
    
}
