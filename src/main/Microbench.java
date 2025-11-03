package main;

import main.simulation.FlockSimulation;
import main.model.BoidType;
import main.spatial.*;

public class Microbench {

    public static void main(String[] args) {
        System.out.println("Starting Microbench...");

        int simulationWidth = 1200;
        int simulationHeight = 800;
        int numBoids = 1000;
        int iterations = 200;
        double neighborRadius = 50;
        double cellSize = 50;

        runBenchmark("NaiveSpatialIndex", new NaiveSpatialIndex(), simulationWidth, simulationHeight, numBoids, iterations, neighborRadius);
        runBenchmark("KDTreeSpatialIndex", new KDTreeSpatialIndex(), simulationWidth, simulationHeight, numBoids, iterations, neighborRadius);
        runBenchmark("QuadTreeSpatialIndex", new QuadTreeSpatialIndex(simulationWidth, simulationHeight), simulationWidth, simulationHeight, numBoids, iterations, neighborRadius);
        runBenchmark("SpatialHashIndex", new SpatialHashIndex(simulationWidth, simulationHeight, cellSize), simulationWidth, simulationHeight, numBoids, iterations, neighborRadius);
    }

    private static void runBenchmark(String name, SpatialIndex index, int width, int height, int numBoids, int iterations, double radius) {
        System.out.println("=== " + name + " ===");

        FlockSimulation simulation = new FlockSimulation(width, height);
        simulation.setSpatialIndex(index);

        for (int i = 0; i < numBoids; i++) {
            simulation.addBoid(BoidType.STANDARD);
        }

        for (int i = 0; i < 20; i++) {
            simulation.update();
        }

        long start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            simulation.update();
        }
        long end = System.nanoTime();

        double avgMillis = (end - start) / 1_000_000.0 / iterations;
        System.out.printf("Average time per iteration: %.3f ms%n%n", avgMillis);
    }
}