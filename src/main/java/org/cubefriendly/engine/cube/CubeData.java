package org.cubefriendly.engine.cube;

import com.google.common.collect.Lists;
import org.cubefriendly.engine.VectorSelectionGenerator;
import org.mapdb.BTreeMap;
import org.mapdb.DB;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by david on 27.02.15.
 */
public class CubeData {

    public final String name;
    private final BTreeMap<int[],String> data;
    private final List<Integer> sizes;

    public CubeData(String name, BTreeMap<int[],String> data, List<Integer> sizes) {
        this.name = name;
        this.data = data;
        this.sizes = sizes;
    }

    public int size() {
        return data.size();
    }

    public static CubeDataBuilder builder(DB db) {
        return new CubeDataBuilder(db);
    }

    public Iterator<int[]> query(Map<Integer, List<Integer>> query) {

        return null;
    }
}
