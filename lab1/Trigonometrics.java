package lab1;

public class Trigonometrics {

	double sinus(double x, int len ){
		double even =0;
		double odd = 0;
		
		for (int i=0 ; i<len ; i++ ){
			if(i%2 ==0)
				even += Math.pow(x, 2 * i + 1) / factorial(2 * i + 1);
			else 
				odd -= Math.pow(x, 2 * i + 1) / factorial(2 * i + 1);
		}
		return odd+even;
	}
	
	double cosinus(double x, int len ){
		double even =0;
		double odd = 0;
		
		for (int i=0 ; i<len ; i++ ){
			if(i%2 ==0)
				even += Math.pow(x, 2 * i) / factorial(2 * i);
			else 
				odd -= Math.pow(x, 2 * i) / factorial(2 * i);
		}
		return odd+even;
	}
	
	
	private double factorial(int n) {
		if(n==0)
			return 1;
		
		return n*factorial(n-1);
	}
	
	double f1(double x , int len){
		return x - sinus(x,len);
	}
	
	double f2(double x, int len){
		return 1- cosinus(x,len);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		double x= 4;
		int precision = 100;
		
		Trigonometrics z = new Trigonometrics();

		System.out.println(" My x- sin(x)=   " +z.f1(x,precision));
		System.out.println(" x- Math.sin(x)= " +(x-Math.sin(x)));
		System.out.println(" My 1-cos(x)=    " +z.f2(x,precision));
		System.out.println(" 1-Math.cos(x)=  " +(1-Math.cos(x)));

	}

}
