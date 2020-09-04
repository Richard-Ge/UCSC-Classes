/**
Richard Ge
rwge
CS12B/M
4/19/2019
.java file that solves the n-Queens problem
Queens.java

 * Lots of the <= had to be changed to <, I mistakenly thought 
 * that < would work because the array size was +1, but then 
 * the indexes didn't change
 * 
 * Lots of the <= had to be changed to <, I mistakenly thought 
 * that < would work because the array size was +1, but then 
 * the indexes didn't change.
 * 
 * SHOULD HAVE LOOKED AT THE PSEUDOCODE AND READ THE
 * WHOLE ASSIGNMENT TO SAVE TIME!
 accidentally deleted my try/catch blocks, had to redo them 4/19
 */
public class Queens {
	public static void main(String[] args) {
		if(args.length == 1 || args.length == 2) {
			String str = "";
			int n = -1;
			try{
				n = Integer.parseInt(args[0]);
			}
			catch(NumberFormatException e) {
				str = args[0];
				if(!str.equals("-v")){
					System.out.println("Usage: Queens [-v] number");
					System.out.println("Option: -v  verbose output, print all solutions");
					System.exit(1);
				}
				n = Integer.parseInt(args[1]);
			}
			int[][] B = new int[n+1][n+1];
			System.out.print(n+"-Queens has "+findSolutions(B, 1, str)+" solution");
			if(n == 1){
				System.out.println();
			} else {
				System.out.println("s");
			}
		} else {
			System.out.println("Usage: Queens [-v] number");
			System.out.println("Option: -v  verbose output, print all solutions");
		}
	}
	
	static int findSolutions(int[][] B, int i, String mode) {
		//recursive method
		int result = 0;
		// System.out.println("start");
		if(i > B.length-1) {	//== to >
			//changing == to > also checked the answer
			//for B.length, if B[i][0] == 0, return false, else return true
			if(mode.equals("-v")) {
				printBoard(B);
			}
			//
				// System.out.println("+1");
				return 1;
			//
		}
		//loop through the horizontal array
		for(int j = 1; j < B.length; j++) {
			if(B[i][j] == 0) {	//checks if empty
//				System.out.println("i: "+i+" j: "+j);
				placeQueen(B, i, j);
				result = result + findSolutions(B, i+1, mode);
				removeQueen(B, i, j);
			}
		}
		return result;
	}
	static void printBoard(int[][] B) {
		//loop through first column of B
		String line = "(";
		for(int i = 1; i < B.length; i++) {	//leaves out last element?????
			line = line + B[i][0];
			if(i != B.length-1) {
				line = line + ", ";
			}
		}
//		int i=1, j=1;
//		for(int l = j+1, k = i+1; l < B.length && k < B.length; l++) {
//			//what did i write this for?????????????
//			j++;
//		}
		System.out.println(line+")");
	}
	static void placeQueen(int[][] B, int i, int j) {
		//indicate queen at b[i][j]
		B[i][j] = 1;
		//set B[i][0] to j to keep track
		B[i][0] = j;
//		System.out.println(j);
			//use k and l instead of mutating i and j
		//vertical attacks: k = i+1, B[k][j] -= 1, k++
		for(int k = i+1; k<B.length; k++) {
			B[k][j] = B[k][j] - 1;
		}
		//right diag.: l = j+1, k = i+1, B[k][l] -= 1, k++, l++
		for(int l = j+1, k = i+1; l < B.length && k < B.length; l++) {
			//
			B[k][l] = B[k][l] - 1;
			k++;
		}
		//left diag.: k = i+1, l = j-1, B[k][l] -= 1, k++, l--
		for(int k = i+1, l = j-1; l >= 1 && k < B.length; k++) {
			//
			B[k][l] = B[k][l] - 1;
			l--;
		}
		//do not need across
	}
	static void removeQueen(int[][] B, int i, int j) {
		//placeQueen, but do opposite
		//indicate queen at b[i][j]
		B[i][j] = 0;
		//set B[i][0] to j to keep track
		B[i][0] = 0;
			//use k and l instead of mutating i and j
		//vertical attacks: k = i+1, B[k][j] -= 1, k++
		for(int k = i+1; k<B.length; k++) {
			B[k][j] = B[k][j] + 1;
		}
		//right diag.: l = j+1, k = i+1, B[k][l] -= 1, k++, l++
		for(int l = j+1, k = i+1; l < B.length && k < B.length; l++) {
			//
			B[k][l] = B[k][l] + 1;
			k++;
		}
		//left diag.: k = i+1, l = j-1, B[k][l] -= 1, k++, l--
		for(int k = i+1, l = j-1; l >= 1 && k < B.length; k++) {
			//
			B[k][l] = B[k][l] + 1;
			l--;
		}
		//do not need across
	}
}



