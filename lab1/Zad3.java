package lab1;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.JavaPlot.Key;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.style.NamedPlotColor;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Style;

public class Zad3 {

	static int N =500;
	
	
	public double [] calculate(double r , double x0){
		double[] x = new double [N];
		x[0]=x0;
		for ( int n =0 ; n<N-1 ; n++){
			x[n+1] =r* x[n]*(1-x[n]);
			System.out.println(x[n+1]);
		}
		return x;	
	}
	
	public float [] calculatef(float r , float x0){
		float[] x = new float [N];
		x[0]=x0;
		for ( int n =0 ; n<N-1 ; n++){
			x[n+1] =r* x[n]*(1-x[n]);
			System.out.println(x[n+1]);
		}
		return x;	
	}
	public void plotting(double x0){
		
		JavaPlot p = new JavaPlot();
		PlotStyle style = new PlotStyle();
		style.setStyle(Style.DOTS);
		style.setLineType(NamedPlotColor.RED);	
		
		double arrayOfXes [] [] = new double [310][N];
		int index =0;
		for(double r = 1.0 ; r<= 4.0 ; r+=0.01){
			
			arrayOfXes[index]= calculate(r, x0);
			double [][] points = new double[N][2];
			for(int i =0 ; i< N-6 ; i++){
				points[i][1]=arrayOfXes[index][i+6];
				points[i][0]=r-2.5;
			}
			DataSetPlot data = new DataSetPlot(points);
			data.setPlotStyle(style);
			p.addPlot(data);
			//p.addPlot(points);
			index ++;
		}
		p.setKey(Key.OFF);
		p.plot(); 
	}
	
	public void compare(double x0d){
		
		
		float x0f = (float)x0d;
		JavaPlot pComp = new JavaPlot();
		
		PlotStyle dStyle = new PlotStyle();
		dStyle.setStyle(Style.DOTS);
		dStyle.setLineType(NamedPlotColor.BLUE);
		
		PlotStyle fStyle = new PlotStyle();
		fStyle.setStyle(Style.DOTS);
		fStyle.setLineType(NamedPlotColor.RED);
		pComp.setKey(Key.OFF);
		pComp.setTitle("Comparison float-red vs double-blue, x0="+ x0f);;
		
		double arrayD [] [] = new double [60][N];
		int ind =0;
		
		for(double r = 3.75 ; r<= 3.8 ; r+=0.001){			
			arrayD[ind]= calculate(r, x0d);
			double [][] points = new double[N][2];
			for(int i =0 ; i< N-6 ; i++){
				points[i][1]=arrayD[ind][i+6];
				points[i][0]=r-3.74;
			}
			DataSetPlot d = new DataSetPlot(points);
			d.setPlotStyle(dStyle);
			pComp.addPlot(d);
			ind ++;
		}

		float arrayF [] [] = new float [60][N];
		ind =0;
		
		for(float r = (float) 3.75 ; r<= (float) 3.8 ; r+=0.001){				
			arrayF[ind]= calculatef(r, x0f);
			float [][] points = new float[N][2];
			for(int i =0 ; i< N-6 ; i++){
				points[i][1]=arrayF[ind][i+6];
				points[i][0]=(float) (r-3.74);
				}
			DataSetPlot f = new DataSetPlot(points);
			f.setPlotStyle(fStyle);
			pComp.addPlot(f);
			ind ++;
		}

		pComp.plot();
	}
	int iterations(float x0){
		int r = 4;
		float x = x0;
		int n =0 ;
		float epsilon = (float) 0.000001;
		 
		 while(Math.abs(x) > epsilon){
			x =r* x*(1-x);
			n++;
		 }
			return n;	
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Zad3 z = new Zad3();
		
		//Zadanie 3 a) :
		for (double x0=0.1; x0<=1.0; x0+=0.1)
			z.plotting(x0);
		
		//Zadanie 3 b) :
		//for (double x0=0.1; x0<=1.0; x0+=0.1)
		//	z.compare(x0);


		//Zadanie 3 c) :		
	//	for (float x0=(float)0.1; x0< 1; x0+=0.1)
	//	System.out.println("Iterations : " +z.iterations(x0));
	
	}

}
