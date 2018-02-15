import java.util.ArrayList;
import java.util.List;

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
			if(Math.max(incLast, excLast) == excLast)
				return incLast + w[n];
			else
				return excLast;
		}
	}
	
	public static int knapsackSumB(int[] w, int n, int limit,List<Integer> list) {
		if(n<0 || w[n] > limit)
			return 0;
		else {
			int excLast = knapsackSumB(w,n-1,limit,new ArrayList<Integer>());
			int incLast = knapsackSumB(w,n-1,limit - w[n],new ArrayList<Integer>());
			if(Math.max(incLast, excLast) == excLast) {
				list.add(excLast);
				return incLast + w[n];
			}else
				return excLast;
		}
	}
	
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>();
		int[] w = new int[] {3,4,5,2,7,1};
		int limit = 11;
		System.out.println(knapsackSumB(w,5,limit,list));
		
	}
}
