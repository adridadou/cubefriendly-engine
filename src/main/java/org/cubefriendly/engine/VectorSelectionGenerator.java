package org.cubefriendly.engine;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;


/**
 * Cubefriendly
 * Created by david on 27.02.15.
 */
public class VectorSelectionGenerator {
    private final int[][]query;
    private final int[] vector;
    private boolean eof = false;
    private final List<Integer> sizes;

    public VectorSelectionGenerator(Map<Integer,List<Integer>> query, List<Integer> sizes) {
        this.query = new int[sizes.size()][];
        this.vector = new int[sizes.size()];
        this.sizes = sizes;
        for(int i = 0;i < sizes.size(); i++){
            List<Integer> current = query.get(i);
            if(current == null){
                current = Lists.newArrayList();
            }
            this.query[i] = new int[current.size()];
            for(int j = 0; j < current.size(); j++){
                this.query[i][j] = current.get(j);
            }
            if(this.query[i].length > 0){
                this.vector[i] = this.query[i][0];
            }
        }

    }
    
    public boolean hasNext() {
        return !eof;
    }

    public int[] seek(int[] newPosition) {
        for(int i = 0; i< query.length ;i++){
            int pos = seekPos(newPosition[i],i);
            vector[i] = newPosition[i];
            if (pos == -1){
                return reset(i - 1);
            }
        }
        return vector;
    }

    private int[] reset(int index){
        if(index == -1){
            eof = true;
            return vector;
        }
        int pos = seekPos(vector[index] + 1,index);
        if(pos == -1) return reset(index - 1);
        vector[index] = query[index].length == 0 ? pos : query[index][pos];
        for(int i = index + 1 ; i < query.length ; i++){
            vector[i] = query[i].length == 0 ? 0 : query[i][0];
        }
        return vector;
    }

    private int seekPos(int value, int index){
        int[] values = query[index];
        if(values.length == 0){
            return sizes.get(index) > value ? value : -1;
        }
        for(int i = 0; i < values.length ;i++){
            if(values[i] >= value) {
                return i;
            }
        }
        return -1;
    }

    public int[] getVector() {
        return vector;
    }

    public void next() {
        int[] newPosition = new int[vector.length];
        System.arraycopy(vector,0,newPosition,0,vector.length);
        newPosition[newPosition.length-1]++;
        seek(newPosition);
    }
}
