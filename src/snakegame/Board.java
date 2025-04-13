package snakegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ListFormat;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
public class Board extends JPanel implements ActionListener{

    private final int ALL_DOTS=900;
    private final int DOT_Size=10;
    private final int x[]=new int[ALL_DOTS];
    private final int y[]=new int[ALL_DOTS];
    private final int RANDOM_POSITION=29;
    private int apple_x;
    private int apple_y;
    private Image apple;
    private Image dot;
    private Image head;
    private Timer timer;
    private boolean leftDirection=false;
    private boolean rightDirection=true;
    private boolean upDirection=false;
    private boolean downDirection=false;
    private boolean inGame=true;
    
    
    
    private int dots;
    
    public Board() {
        
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(300,300));
        setFocusable(true);
        loadImages();
        initGame();
    }
    
    public void loadImages(){
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/apple.png"));
        apple=i1.getImage();
        ImageIcon i2=new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/dot.png"));
        dot=i2.getImage();
        ImageIcon i3=new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/head.png"));
        head=i3.getImage();
    }
    public  void initGame(){
        dots=3;
        for(int i=0;i<dots;i++){
            y[i]=50;
            x[i]=50 - i* DOT_Size;
        }
        
        locateApple();
        
        timer=new Timer(140, this);
        timer.start();
    }
    
    public void locateApple(){
        int r= (int)(Math.random()* RANDOM_POSITION);
        apple_x=r * DOT_Size;
        r= (int)(Math.random()* RANDOM_POSITION);
        apple_y=r * DOT_Size;
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(inGame){
            g.drawImage(apple,apple_x, apple_y, this);
            for(int i=0;i<dots;i++){
               if(i==0){
                   g.drawImage(head, x[i], y[i], this);
               }else{
                   g.drawImage(dot, x[i], y[i], this);
               }
            }
            Toolkit.getDefaultToolkit().sync();
        }else{
            gameOver(g);
        }
    }
    
    public void gameOver(Graphics g){
        String msg ="Game Over!";
        Font font = new Font("SAN_SERIF",Font.BOLD,14);
        FontMetrics metrices=getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (300 - metrices.stringWidth(msg))/2,300/2);
    }
    
    public void move(){
        for(int i=dots;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(leftDirection){
            x[0]=x[0]-DOT_Size;
        }
        if(rightDirection){
            x[0]=x[0]+DOT_Size;
        }
        if(upDirection){
            y[0]=y[0]-DOT_Size;
        }
        if(downDirection){
            y[0]=y[0]+DOT_Size;
        }
//        x[0] +=DOT_Size;
//        y[0] +=DOT_Size;
    }
    public void chekApple(){
        if((x[0]==apple_x) && (y[0]==apple_y)){
            dots++;
            locateApple();
        }
    }
    
    public void chekCollision(){
        for(int i=dots; i>0;i--){
            if((i>4) && (x[0]==x[i]) && (y[0]==y[i])){
                inGame=false;
            } 
        }
        if(y[0]>=300){
            inGame=false;
        }
        if(x[0]>=300){
            inGame=false;
        }
        if(x[0]<0){
            inGame=false;
        }
        if(y[0]<0){
            inGame=false;
        }
        
        if(!inGame){
            timer.stop();
        }
    }
    
    public void actionPerformed(ActionEvent ae){
        if(inGame){
            chekApple();
            chekCollision();
            move();
            repaint();
        }
    }
    
    public class TAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            int key=e.getKeyCode();
            if(key==KeyEvent.VK_LEFT && (!rightDirection)){
                leftDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_RIGHT && (!leftDirection)){
                rightDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_UP && (!downDirection)){
                upDirection=true;
                leftDirection=false;
                rightDirection=false;
            }
            if(key==KeyEvent.VK_DOWN && (!upDirection)){
                downDirection=true;
                leftDirection=false;
                rightDirection=false;
            }
        }
    }
}