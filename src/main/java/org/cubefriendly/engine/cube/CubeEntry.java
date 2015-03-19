package org.cubefriendly.engine.cube;

/**
 * Cubefriendly
 * Created by david on 19.03.15.
 */
public class CubeEntry {

    public final int[] vector;
    public final String[] metrics;

    public CubeEntry(int[] vector, String[] metrics) {
        this.vector = vector;
        this.metrics = metrics;
    }
}
