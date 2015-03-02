package org.cubefriendly.engine.cube;

import org.cubefriendly.engine.VectorSelectionGenerator;
import org.mapdb.BTreeMap;

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
        vectorSelection.seek(data.ceilingKey(vectorSelection.getVector()));
    }


    @Override
    public boolean hasNext() {
        return vectorSelection.hasNext();
    }

    @Override
    public int[] next() {
        int[] reader = new int[vectorSelection.getVector().length];
        Map.Entry<int[], String> entry = data.ceilingEntry(vectorSelection.getVector());
        vectorSelection.seek(entry.getKey());
        System.arraycopy(vectorSelection.getVector(),0,reader,0,reader.length);
        vectorSelection.next();
        return reader;
    }
}
