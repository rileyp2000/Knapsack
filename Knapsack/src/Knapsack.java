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
			int incLast = w[n] + knapsackSumA(w,n-1,limit - w[n]);
			
			if(Math.max(incLast, excLast) == incLast)
				return incLast;
			else
				return excLast;
		}
	}
	
	public static int knapsackSumB(int[] w, int n, int limit,List<Integer> list) {
		//int weight = w[n];
		if(n<0 || w[n] > limit)
			return 0;
		else {
			
			//Lists to add
			List<Integer> list1 = new ArrayList<Integer>();
			list1.addAll(list);
			List<Integer> list2 = new ArrayList<Integer>();
			list2.addAll(list);
			
			//gets value, excluding the last element
			int excLast = knapsackSumB(w,n-1,limit,list1);
			
			//adds weight of nth item to the list
			list2.add(w[n]);
			
			//gets value, including the last element
			int incLast = w[n] + knapsackSumB(w,n-1,limit - w[n],list2);
			
			
			if(incLast >= excLast) {
				
				list.add(w[n]);
				return incLast;
				
			}else
				
				return excLast;
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
