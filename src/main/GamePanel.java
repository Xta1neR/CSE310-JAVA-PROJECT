package main;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import entity.Player;
import tile.TileManager;


public class GamePanel extends JPanel implements Runnable{
    
    // screen settings 
    final int originalTileSize = 16; //16x16 characters
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; //48x48 tile
    public final int maxScreenCol = 15;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize*maxScreenCol; //768 pixels
    public final int ScreenHeight = tileSize*maxScreenRow; //576 pixels
    
    //world Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    
    //FPS
    int FPS = 45;
    
    TileManager tileM = new TileManager(this);
    
    KeyHandler KeyH = new KeyHandler();

    Thread gameThread;
    
    public Player player = new Player(this,KeyH);
    
    // Set player's Location 
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;
    
    
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, ScreenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(KeyH);
        this.setFocusable(true);

    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){
    	
    	double drawInterval = 1000000000/FPS; 
    	double nextDrawTime = System.nanoTime()+drawInterval;    	
    	
        while (gameThread != null){
            
        	//long currentTime = System.nanoTime();
        	//System.out.println(currentTime);
        	
        	//System.out.println("Running");
        	
        	//1. UPDTE information such as character position.
        	update();
        	
        	
        	//2. DRAW the screen with updated information.
        	repaint();
        	     
        	
        	try {
        		double remainingTime = nextDrawTime - System.nanoTime();
            	remainingTime = remainingTime/1000000;
            	
            	if(remainingTime < 0) {
            		remainingTime = 0;
            	}
        		
        		Thread.sleep((long) remainingTime);
        		
        		nextDrawTime += drawInterval;
			} 
        	catch (InterruptedException e) {
				
				e.printStackTrace();
			}
        }
    }
    
    public void update() {
    	player.update();
    }
    
    
    public void paintComponent(Graphics g) {
    	 
    	super.paintComponent(g);
    	
    	Graphics2D g2 = (Graphics2D)g;
    	
    	tileM.draw(g2);
    	player.draw(g2);
    	g2.dispose();
    }
        
}




