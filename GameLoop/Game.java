package GameLoop;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import ChessObjects.Square;


public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = -2947324632374366433L;
	private boolean running = false;
	private Thread thread;
	private Color c1 = Color.WHITE;
	private Color c2 = Color.gray;
	Handler handler;
	MouseHandler mouse;
	
	public Game () {
		mouse = new MouseHandler();
		addMouseListener(mouse);
		
	}
	public synchronized void start() 
	{
		if(running)
			return;
		//GameChecks.TURN = true;
		running  = true; 
		thread = new Thread(this);
		initHandler();
		thread.start();
		//run();
		
		
		
	}
	// add Squares to handler 
	public void initHandler() 
	{
		
		Color temp;
		handler = new Handler();
		for(int i = 0; i < 8; i++) 
		{
			temp = (i % 2 == 0)? c1:c2;
			for(int j = 0; j < 8; j++) 
			{
				try 
				{ 
					handler.addSquare(new Square(j, i, temp));
					temp = (temp.equals(c1))? c2:c1;
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
		handler.initPieceHolders();
	}
	
	public void run() 
	{
		int oldX = 0;
		int oldY = 0;
		int updatedX = 0;
		int updatedY = 0;
		int count = 0; 
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(running){
			
			if(GameChecks.COMPUTER && GameChecks.TURN) {
				
				handler.computerMove();	
			
			}
			if(mouse.getClicked() && (!GameChecks.TURN || !GameChecks.COMPUTER))
			{
				
				if(count == 0) 
				{
					int[] move = mouse.getClickedCoordinates();
					oldX = move[0];
					oldY = move[1];	
					count++;
					
				}
				else 
				{
					int[] move = mouse.getClickedCoordinates();
					updatedX = move[0];
					updatedY = move[1];	
					count = 0;
					try {
						handler.move(oldX, oldY, updatedX, updatedY);
					} catch (Exception e) {
					}
					if(GameChecks.CHECKMATE) {
						running = false;
					}
					else if(GameChecks.STALEMATE) {
						running = false;
					}
					
				}
				
				
				mouse.resetClick();
			}
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
					
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
		render();
		
		try {
			TimeUnit.MILLISECONDS.sleep(10);
			GameChecks.GAMEOVER = true;
		} catch (InterruptedException e) {
		
			e.printStackTrace();
		}
	}
	
	
	private void tick() 
	{
		handler.tick();
	}
	
	private void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(2);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		
		
		// DRAW in board and animations
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		handler.render(g);
		//
		g.dispose();
		bs.show();
	}
	
	public void setMouse(MouseHandler mouse) {
		this.mouse = mouse;
	}
	public boolean isRunning() {
		return running;
	}
	
	public static void main(String[] args)
	{
		new Window(804,804, "Chess");
	}
}



