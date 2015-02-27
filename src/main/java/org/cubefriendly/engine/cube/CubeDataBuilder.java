package org.cubefriendly.engine.cube;

import org.mapdb.*;

import java.io.File;
import java.util.List;

/**
 * Created by david on 27.02.15.
 */
public class CubeDataBuilder {
    private String name;
    private DB db;
    private BTreeMap<int[],String> data;

    public CubeDataBuilder name(String name){
        this.name = name;
        File file = new File(name + ".cube");
        this.db = DBMaker.newFileDB(file).cacheDisable().lockThreadUnsafeEnable().mmapFileEnableIfSupported().transactionDisable().make();
        this.data = db.<int[],String>createTreeMap("data").keySerializer(Serializer.INT_ARRAY).valueSerializer(Serializer.STRING_ASCII).make();
        return this;
    }

    public CubeDataBuilder add(List<Integer> vector) {
        int[] key = new int[vector.size()];
        data.put(key,""); //FUTUR_WORK: right now we save an empty string as the value. Can be useful in the futur
        return this;
    }

    public CubeData build() {
        return null;
    }
}
