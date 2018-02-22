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
	 * 
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
		if (n < 0 || w[n] > limit)
			return 0;
		else {

			int excLast = knapsackSumA(w, n - 1, limit);
			int incLast = w[n] + knapsackSumA(w, n - 1, limit - w[n]);

			if (Math.max(incLast, excLast) == incLast)
				return incLast;
			else
				return excLast;
		}
	}

	/**
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
		if (n < 0 || w[n] > limit)
			return 0;
		else {

			// Lists to add
			List<Integer> list1 = new ArrayList<Integer>();

			List<Integer> list2 = new ArrayList<Integer>();

			list2.add(w[n]);

			int excLast = knapsackSumB(w, n - 1, limit, list1);

			int incLast = w[n] + knapsackSumB(w, n - 1, limit - w[n], list2);

			if (Math.max(incLast, excLast) == incLast) {

				list.addAll(list2);
				return incLast;

			} else {
				list.addAll(list1);
				return excLast;
			}
		}
	}

	public static ArrayList<String> getFileNames(String[] args) {
		String fileName = "";
		Scanner keyboard = new Scanner(System.in);

		if (args.length == 1)
			fileName = args[0];
		else {
			System.out.print("\nEnter input file name: ");
			fileName = keyboard.nextLine().trim();
		}

		Scanner allFiles = null;

		try {
			allFiles = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("File not found!!");
		}

		ArrayList<String> fileNames = new ArrayList<String>();

		while (allFiles.hasNextLine()) {
			fileNames.add(allFiles.nextLine().trim());
		}
		keyboard.close();
		allFiles.close();
		return fileNames;
	}

	public static void formatPrint(PrintWriter out, int limit, List<Integer> usedWeights, int optimal, int[] w) {
		out.print(" " + limit + "\t");
		
		for(int i = 0; i < w.length-1; i++) 
			out.print(w[i] + ", ");
		out.print(w[w.length-1]);
		
		out.println();
		
		for(Integer i : w) {
			if(usedWeights.contains(i))
				out.println(1 + " " + i + " pound watermelons");
			else
				out.println(0 + " " + i + " pound watermelons");
		}
		
		out.println("\n");
	}

	public static void processFile(PrintWriter out, Scanner f) {
		String limit = f.nextLine().trim();
		int lim = Integer.parseInt(limit);

		ArrayList<Integer> weights = new ArrayList<Integer>();
		
		while (f.hasNextLine()) 
			weights.add(Integer.parseInt(f.nextLine().trim()));
		
		int[] w = new int[weights.size()];
		for (int i = 0; i < weights.size(); i++)
			w[i] = weights.get(i);

		List<Integer> usedWeights = new ArrayList<Integer>();
		int optimal = knapsackSumB(w, w.length - 1, lim, usedWeights);

		formatPrint(out, lim,usedWeights, optimal, w);

	}

	public static void main(String[] args) {

		ArrayList<String> fileNames = getFileNames(args);

		PrintWriter out = null;
		try {
			out = new PrintWriter(new File("knapsack.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Output file Error!");
			System.exit(1);
		}

		for (String file : fileNames) {
			if (!file.equals("")) {
				out.print(file);
				Scanner f = null;
				try {
					f = new Scanner(new File(file));
				} catch (FileNotFoundException e) {
					out.print("File Missing");
					System.exit(1);
				}

				// Process the file
				processFile(out, f);
			}
			
		out.close();
		}

		/*List<Integer> list = new ArrayList<Integer>();

		int[] w = new int[] { 3, 4, 5, 2, 7, 1 };
		int limit = 11;
		System.out.println(knapsackSumA(w, 5, limit));

		System.out.println(knapsackSumB(w, 5, limit, list));
		System.out.println(list);*/

	}
}
