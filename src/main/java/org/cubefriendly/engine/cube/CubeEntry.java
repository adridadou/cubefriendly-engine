package org.cubefriendly.engine.cube;

/**
 * Cubefriendly
 * Created by david on 19.03.15.
 */
public class CubeEntry {

    public final int[] vector;
    public final String[] metrics;

    public CubeEntry(int[] vector, String[] metrics) {
        this.vector = new int[vector.length];
        System.arraycopy(vector,0,this.vector,0,vector.length);
        if(metrics != null){
            this.metrics = new String[metrics.length];
            System.arraycopy(metrics,0,this.metrics,0,metrics.length);
        }else{
            this.metrics = new String[0];
        }
    }
}
