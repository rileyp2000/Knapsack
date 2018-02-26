import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author rileyp This is the class for the knapsack programming assignment
 *
 */
public class Knapsack {

	/**
	 * This does the knapsack problem recursively, without storing the items
	 * @param w
	 *            List of weights
	 * @param n
	 *            What stage you are currently at
	 * @param limit
	 *            The limit of weights you can carry in the knapsack
	 * @return int the optimal weight of the knapsack
	 *
	 *
	 */
	public static int knapsackSumA(int[] w, int n, int limit) {
		//base case
		if (n < 0)
			return 0;
		else {
			//recursively calculates including last, and excluding last
			int excLast = knapsackSumA(w, n - 1, limit);
			int incLast = w[n] + knapsackSumA(w, n - 1, limit - w[n]);
			
			if(w[n] > limit)
				return excLast;
			
			//returns whichever is greater
			if (Math.max(incLast, excLast) == incLast)
				return incLast;
			else
				return excLast;
		}
	}

	/**
	 * 
	 * This does the knapsack problem recursively, while storing which items are used
	 * 
	 * @param w
	 *            List of weights
	 * @param n
	 *            What stage you are currently at
	 * @param limit
	 *            The limit of weight you can carry in the knapsack
	 * @param list
	 *            A list of the objects in the knapsack
	 * @return int the optimal weight of the knapsack
	 *
	 *
	 */
	public static int knapsackSumB(int[] w, int n, int limit, List<Integer> list) {
		//base case
		if (n < 0 )
			return 0;
		else {
			
			// Lists to add
			List<Integer> list1 = new ArrayList<Integer>();

			List<Integer> list2 = new ArrayList<Integer>();
			
			//add current weight to second list (for incLast)
			list2.add(w[n]);
			
			//Calculates recursively both including and excluding the current sum, passes in correct list
			int excLast = knapsackSumB(w, n - 1, limit, list1);
			int incLast = w[n] + knapsackSumB(w, n - 1, limit - w[n], list2);
			
			if(w[n] > limit){
				list.addAll(list1);
				return excLast;
			}
			//if including is greater, add the values including the current to the main list
			if (Math.max(incLast, excLast) == incLast) {
				list.addAll(list2);
				return incLast;
			} else {
				list.addAll(list1);
				return excLast;
			}
		}
	}
	
	
	/**
	 * 
	 * This gets the list of all the file names in the main text file
	 * 
	 * @param args arguments given for program in runtime
	 * @return ArrayList of the file names within the given main file
	 */
	public static ArrayList<String> getFileNames(String[] args) {
		String fileName = "";
		Scanner keyboard = new Scanner(System.in);
		
		//If given arg, use that. Otherwise, ask for it
		if (args.length == 1)
			fileName = args[0].trim();
		else {
			System.out.print("\nEnter input file name: ");
			fileName = keyboard.nextLine().trim();
		}

		Scanner allFiles = null;
		//Scanner based on given file name
		try {
			allFiles = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("File not found!!");
			System.exit(1);
		}
		
		//Create list of file names in txt file
		ArrayList<String> fileNames = new ArrayList<String>();

		while (allFiles.hasNextLine()) {
			fileNames.add(allFiles.nextLine().trim());
		}
		
		//closes up scanners
		keyboard.close();
		allFiles.close();
		
		return fileNames;
	}
	
	/**
	 * 
	 * Formats output to be printed to the file
	 * 
	 * @param out PrintWriter for ouput file
	 * @param limit limit of the knapsack problem
	 * @param usedWeights List of the weights used in that knapsack problem
	 * @param optimal The weight returned by the knapsack problem
	 * @param w Array of weights
	 *
	 *void
	 */
	public static void formatPrint(PrintWriter out, int limit, List<Integer> usedWeights, int optimal, int[] w) {
		//print limit of problem
		out.print(" " + limit + " \t");
		
		//print weights used in problem
		for(int i = 0; i < w.length-1; i++) 
			out.print(w[i] + ", ");
		out.print(w[w.length-1]);
		
		out.println();
		
		//print which weights are used
		for(Integer i : w) {
			if(usedWeights.contains(i)) {
				out.println(1 + " " + i + " pound watermelons");
				usedWeights.remove(i);
			}else
				out.println(0 + " " + i + " pound watermelons");
		}
		
		out.println();
		out.println();
	}
	
	/**
	 * 
	 * Does all the knapsack stuff for the given file, and then sends the results to be printed in the output file
	 * 
	 * @param out the PrintWriter for the output file
	 * @param f Scanner for the print file
	 *
	 *void
	 */
	public static void processFile(PrintWriter out, Scanner f) {
		if(!f.hasNextLine()){
			out.println(" Empty File!!\n\n");
			return;	
		}
		
		//gets limit as both string and int
		String limit = f.nextLine().trim();
		int lim = Integer.parseInt(limit);
		
		//get list of weights
		ArrayList<Integer> weights = new ArrayList<Integer>();
		
		while (f.hasNextLine() && f.hasNextInt()){ 
			String nxtLn = f.nextLine();
			if(!nxtLn.equals(""))
				weights.add(Integer.parseInt(nxtLn.trim()));
		}
		
		//converts list to array
		int[] w = new int[weights.size()];
		for (int i = 0; i < weights.size(); i++)
			w[i] = weights.get(i);
		
		if(w.length == 0){
			out.println("No valid Weights for limit " + limit + ", you have an empty knapsack :(\n\n");
			return;
		}
		
		//does knapsack problem, and gets new weights
		List<Integer> usedWeights = new ArrayList<Integer>();
		int optimal = knapsackSumB(w, w.length - 1, lim, usedWeights);
		System.out.println(optimal);
		//sends info to be formatted and printed to file
		formatPrint(out, lim,usedWeights, optimal, w);

	}

	public static void main(String[] args) {

		ArrayList<String> fileNames = getFileNames(args);
		
		//creates PrintWriter for output file
		PrintWriter out = null;
		try {
			out = new PrintWriter(new File("knapsack.txt"));
		} catch (Exception e) {
			System.out.println("Output file Error!");
			System.exit(1);
		}
		System.out.println(fileNames);
		//Does whole process for each file
		for (String file : fileNames) {
			System.out.println("Working on: " + file);
			if (!file.equals("")) {
				
				//creates scanner for file
				Scanner f = null;
				try {
					f = new Scanner(new File(file));
				} catch (FileNotFoundException e) {
					System.out.println("File Missing: " + file);
					continue;
				}
				
				out.print(file);
				
				// Process the file
				processFile(out, f);
				//System.out.println("Finished processing file: " + file);
			}
			
		}
		
		out.close();
		
		/************** OLD TEST **************/
		
		/*List<Integer> list = new ArrayList<Integer>();

		int[] w = new int[] { 3, 4, 5, 2, 7, 1 };
		int limit = 11;
		System.out.println(knapsackSumA(w, 5, limit));

		System.out.println(knapsackSumB(w, 5, limit, list));
		System.out.println(list);*/

	}
}
