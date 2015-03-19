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

    public final String name;
    private final BTreeMap<int[],String[]> data;
    private final List<Integer> sizes;

    public CubeData(String name, BTreeMap<int[],String[]> data, List<Integer> sizes) {
        this.name = name;
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

    public Iterator<int[]> query(Map<Integer, List<Integer>> query) {
        return new CubeDataIterator(new VectorSelectionGenerator(query, sizes),data);
    }
}
