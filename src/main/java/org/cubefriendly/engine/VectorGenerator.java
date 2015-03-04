package org.cubefriendly.engine;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

/**
 * Created by david on 26.02.15.
 */
public class VectorGenerator implements Iterator<int[]>{
    private final int[] sizes;
    private int[] vector;
    private int[] returnValue;
    public final BigInteger length;
    private boolean eof = false;

    public VectorGenerator(List<Integer> sizes) {
        int[] tmpSizes = new int[sizes.size()];
        BigInteger total = BigInteger.ONE;
        for(int i = 0; i< sizes.size(); i++){
            tmpSizes[i] = sizes.get(i);
            total = total.multiply(BigInteger.valueOf(sizes.get(i)));
        }
        this.sizes = tmpSizes;
        this.length = total;
        this.vector = new int[sizes.size()];
        this.returnValue = new int[sizes.size()];
    }

    @Override
    public boolean hasNext() {
        return !eof;
    }

    @Override
    public int[] next() {
        System.arraycopy(vector,0,returnValue,0,vector.length);
        next(vector.length -1);
        return returnValue;
    }

    private void next(int pos){
        if(eof){
            throw new IndexOutOfBoundsException("the generator has no more values");
        }
        if(pos == -1){
            eof = true;
        }else{
            long current = vector[pos];
            if(current + 1 < sizes[pos]){
                vector[pos] += 1;
            }else{
                vector[pos] = 0;
                next(pos-1);
            }
        }

    }

    @Override
    public void remove() {
        throw new NotImplementedException();
    }


}
