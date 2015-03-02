package org.cubefriendly.engine.cube;

import com.google.common.collect.Lists;
import org.mapdb.*;

import java.util.List;

/**
 * Cubefriendly
 * Created by david on 27.02.15.
 */
public class CubeDataBuilder {
    private String name;
    private final DB db;
    private BTreeMap<int[],String> data;
    private List<Integer> sizes;

    public CubeDataBuilder(DB db) {
        this.db = db;
        this.data = db.<int[],String>createTreeMap("__data__")
                .comparator(Fun.INT_ARRAY_COMPARATOR)
                .keySerializer(Serializer.INT_ARRAY)
                .valueSerializer(Serializer.STRING_ASCII).make();
    }

    public CubeDataBuilder name(String name){
        this.name = name;
        return this;
    }

    public CubeDataBuilder add(List<Integer> vector) {
        int[] key = new int[vector.size()];
        if(sizes == null){
            sizes = Lists.newArrayList(vector);
        }
        for(int i = 0; i < key.length ; i++){
            key[i] = vector.get(i);
            sizes.add(i,Math.max(sizes.get(i),vector.get(i)));
        }
        data.put(key,""); //FUTUR_WORK: right now we save an empty string as the value. Can be useful in the futur
        return this;
    }

    public CubeData build() {
        db.commit();
        return new CubeData(name,data,sizes);
    }
}
