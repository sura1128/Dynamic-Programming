
// Copy paste this Java Template and save it as "Supermarket.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0102800A	
// write your name here: Suranjana Sengupta
// write list of collaborators here: Akshat Dubey
// year 2015 hash code: JESg5svjYpIsmHmIjabX (do NOT delete this line)

class Supermarket {
	private int N; // number of items in the supermarket. V = N+1
	private int K; // the number of items that Steven has to buy
	private int[] shoppingList; // indices of items that Steven has to buy
	private int[][] T; // the complete weighted graph that measures the direct
						// walking time to go from one point to another point in
						// seconds

	// if needed, declare a private data structure here that
	// is accessible to all methods in this class
	// --------------------------------------------

	private int memo[][];
	private int INF = 100000000;
	private int V;
	private int p[][];
	private int temp[][];

	public Supermarket() {

	}

	void PreProcess() {
		V = N + 1;

		for (int k = 0; k < V; k++) {
			for (int i = 0; i < V; i++) {
				for (int j = 0; j < V; j++) {
					if (T[i][k] + T[k][j] < T[i][j]) {
						T[i][j] = T[i][k] + T[k][j];
					}
				}
			}
		}
		
		temp = new int[K+1][K+1];
		
		for (int i=0; i<K; i++) {
			for (int j=0; j<K; j++) {
				temp[i+1][j+1] = T[shoppingList[i]][shoppingList[j]];
			}
		}
		
		for (int i=0; i<K; i++) {
			temp[0][i+1] =  T[0][shoppingList[i]];
			temp[i+1][0] =  temp[0][i+1];
		}

	}

	int Query() {
		int ans = 0;

		memo = new int[K+1][(int) Math.pow(2, K + 1)];
		for (int i = 0; i <= K; i++) {
			Arrays.fill(memo[i], -1);
		} 
		
		ans = DP_TSP(0,1);

		return ans;
	}

	int DP_TSP(int u, int m) {

		if (m == (1 << K + 1) - 1) {
			return temp[u][0];
		}

		if (memo[u][m] != -1) {
			return memo[u][m];
		}

		memo[u][m] = INF;
		for (int i = 0; i < K+1; i++) {
			if (i != u && ((m & 1 << i) == 0)) {				
				int D = temp[u][i] + DP_TSP(i, m | (1 << i));
				memo[u][m] = Math.min(memo[u][m], D);
			}

		}
		return memo[u][m];
	}

	void printMatrix() {
		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				System.out.print(T[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	void print() {
		for (int i = 0; i < K+1; i++) {
			for (int j = 0; j < K+1; j++) {
				System.out.print(temp[i][j] + " ");
			}
			System.out.println();
		}
	}

	void run() throws Exception {
		// do not alter this method to standardize the I/O speed (this is
		// already very fast)
		IntegerScanner sc = new IntegerScanner(System.in);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int TC = sc.nextInt(); // there will be several test cases
		while (TC-- > 0) {
			// read the information of the complete graph with N+1 vertices
			N = sc.nextInt();
			K = sc.nextInt(); // K is the number of items to be bought

			shoppingList = new int[K];
			for (int i = 0; i < K; i++)
				shoppingList[i] = sc.nextInt();

			T = new int[N + 1][N + 1];
			for (int i = 0; i <= N; i++)
				for (int j = 0; j <= N; j++)
					T[i][j] = sc.nextInt();

			PreProcess();

			pw.println(Query());
		}

		pw.close();
	}

	public static void main(String[] args) throws Exception {
		// do not alter this method
		Supermarket ps6 = new Supermarket();
		ps6.run();
	}
}

class IntegerScanner { // coded by Ian Leow, using any other I/O method is not
						// recommended
	BufferedInputStream bis;

	IntegerScanner(InputStream is) {
		bis = new BufferedInputStream(is, 1000000);
	}

	public int nextInt() {
		int result = 0;
		try {
			int cur = bis.read();
			if (cur == -1)
				return -1;

			while ((cur < 48 || cur > 57) && cur != 45) {
				cur = bis.read();
			}

			boolean negate = false;
			if (cur == 45) {
				negate = true;
				cur = bis.read();
			}

			while (cur >= 48 && cur <= 57) {
				result = result * 10 + (cur - 48);
				cur = bis.read();
			}

			if (negate) {
				return -result;
			}
			return result;
		} catch (IOException ioe) {
			return -1;
		}
	}
}