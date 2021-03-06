package org.cubefriendly.engine.cube;

import com.google.common.collect.Lists;
import org.cubefriendly.engine.VectorSelectionGenerator;
import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.Fun;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Cubefriendly
 * Created by david on 27.02.15.
 */
public class CubeData {

    private final DB db;

    private final BTreeMap<int[],String[]> data;
    private final List<Integer> sizes;

    public CubeData(BTreeMap<int[],String[]> data, List<Integer> sizes, DB db) {
        this.data = data;
        this.sizes = sizes;
        this.db = db;
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

    public CubeData toMetric(int index, Map<Integer, String> values) {
        sizes.remove(index);
        BTreeMap<int[], String[]> dest = db.treeMapCreate("dest").comparator(Fun.INT_ARRAY_COMPARATOR).makeOrGet();
        dest.clear();
        db.commit();

        for(Map.Entry<int[],String[]> entry : data.entrySet()){
            int[] newKey = new int[sizes.size()];
            System.arraycopy(entry.getKey(), 0, newKey, 0, index);
            if(index < entry.getKey().length -1){
                    System.arraycopy(entry.getKey(),index + 1,newKey,index,entry.getKey().length - index - 1);
            }
            List<String> newMetrics = Lists.newArrayList(entry.getValue());
            newMetrics.add(values.get(entry.getKey()[index]));
            dest.put(newKey, newMetrics.toArray(new String[newMetrics.size()]));
        }
        data.clear();
        db.commit();

        //TODO: do this with Pump
        for(Map.Entry<int[],String[]> entry : dest.entrySet()){
            data.put(entry.getKey(),entry.getValue());
        }
        db.commit();

        return this;
    }
}
