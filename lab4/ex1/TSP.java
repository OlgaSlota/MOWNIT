package ex1;

import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.PrintWriter;

import org.apache.commons.math3.distribution.NormalDistribution;
import java.util.Random;

public class TSP extends Applet implements Runnable {
	int n;
	int iteration ;
	Random rand = new Random();
	boolean ready ;
	Thread runner;
	Point[] points;
	Point[] currentOrder ;
	double total ;
	double T0;
	double temp;
	public void start(){
		runner= new Thread(this);
		runner.start();
		}
	
	public void stop(){
		runner=null;
	}
	public void init (){
		this.setSize(new Dimension(800,800));
	}
	public double anneal(Point[] points ,double T0)
	{
		double[][] d = prepareDistances(points);
		
		Point[] nextOrder;
	    iteration = 0;

	    temp=T0;
	    double deltaDistance = 0;
	    double coolingRate = 0.99;
	    double absoluteTemperature = 0.01;
	    
	    double distance = getTotalDistance(currentOrder, d);
	    System.out.println("First computed distance= "+distance);
	    
	    try{
	    	PrintWriter w = new PrintWriter("C:/Users/Suota/PycharmProjects/Mownit2/mownitZad4/distances.txt");
	    	w.println(distance);
	    	
	    	 while (temp > absoluteTemperature) {
	    		 
	 	        nextOrder = getNextOrderConsecutive(currentOrder);

	 	        deltaDistance = getTotalDistance(nextOrder, d) - distance;

	 	        if ((deltaDistance < 0) || (distance > 0 && 
	 	             Math.exp(-deltaDistance / temp) > rand.nextDouble())){
	 	             for (int i = 0; i < nextOrder.length; i++)
	 	                currentOrder[i] = new Point(nextOrder[i].x,nextOrder[i].y,nextOrder[i].index);

	 	             distance = deltaDistance + distance;
	 	             w.println(distance);
	 	             }
	 	        
	 	        temp *= coolingRate;

	 	        iteration++;
	 	    }

	 	    ready = true;
	 	   System.out.println("iterations : "+iteration+"\nFinal order: ");
	 	   for (int i = 0; i < currentOrder.length; i++)
	           System.out.print( currentOrder[i].index+ "  " );
	 	   System.out.println("\n Final distance = "+distance);
	 	   w.close();
	 	  
	    }catch(Exception e ){
	    	e.printStackTrace();
	    }
	    return distance;
	  
	}

	public Point[] uniformDistribution(){
		
		Point[] cloud = new Point[n];
		
		for( int k=0;k <n ; k++){
			cloud[k]= new Point(rand.nextInt(500),rand.nextInt(500),k);
		}
		return cloud;
	}
	
	public Point[] normalDistribution(){
		
		Point[] cloud = new Point[n];
		
		NormalDistribution d = new NormalDistribution(0,200);
		NormalDistribution d1 = new NormalDistribution(200,100);
		NormalDistribution d2 = new NormalDistribution(100,50);
		NormalDistribution d3 = new NormalDistribution(400,80);
		
	
		for( int k=0;k <n ; k++){
			cloud[k]= new Point((int)(Math.abs(d3.sample())),(int)(Math.abs(d3.sample())),k);
			System.out.print(cloud[k].x+"  ");
		}
		return cloud;
	}

	public Point[] groupsDistribution(){
		
		Point[] cloud = new Point[n];
		for( int k=0;k <n ; k++){
			cloud[k]= new Point(rand.nextInt(100),rand.nextInt(100),k);
		}
		int i=0,k=1, l=0;
		while(i<8*(n/9)){
			for( int j = i ; j<(i + n/9); j++){	
				cloud[j].x+=200*k;
				cloud[j].y+=200*l;
			}
			i+=n/9;
				
			k++;
			if(k%3==0){
				k=0;
				l++;
			}
		}			
		return cloud;
	}
		
	
	public double dist (Point p1 , Point p2){
		return Math.sqrt((p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y));
		
	}
	public double[][] prepareDistances( Point[] points){
		double[][]d = new double[n][n];
		for( int k=0;k <n ; k++)
			for( int j=0;j <n ; j++){
				d[k][j]= dist(points[k],points[j]);
				//System.out.println(k+" " +j+" "+d[k][j]);
				}
		return d;
	}
	
	
	public double getTotalDistance(Point[] p, double dist[][]){
		double total =0;
		for(int i =0; i<n-1 ; i++){
			total+= dist[p[i].index][p[i+1].index];
		}
		total+= dist[p[0].index][p[n-1].index];
		return total;
		}
	
	public Point[] getNextOrderArbitrary(Point[] current){
		Point [] next =new Point[n];
		for(int i =0; i<n ; i++)
			next[i]=new Point(current[i].x,current[i].y,current[i].index);
		int a=rand.nextInt(n),b=rand.nextInt(n);
		Point tmp =new Point(next[a].x,next[a].y,next[a].index);
		next[a].x=next[b].x;
		next[a].y=next[b].y;
		next[a].index= next[b].index;
		next[b].x=tmp.x;
		next[b].y=tmp.y;
		next[b].index=tmp.index;
		
		return next;
		
	}
	public Point[] getNextOrderConsecutive(Point[] current){
		Point [] next =new Point[n];
		for(int i =0; i<n ; i++)
			next[i]=new Point(current[i].x,current[i].y,current[i].index);
		int a=iteration%n,b=(iteration+1)%n;
		Point tmp =new Point(next[a].x,next[a].y,next[a].index);
		next[a].x=next[b].x;
		next[a].y=next[b].y;
		next[a].index= next[b].index;
		next[b].x=tmp.x;
		next[b].y=tmp.y;
		next[b].index=tmp.index;
		
		return next;
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(runner==Thread.currentThread()){
			
			n = 100;
			currentOrder = uniformDistribution();
			T0 = 2000;
			total= anneal(currentOrder, T0);
			try{Thread.sleep(200);
			}
			catch (InterruptedException exc){
				System.out.println("sleep exeption");
			} 
			repaint();
			stop();
		}
	}

	 public void paint(Graphics g) {
	        int i,j,rad=2,shift=10,p1,p2,p3,p4;
	       if(ready){
	        for(i=0;i<n;i++){
	           p1=(int)(currentOrder[i].x);
	           p2=(int)(currentOrder[i].y);
	           g.fillOval(shift+p1,shift+p2,2*rad,2*rad);
	       }
	    for(i=0;i<n-1;i++){
	           p1=(int)(currentOrder[i].x);
	           p2=(int)(currentOrder[i].y);
	           p3=(int)(currentOrder[i+1].x);
	           p4=(int)(currentOrder[i+1].y);
	      g.drawLine(shift+p1,shift+p2,shift+p3,shift+p4);
	         }         
	           p1=(int)(currentOrder[n-1].x);
	           p2=(int)(currentOrder[n-1].y);
	           p3=(int)(currentOrder[0].x);
	           p4=(int)(currentOrder[0].y);
	      g.drawLine(shift+p1,shift+p2,shift+p3,shift+p4);
	      
	       }
	      g.drawString("Total length= "+total,100,550);
	      g.drawString("initial temp = "+T0+" current temp = " + temp,100,580);
	
	     }
	}
