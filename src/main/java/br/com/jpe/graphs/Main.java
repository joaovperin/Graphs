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
        Main.runWithTestDummyArgs();
    }

    /** Vertex count */
    final int vertex;
    /** Edges count */
    final int edges;
    /** Matrix with input data - The connections */
    final int[][] mtz;

    /**
     * Class constructor
     *
     * @param vertex
     * @param edges
     * @param mtz
     */
    public Main(int vertex, int edges, int[][] mtz) {
        this.vertex = vertex;
        this.edges = edges;
        this.mtz = mtz;
    }

    /**
     * Run the application with test dummy data
     */
    private static void runWithTestDummyArgs() {
        new Main(3, 2, new int[][] {
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
