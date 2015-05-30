package org.cubefriendly.engine.cube;

import org.cubefriendly.engine.VectorSelectionGenerator;
import org.mapdb.BTreeMap;
import org.mapdb.DB;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Cubefriendly
 * Created by david on 27.02.15.
 */
public class CubeData {

    private final BTreeMap<int[],String[]> data;
    private final List<Integer> sizes;

    public CubeData(BTreeMap<int[],String[]> data, List<Integer> sizes) {
        this.data = data;
        this.sizes = sizes;
    }

    public int size() {
        return data.size();
    }

    public int[] getSizes(){
        int[] result = new int[sizes.size()];
        for(int i = 0; i < sizes.size(); i++){
            result[i] = sizes.get(i);
        }
        return result;
    }

    public static CubeDataBuilder builder(DB db) {
        return new CubeDataBuilder(db);
    }

    public Iterator<CubeEntry> query(Map<Integer, List<Integer>> query) {
        return new CubeDataIterator(new VectorSelectionGenerator(query, sizes),data);
    }
}
