package org.cubefriendly.engine.cube;

import org.cubefriendly.engine.VectorSelectionGenerator;
import org.mapdb.BTreeMap;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * Cubefriendly
 * Created by david on 02.03.15.
 */
public final class CubeDataIterator implements Iterator<int[]> {

    private final VectorSelectionGenerator vectorSelection;
    private final BTreeMap<int[],String> data;

    CubeDataIterator(VectorSelectionGenerator vectorSelection, BTreeMap<int[],String> data) {
        this.vectorSelection = vectorSelection;
        this.data = data;
        gotoNext();
    }


    @Override
    public boolean hasNext() {
        return vectorSelection.hasNext();
    }

    private void gotoNext(){
        boolean found = false;
        Map.Entry<int[], String> entry;
        while(!found && hasNext()){
            entry = data.ceilingEntry(vectorSelection.getVector());
            vectorSelection.seek(entry.getKey());
            found = Arrays.equals(entry.getKey(),vectorSelection.getVector());
        }
    }

    @Override
    public int[] next() {
        if(!hasNext()){
            throw new RuntimeException("EOF reached");
        }else{
            int[] reader = new int[vectorSelection.getVector().length];
            System.arraycopy(vectorSelection.getVector(),0,reader,0,reader.length);
            vectorSelection.next();
            gotoNext();
            return reader;
        }

    }

    @Override
    public void remove() {
        throw new RuntimeException("you cannot remove from the generator");
    }
}
