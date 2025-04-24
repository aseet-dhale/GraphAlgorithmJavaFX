package org.graphalgorithm.algorithm;

import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.graphalgorithm.utils.BlockType;

import java.util.*;


public class ShortestDistanceWithQueue {
    private final static String SEARCHED_BLOCK_COLOR = "#bdb2ff";   // Soft lavender
    private static Set<Tuple<Integer, Integer>> visited;
    private static Map<Tuple<Integer, Integer>, Integer> dist;
    private static Map<Tuple<Integer, Integer>, Tuple<Integer, Integer>> prev;
    private static BlockType[][] gridCopy;

    public static List<Tuple<Integer, Integer>> solveGrid(Group blocks, BlockType[][] grid) {
        visited = new HashSet<>();
        dist = new HashMap<>();
        prev = new HashMap<>();
        gridCopy = grid;
        Tuple<Integer, Integer> start = new Tuple<>(0, 0);
        int height = grid.length, width = grid[0].length;
        fillMapWithInitialDistance(dist, height, width);
        dist.put(start, 0);
        Queue<Tuple<Integer, Integer>> queue = new LinkedList<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            // get neighbours
            Tuple<Integer, Integer> current = queue.poll();
            visited.add(current);
            if (grid[current.x][current.y] == BlockType.DESTINATION) {
                List<Tuple<Integer, Integer>> seq = new ArrayList<>();
                while (prev.containsKey(current)) {
                    seq.add(current);
                    current = prev.get(current);
                }
                return seq;
            }
            Rectangle rectangle = (Rectangle) (((Group) (blocks.getChildren().get(current.x))).getChildren().get(
                    current.y));
            if (current.x != 0 || current.y != 0) {
                transition(rectangle);
            }
            List<Tuple<Integer, Integer>> neighbours = getNextNeighboursToVisit(current, height, width);
            for (Tuple<Integer, Integer> neighbour : neighbours) {
                int temp = dist.get(current) + 1;
                if (temp < dist.get(neighbour)) {
                    dist.put(neighbour, temp);
                    prev.put(neighbour, current);
                }
            }
            queue.addAll(neighbours);
        }
        return new ArrayList<>();
    }

    private static void fillMapWithInitialDistance(Map<Tuple<Integer, Integer>, Integer> dist, int height, int width) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                dist.put(new Tuple<>(i, j), Integer.MAX_VALUE);
            }
        }
    }

    private static void transition(Rectangle rectangle) {
        FillTransition fillTransition = new FillTransition(Duration.millis(1000), rectangle);
        fillTransition.setToValue(Color.web(SEARCHED_BLOCK_COLOR));
        fillTransition.setInterpolator(Interpolator.LINEAR);
        fillTransition.play();
    }

    private static List<Tuple<Integer, Integer>> getNextNeighboursToVisit(Tuple<Integer, Integer> current, int height
            , int width) {
        List<Tuple<Integer, Integer>> tuples = new ArrayList<>();
        if (current.x - 1 >= 0 && !visited.contains(
                new Tuple<>(current.x - 1, current.y)) && gridCopy[current.x - 1][current.y] != BlockType.BRICK) {
            tuples.add(new Tuple<>(current.x - 1, current.y));
        }
        if (current.x + 1 < height && !visited.contains(
                new Tuple<>(current.x + 1, current.y)) && gridCopy[current.x + 1][current.y] != BlockType.BRICK) {
            tuples.add(new Tuple<>(current.x + 1, current.y));
        }
        if (current.y - 1 >= 0 && !visited.contains(
                new Tuple<>(current.x, current.y - 1)) && gridCopy[current.x][current.y - 1] != BlockType.BRICK) {
            tuples.add(new Tuple<>(current.x, current.y - 1));
        }
        if (current.y + 1 < width && !visited.contains(
                new Tuple<>(current.x, current.y + 1)) && gridCopy[current.x][current.y + 1] != BlockType.BRICK) {
            tuples.add(new Tuple<>(current.x, current.y + 1));
        }
        return tuples;
    }
}
