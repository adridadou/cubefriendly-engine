package org.cubefriendly.engine;

import com.google.common.collect.Lists;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by david on 27.02.15.
 */
public class VectorSelectionGenerator implements Iterable<int[]>, Iterator<int[]> {
    private final int[][]query;
    private final VectorGenerator generator;
    private final int[] vector;
    public final BigInteger length;


    public VectorSelectionGenerator(List<List<Integer>> query) {
        List<Integer> sizes = Lists.newArrayList();
        this.query = new int[query.size()][];

        for(int i = 0;i < query.size(); i++){
            List<Integer> current = query.get(i);
            sizes.add(current.size());
            this.query[i] = new int[current.size()];
            for(int j = 0; j < current.size(); j++){
                this.query[i][j] = current.get(j);
            }
        }
        BigInteger length = BigInteger.ONE;
        for(List<Integer> dim : query){
            length = length.multiply(BigInteger.valueOf(dim.size()));
        }
        this.generator = new VectorGenerator(sizes);
        this.length = length;
        this.vector = new int[query.size()];
    }

    @Override
    public Iterator<int[]> iterator() {
        return this;
    }

    @Override
    public void forEach(Consumer<? super int[]> action) {
        throw new NotImplementedException();
    }

    @Override
    public Spliterator<int[]> spliterator() {
        throw new NotImplementedException();
    }

    @Override
    public boolean hasNext() {
        return generator.hasNext();
    }

    @Override
    public int[] next() {
        int[] next = generator.next();
        System.arraycopy(next,0,vector,0,vector.length);
        for(int i = 0; i< next.length; i++){
            vector[i] = query[i][vector[i]];
        }
        return vector;
    }

    @Override
    public void remove() {
        throw new NotImplementedException();
    }

    @Override
    public void forEachRemaining(Consumer<? super int[]> action) {
        throw new NotImplementedException();
    }
}
