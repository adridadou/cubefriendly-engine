package org.cubefriendly.engine.cube;

import org.mapdb.BTreeMap;

/**
 * Created by david on 27.02.15.
 */
public class CubeData {

    public final String name;
    private final BTreeMap<int[],String> data;

    public CubeData(String name, BTreeMap<int[],String> data) {
        this.name = name;
        this.data = data;
    }

    public int size() {
        return data.size();
    }

    public static CubeDataBuilder builder() {
        return new CubeDataBuilder();
    }
}
