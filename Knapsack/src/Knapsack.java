import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author rileyp
 * This is the class for the knapsack programming assignment
 *
 */
public class Knapsack {
	
	/**
	 * 
	 * @param w List of weights
	 * @param n What stage you are currently at
	 * @param limit The limit of weights you can carry in the knapsack
	 * @return int the optimal weight of the knapsack
	 *
	 *
	 */
	public static int knapsackSumA(int[] w, int n, int limit) {
		if(n<0 || w[n] > limit)
			return 0;
		else {
						
			int excLast = knapsackSumA(w,n-1,limit);
			int incLast = w[n] + knapsackSumA(w,n-1,limit - w[n]);
			
			if(Math.max(incLast, excLast) == incLast)
				return incLast;
			else
				return excLast;
		}
	}
	
	/**
	 * 
	 * @param w List of weights
	 * @param n What stage you are currently at
	 * @param limit The limit of weight you can carry in the knapsack
	 * @param list A list of the objects in the knapsack
	 * @return int the optimal weight of the knapsack
	 *
	 *
	 */
	public static int knapsackSumB(int[] w, int n, int limit, List<Integer> list) {
		if(n<0 || w[n] > limit)
			return 0;
		else {
			
			//Lists to add
			List<Integer> list1 = new ArrayList<Integer>();
			
			List<Integer> list2 = new ArrayList<Integer>();
			
			list2.add(w[n]);
			
			int excLast = knapsackSumB(w,n-1,limit, list1);
			
			int incLast = w[n] + knapsackSumB(w,n-1,limit - w[n], list2);
			
			if(Math.max(incLast, excLast) == incLast) {
				
				list.addAll(list2);
				return incLast;
			
			}else {
				list.addAll(list1);
				return excLast;
			}
		}
	}
	
	
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>();
		
		int[] w = new int[] {3,4,5,2,7,1};
		int limit = 11;
		System.out.println(knapsackSumA(w,5,limit));
		
		System.out.println(knapsackSumB(w,5,limit,list));
		System.out.println(list);
		
	}
}
