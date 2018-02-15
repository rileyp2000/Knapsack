/**
 * 
 * @author rileyp
 *
 */
public class Knapsack {
	
	public static int knapsackSumA(int[] w, int n, int limit) {
		if(n<0 || w[n] > limit)
			return 0;
		else {
			int excLast = knapsackSumA(w,n-1,limit);
			int incLast = knapsackSumA(w,n-1,limit - w[n]);
			return Math.max(excLast, incLast) + w[n];
		}
	}
	
	public static void main(String[] args) {
		Knapsack k = new Knapsack();
		int[] w = new int[] {3,4,5,};
		int limit = 11;
		System.out.println(knapsackSumA(w,2,limit));
	}
}
