import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.*;

import javax.swing.JComboBox;

public class Main {
	public static void main(String[] args) throws Exception {
		(new Main()).go();
	}

	private void go() throws Exception {
		//gets file names and initializes matrices with corresponding files
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter filename for first matrix");
		String filename = sc.nextLine();
		Matrix a = new Matrix(filename);
		System.out.println("Please enter filename for second matrix");
		filename = sc.nextLine();
		Matrix b = new Matrix(filename);
		System.out.println(a);
		System.out.println(b);
		//creates the product matrice
		Matrix c = new Matrix(a.getRows(), b.getCols());
		//creates list of threads to be run
		List<Callable<Boolean>> tasks = new ArrayList<Callable<Boolean>>();
		//checks if columns multipliable
		if (a.getCols() != b.getRows() == true) {
			System.out.println("improperly sized matrices");
			System.exit(0);
		}
		//Increments through a's rows and b's columns to send the cells to the thread and runs the thread
		for (int i = 0; i < a.getRows(); i++) {
			for (int j = 0; j < b.getCols(); j++) {
				Callable<Boolean> task = new MyThread(i, j, a, b, c);
				tasks.add(task);
			}

		}
		ExecutorService executor = Executors.newFixedThreadPool(50);
		List<Future<Boolean>> futures = executor.invokeAll(tasks);

		for (Future<Boolean> f : futures) {
			if (!f.get())
				throw new Exception("Thread completion error!");
		}
		
		//Creates c1.txt file and writes the product to the file
		try {
			PrintWriter writer = new PrintWriter("C1.txt", "UTF-8");
			writer.println(c);
			writer.close();
		} catch (IOException e) {
			System.exit(0);
		}
		//prints the cell total and the product matrix
		System.out.println(c.getCellTotal());
		executor.shutdown();
	}

	public class MyThread implements Callable<Boolean> {
		int m_x;
		int m_y;
		Matrix m_a;
		Matrix m_b;
		Matrix m_c;
		//used container as a buffer at the time for debugging purposes
		int[][] container;

		public MyThread(int x, int y, Matrix a, Matrix b, Matrix c) {
			m_x = x;
			m_y = y;
			m_a = a;
			m_b = b;
			m_c = c;
			container = new int[a.getRows()][b.getCols()];
		}

		@Override
		public Boolean call() {
			//increment through the a cols and multiply the proper cells
			for (int k = 0; k < m_a.getCols(); k++) {
				container[m_x][m_y] = container[m_x][m_y] + (m_a.getCell(m_x, k) * m_b.getCell(k, m_y));
			}
			//sets cell in matrix to result found
			m_c.setCell(m_x, m_y, container[m_x][m_y]);
			return true;
		}
	}
}
