/*
 * Copyright (C) 2019 Perin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received edges copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.com.jpe.graphs;

import java.util.Scanner;

/**
 * Main class
 */
public class Main {

    /**
     * Main method
     *
     * @param args
     */
    public static void main(String[] args) {
        // Force to use test dummy data :P
        if (true) {
            args = new String[] { "--test" };
        }
        // Test data easter egg
        if (args != null && args.length == 1 && args[0].toLowerCase().contains("--test")) {
            Main.runWithTestDummyArgs();
            System.exit(0);
        }
        // Run with data from the input
        try (Scanner sc = new Scanner(System.in)) {
            System.out.printf("\nPlease, input the number of vertexes: ");
            int vertex = sc.nextInt();
            System.out.printf("\nPlease, input the number of edges: ");
            int edges = sc.nextInt();
            System.out.printf("\nPlease, input %2d edges in the format '3 5' (without the quotes): ", edges);
            int[][] edgesArray = new int[edges][2];
            for (int i = 0; i < vertex; i++) {
                edgesArray[i] = new int[2];
                sc.reset();
                String[] split = sc.next().split(" ");
                int v0 = Integer.parseInt(split[0]),
                        v1 = Integer.parseInt(split[1]);
                edgesArray[i][0] = v0;
                edgesArray[i][1] = v1;
            }
            // Run with the data
            new Main(vertex, edgesArray).run();
        }
    }

    /** Vertex count */
    private final int vertex;
    /** Matrix with input data - The connections */
    private final int[][] mtz;

    /**
     * Class constructor
     *
     * @param vertex
     * @param mtz
     */
    public Main(int vertex, int[][] mtz) {
        this.vertex = vertex;
        this.mtz = mtz;
    }

    /**
     * Run the application with test dummy data
     */
    private static void runWithTestDummyArgs() {
        new Main(3, new int[][] {
            { 0, 1 },
            { 1, 2 }
        }).run();
    }

    /**
     * Runs the exercis
     */
    private void run() {
        // Instantiate an empty adjacence matrix
        final int[][] adjx = new int[vertex][vertex];

        // Fills the matrix with data from input
        for (int[] line : mtz) {
            adjx[line[0]][line[1]] = 1;
        }

        // Count the number of edges on the first line
        int cnt = 0;
        for (int j = 0; j < adjx[0].length; j++) {
            if (adjx[0][j] == 1) {
                cnt++;
            }
        }

        // Defaults to regular graph
        boolean regular = true;

        // Validates the matrix to see if it's a regular graph
        for (int i = 1; i < adjx.length; i++) {
            int tmpCnt = cnt;
            for (int j = 0; j < adjx[0].length; j++) {
                if (adjx[i][j] == 1) {
                    tmpCnt--;
                }
            }
            // Needs to be 0
            if (tmpCnt != 0) {
                regular = false;
            }
        }

        // Print the matrix
        for (int[] line : adjx) {
            for (int j = 0; j < line.length; j++) {
                System.out.printf("%5d", line[j]);
            }
            System.out.println();
        }
        // Prints the result
        System.out.printf("Is the chosen graph regular? %s \n", String.valueOf(regular));
    }

}
