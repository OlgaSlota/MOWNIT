package ex2;

import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.io.PrintWriter;
import java.util.Random;


public class Image extends Applet implements Runnable {

	int n ;
	Random rand = new Random();
	double ro ;
	Thread runner;
	double temp, T0 ;
	boolean[][] currentImg ;
	boolean[][] initialImg;
	double total ;
	boolean done = false;
	int []ind= new int[40];
	
	
	public void start(){
		runner= new Thread(this);
		runner.start();
		}
	
	public void stop(){
		runner=null;
	}
	public void init (){
		this.setSize(new Dimension(900,900));
	}

	public boolean diagonalNeighbours(int x,int y, int x1,int y1){
		if(Math.abs(x-x1)==Math.abs(y-y1) && y!=y1)
			return true;
		return false;
		
	}
	public boolean crossNeighbours(int x,int y, int x1,int y1){
		if( y==y1||x==x1) 
			return true;
		return false;
		
	}
	public boolean eightNeighbours(int x,int y, int x1,int y1){
		if(crossNeighbours(x,y,x1,y1)|| diagonalNeighbours(x,y,x1,y1))
			return true;
		return false;
		
	}
	
	
	public int unitEnergy(boolean[][]img, int a, int b, int c, int d ){

		int e=0;
		if((img[a][b]&&img[c][d]) && (a!=c || b!=d))
			e =-20;
		else e= 10;
		return e;
	}
	
	
	
	public int totalEnergy(boolean [][] img){
		int sum =0;
		for(int i=0; i<n ; i++)
			for(int j=0; j<n; j++){
				int k= i-5;
				if(k<0)
					k=0;
				while(k<i+5 && k<n){
					int l= j-5;
					if(l<0)
						l=0;
					while(l<j+5 && l<n){
						int e= 0;
						if (eightNeighbours(i,j,k,l))
							 e= unitEnergy(img,i,j,k,l);
						sum+= e;
						l++;
					}
					k++;
				}	
			}
		return sum;
	}
	
	
	public int updateEnergy(boolean[][] newImg ){
      int sum =0;
	  for(int j=0;j<10;j++){ // 10 swapped pairs of points in each getNextImg
		int x1=ind[j] , y1 = ind[j+1]; // first point
		int x2 = ind[j+10], y2 = ind[j+11]; // second point
		
		int k= x1-5;
		if(k<0)
			k=0;
		while(k<x1+5 && k<n){
			int l= y1-5;
			if(l<0)
				l=0;
			while(l<y1+5 && l<n){
				int e1 =0, e= 0;
				if (eightNeighbours(x1,y1,k,l)){
					 e= unitEnergy(newImg,x1,y1,k,l);
					 e1 = unitEnergy(currentImg,x1,y1,k,l);
				}
				sum += e ;
				sum -= e1;
				
				l++;
			}
			k++;
		}	
		k= x2-5;
			if(k<0)
				k=0;
			while(k<x2+5 && k<n){
				int l= y2-5;
				if(l<0)
					l=0;
				while(l<y2+5 && l<n){
					double e1 =0, e= 0;
					if (eightNeighbours(x2,y2,k,l)){
						 e= unitEnergy(newImg,x2,y2,k,l);
						 e1 = unitEnergy(currentImg,x2,y2,k,l);
					}
					sum += e ;
					sum -= e1;
					l++;
				}
				k++;
			}	
	  }
	return sum;	
	}
	
	public boolean[][]getNextImg(boolean [][]current){
		
		boolean [][] next =new boolean[n][n];
		for(int i =0; i<n ; i++)
			for(int j=0; j<n; j++)
				next[i][j]=new Boolean(current[i][j]);
		
		// swapping four random points 
		
		for(int j=0;j<22;j++)
			ind[j]= rand.nextInt(n);
		boolean tmp = false;
		for(int j=0;j<10;j++){
			tmp =new Boolean(next[ind[j]][ind[j+1]]);
			next[ind[j]][ind[j+1]]=next[ind[j+10]][ind[j+11]];
			next[ind[j+10]][ind[j+11]]= tmp;
		}
		return next;
	}
	
	public boolean[][] createImage(){
		boolean[][] img = new boolean[n][n];
		int x,y,el=0;
		for(int i=0;i<n; i++)
			for (int j=0 ; j<n ;j++)
				img[i][j]=false;
		
		while( el<n*n*ro){
			 x = rand.nextInt(n);
			 y= rand.nextInt(n);
			 if(!img[x][y]){
				 img[x][y]=true;
				 el++;
			 }
		}			
		return img;
	}
	
	public int anneal(boolean[][] image, double T0)
	{
		boolean [][] nextImg;
	    int iteration = -1;
	    int counter=0;
	    temp=T0;
	    int deltaEnergy = 0;
	    double coolingRate = 0.9999;
	    double absoluteTemperature = 0.01;

	    int energy = totalEnergy(currentImg);
	    try{
	    	PrintWriter w = new PrintWriter("C:/Users/Suota/PycharmProjects/Mownit2/mownitZad4/energies.txt");
	    	w.println(energy);
	    	
	    while (temp > absoluteTemperature && counter <100)
	    {
	    	nextImg = getNextImg(currentImg);

	       deltaEnergy = updateEnergy(nextImg);

	       if(deltaEnergy <1)
	    	   counter++;
	       else counter =0;
	       
	        if ((deltaEnergy < 0) || (deltaEnergy > 0 && 
	             Math.exp(-deltaEnergy / temp) > rand.nextDouble()))
	        {
	            for (int i = 0; i < n; i++)
	            	 for (int j = 0; j < n; j++)
	            		currentImg[i][j] =new Boolean(nextImg[i][j]);

	            energy = deltaEnergy + energy;
	            w.println(energy);
	        }

	        temp *= coolingRate;

	        iteration++;
	    }
	    System.out.println("\niterations : "+iteration);
		done = true;  
		if(temp > absoluteTemperature )
			System.out.println("stopped because of no energy changes " );
		w.close();
	    }catch(Exception e ){
	    	e.printStackTrace();
	    }
	   return energy;
	}
	
	@Override
	public void run() {
			n = 300 ;
			T0 = 700;
			ro = 0.1;
			currentImg = createImage();
			initialImg= new boolean[n][n];	
			 for (int i = 0; i < n; i++)
            	 for (int j = 0; j < n; j++)
            		initialImg[i][j] =new Boolean(currentImg[i][j]);
			
			total= anneal(currentImg, T0);	
			System.out.println(total);
			
			try{Thread.sleep(200);
			}
			catch (InterruptedException exc){
				System.out.println("sleep exeption");
			}
			
			repaint();
			stop();
		}
	
	 public void paint(Graphics g) {
	        int rad=1,shift=50;
	        g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 

	       if(done){
	     
	        for(int i=0;i<n;i++)
	        	for(int j=0 ; j<n ; j++){
	        		if(currentImg[i][j])
	        			g.fillOval((shift+i),(shift+j),rad,rad);
	        	}
	      g.drawString("After ",shift-10,shift);
	       }
	       else 
	    	   g.drawString("Computation in progress..",shift+100,shift+100);
	      g.drawString("Total energy= "+total,100,850);
	      g.drawString("initial temp = "+T0+" current temp = " + temp,100,880);
	
	     }

}
