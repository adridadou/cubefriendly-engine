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
    private BTreeMap<int[],String[]> data;
    private List<Integer> sizes;

    public CubeDataBuilder(DB db) {
        this.db = db;
        this.data = db.<int[],String>createTreeMap("__data__")
                .comparator(Fun.INT_ARRAY_COMPARATOR)
                .keySerializer(Serializer.INT_ARRAY).make();
    }

    public CubeDataBuilder name(String name){
        this.name = name;
        return this;
    }

    public CubeDataBuilder add(List<Integer> dimensions,List<String> metrics) {
        int[] key = new int[dimensions.size()];
        if(sizes == null){
            sizes = Lists.newArrayList(dimensions);
        }
        for(int i = 0; i < key.length ; i++){
            key[i] = dimensions.get(i);
            sizes.add(i,Math.max(sizes.get(i),dimensions.get(i)));
            sizes.remove(i + 1);
        }
        data.put(key,metrics.toArray(new String[metrics.size()])); //FUTUR_WORK: right now we save an empty string as the value. Can be useful in the future
        return this;
    }

    public CubeData build() {
        db.commit();
        return new CubeData(name,data,sizes);
    }
}
