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

import java.io.IOException;
import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.InputStreamReader;
import java.security.InvalidParameterException;

/**
 * Main class
 */
public class Main {

    /**
     * Main method
     *
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // Test data easter egg
        if (args != null && args.length == 1 && args[0].toLowerCase().contains("--test")) {
            Main.runWithTestDummyArgs();
            System.exit(0);
        }
        // Run with data from the input
        try ( BufferedReader sc = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.printf("Please, input the number of vertexes: ");
            final int vertex = Integer.valueOf(sc.readLine());
            System.out.printf("Please, input the number of edges: ");
            final int edges = Integer.valueOf(sc.readLine());
            int[][] edgesArray = new int[edges][2];
            int currentEdge = edges;
            for (int i = 0; i < edges; i++) {
                edgesArray[i] = new int[2];
                System.out.printf("Please, input %2d edges in the format '3 5' (without the quotes): ", currentEdge--);
                String input = sc.readLine();
                Matcher m = Pattern.compile("(\\d+)\\s+(\\d+)").matcher(input);
                // Validate pattern
                if (!m.find()) {
                    throw new InvalidParameterException("Invalid input: ".concat(input));
                }
                int v0 = Integer.parseInt(m.group(1)),
                        v1 = Integer.parseInt(m.group(2));
                // Validate if vertex exists
                if (v0 < 0 || v0 >= vertex || v1 < 0 || v1 >= vertex || v0 == v1) {
                    throw new InvalidParameterException("Invalid input: ".concat(input));
                }
                // Validate value already taken
                for (int[] v : edgesArray) {
                    if ((v[0] == v0 && v[1] == v1) || v[0] == v1 && v[1] == v0) {
                        throw new InvalidParameterException("Invalid input: ".concat(input));
                    }
                }
                // Store the values
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
        new Main(3, new int[][]{
            {0, 1},
            {1, 2},
            {2, 0}
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

        // Validates the matrix X axis to see if it's a regular graph
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

        // Validates the matrix Y axis to see if it's a regular graph
        for (int j = 0; j < adjx.length; j++) {
            int tmpCnt = cnt;
            for (int i = 0; i < adjx[0].length; i++) {
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
