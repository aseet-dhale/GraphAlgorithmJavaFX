package org.graphalgorithm.utils;

import java.util.Random;

public class GenerateGrid {
    private int height = 100;
    private int width = 100;
    private BlockType[][] grid;

    public GenerateGrid() {
        grid = new BlockType[this.height][this.width];
    }

    public GenerateGrid(int height, int width) {
        this.height = height;
        this.width = width;
        grid = new BlockType[height][width];
    }

    BlockType getRandomBlock() {
        return new Random().nextDouble() < 0.75 ? BlockType.PATH : BlockType.BRICK;
    }

    public BlockType[][] generateGrid() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = getRandomBlock();
            }
        }
        // Starting block cannot be brick
        grid[0][0] = BlockType.PATH;
        // Set destination block
        int i = Math.abs(new Random().nextInt()) % height;
        int j = Math.abs(new Random().nextInt()) % width;

        grid[i][j] = BlockType.DESTINATION;

        return grid;
    }

}