import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Matrix {
	// creates a list of arrays to contain proper elements
	ArrayList<int[]> c_mat = new ArrayList<int[]>();

	// Constructor that populate the specified dimensions with 0
	public Matrix(int m, int n) {
		//Creates arrays that are added to the list to allow for an easily expandable matrix
		for (int i = 0; i < m; i++) {
			int[] temp = new int[n];
			for (int j = 0; j < n; j++) {
				temp[j] = 0;
			}
			c_mat.add(temp);
		}

	}
	//Constructs the matrix given a filename
	public Matrix(String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line;
		//Splits based on commas and remove extraneous whitespace to avoid string to integer issues
		while ((line = reader.readLine()) != null) {
			String[] row = line.replaceAll(" ", "").split(",");
			int[] temp = new int[row.length];
			for (int i = 0; i < row.length; i++) {
				temp[i] = Integer.parseInt(row[i]);
			}
			c_mat.add(temp);
		}
		reader.close();
	}
	//Prints the Matrix
	public String toString() {
		String output = "";
		for (int m = 0; m < c_mat.size(); m++) {
			for (int n = 0; n < c_mat.get(m).length; n++) {
				output += c_mat.get(m)[n] + ",";
			}
			output += System.lineSeparator();
		}
		return output;
	}
	//increments through all cells and returns the sum of the cells
	public int getCellTotal() {
		int total = 0;
		for (int i = 0; i < c_mat.size(); i++) {
			for (int j = 0; j < c_mat.get(i).length; j++) {
				total += c_mat.get(i)[j];
			}
		}
		return total;
	}

	public void setCell(int x, int y, int rep) {
		c_mat.get(x)[y] = rep;
	}

	public int getCell(int x, int y) {
		return c_mat.get(x)[y];
	}

	public int getCols() {
		return c_mat.get(0).length;
	}

	public int getRows() {
		return c_mat.size();
	}
}
