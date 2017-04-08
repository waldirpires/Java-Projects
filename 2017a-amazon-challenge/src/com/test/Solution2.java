package com.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Solution2 {

	public static class Coord {
		final int x;
		final int y;

		Coord(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);

		int width = stdin.nextInt();
		int length = stdin.nextInt();
		int numberOfDcs = stdin.nextInt();
		List<Coord> listOfDcs = new ArrayList<>(numberOfDcs);

		for (int i = 0; i < numberOfDcs; i++) {

			Coord coord = new Coord(stdin.nextInt(), stdin.nextInt());
			listOfDcs.add(coord);
		}

		int maxDist = getMaxCustomerDistance(listOfDcs, width, length);

		System.out.print(maxDist);
	}

	public static int getMaxCustomerDistance(final List<Coord> distributionCenterList, final int regionWidth,
			final int regionHeight) {
		int max = 0;
		int matrix[][] = new int[regionWidth][regionHeight];

		for (int i = 0; i < regionWidth; i++) {
			Arrays.fill(matrix[i], Integer.MAX_VALUE);
		}

		for (Coord c : distributionCenterList) {
			matrix[c.x][c.y] = 0;
		}

		for (Coord c : distributionCenterList) {
			for (int i = 0; i < regionWidth; i++) {
				for (int j = 0; j < regionHeight; j++) {
					if (matrix[i][j] == 0)
						continue;
					int dist = (int) Math.ceil(Math.sqrt(Math.pow(i - c.x, 2) + Math.pow(j - c.y, 2)));
					if (dist < matrix[i][j]) {
						matrix[i][j] = dist;
					}
				}
			}
		}
		for (int i = 0; i < regionWidth; i++) {
			for (int j = 0; j < regionHeight; j++) {
				if (max < matrix[i][j]) {
					max = matrix[i][j];
				}
			}
		}

		return max;
	}

	static void printMatrix(int matrix[][]) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + "  ");
			}
			System.out.println();
		}

	}

	static int getDistance(int x1, int y1, int x2, int y2) {
		return (int) Math.ceil(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
	}
}
