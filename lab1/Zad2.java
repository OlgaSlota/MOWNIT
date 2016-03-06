package lab1;

public class Zad2 {

	float dzeta(float s,int n, boolean back){

		float result = 0;
		if(back){
			for (int k = n ; k>=1 ; k--){
				result += 1/Math.pow(k,s);
			}
		}
		else {
			for (int k = 1 ; k<=n ; k++){
				result += 1/Math.pow(k,s);
		}}
		return result;
			
	}

	float eta(float s,int n, boolean back){

		float result = 0;
		if(back){
			for (int k = n ; k>=1 ; k--){
				result += Math.pow(-1,k-1) * 1/Math.pow(k,s);
			}
		}
		else {
			for (int k = 1 ; k<=n ; k++){
				result += Math.pow(-1,k-1) * 1/Math.pow(k,s);
		}}
		return result;
			
	}

	double dzetaD(double s,int n, boolean back){

		float result = 0;
		if(back){
			for (int k = n ; k>=1 ; k--){
				result += 1/Math.pow(k,s);
			}
		}
		else {
			for (int k = 1 ; k<=n ; k++){
				result += 1/Math.pow(k,s);
		}}
		return result;
			
	}

	double etaD(double s,int n, boolean back){

		float result = 0;
		if(back){
			for (int k = n ; k>=1 ; k--){
				result += Math.pow(-1,k-1) * 1/Math.pow(k,s);
			}
		}
		else {
			for (int k = 1 ; k<=n ; k++){
				result += Math.pow(-1,k-1) * 1/Math.pow(k,s);
		}}
		return result;
			
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Zad2 z = new Zad2();
		
		System.out.println("single:");
		System.out.println("w przód:");
		System.out.println(z.dzeta(2, 50, false));
		System.out.println(z.eta(2, 50, false));
		System.out.println(z.dzeta((float) 3.22, 100, false));
		
		System.out.println("w ty³:");
		System.out.println(z.dzeta(2, 50, true));
		System.out.println(z.eta(2, 50, true));
		System.out.println(z.dzeta((float) 3.22, 100,true));
		
		
		System.out.println("double:");
		System.out.println("w przód:");
		System.out.println(z.dzetaD(2, 50, false));
		System.out.println(z.etaD(2, 50, false));
		System.out.println(z.dzetaD((float) 3.22, 100, false));
		System.out.println(z.etaD((float)7.2, 500, false));
		System.out.println("w ty³:");
		System.out.println(z.dzetaD(2, 50, true));
		System.out.println(z.etaD(2, 50, true));
		System.out.println(z.dzetaD((float) 3.22, 100,true));
		System.out.println(z.etaD((float)7.2, 500, true));
		
	}

}
