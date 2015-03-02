package org.cubefriendly.engine;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;


/**
 * Created by david on 27.02.15.
 */
public class VectorSelectionGenerator {
    private final int[][]query;
    private final int[] vector;
    private boolean eof = false;

    public VectorSelectionGenerator(Map<Integer,List<Integer>> query, List<Integer> sizes) {
        this.query = new int[sizes.size()][];
        this.vector = new int[sizes.size()];
        for(int i = 0;i < sizes.size(); i++){
            List<Integer> current = query.getOrDefault(i, Lists.<Integer>newArrayList());
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
            int pos = seekPos(query[i],newPosition[i]);
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
        int pos = seekPos(query[index],vector[index] + 1);
        if(pos == -1) return reset(index - 1);
        vector[index] = query[index][pos];
        for(int i = 0 ; i < index - 1 ; i++){
            vector[i] = query[i][0];
        }
        return vector;
    }

    private int seekPos(int[] values, int value){
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
}
