package org.cubefriendly.engine.cube;

import org.cubefriendly.CubefriendlyException;
import org.cubefriendly.engine.VectorSelectionGenerator;
import org.mapdb.BTreeMap;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * Cubefriendly
 * Created by david on 02.03.15.
 */
public final class CubeDataIterator implements Iterator<CubeEntry> {

    private final VectorSelectionGenerator vectorSelection;
    private final BTreeMap<int[],String[]> data;
    private boolean eof = false;

    CubeDataIterator(VectorSelectionGenerator vectorSelection, BTreeMap<int[],String[]> data) {
        this.vectorSelection = vectorSelection;
        this.data = data;
        gotoNext();
    }

    @Override
    public boolean hasNext() {
        return vectorSelection.hasNext() && !eof;
    }

    private void gotoNext(){
        boolean found = false;
        Map.Entry<int[], String[]> entry;
        while(!found && hasNext()){
            entry = data.ceilingEntry(vectorSelection.getVector());
            if(entry != null) {
                vectorSelection.seek(entry.getKey());
                found = Arrays.equals(entry.getKey(), vectorSelection.getVector());
            }else {
                eof = true;
            }
        }
    }

    @Override
    public CubeEntry next() {
        if(!hasNext()){
            throw new CubefriendlyException("EOF reached");
        }else{
            CubeEntry entry = new CubeEntry(vectorSelection.getVector(),data.get(vectorSelection.getVector()));
            vectorSelection.next();
            gotoNext();
            return entry;
        }

    }
}
