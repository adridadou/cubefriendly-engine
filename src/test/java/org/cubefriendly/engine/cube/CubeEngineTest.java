package org.cubefriendly.engine.cube;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Cubefriendly
 * Created by david on 24.02.15.
 */
public class CubeEngineTest {

    @Test
    public void should_be_created_by_setting_the_name_and_data(){
        DB db = DBMaker.tempFileDB().transactionDisable().fileMmapCleanerHackEnable().make();
        CubeDataBuilder cubeDataBuilder = CubeData.builder(db);
        cubeDataBuilder.add(Lists.newArrayList(1, 1, 1), Lists.newArrayList("12002","..."));
        cubeDataBuilder.add(Lists.newArrayList(2, 2, 2), Lists.newArrayList("12002","..."));
        cubeDataBuilder.add(Lists.newArrayList(1, 2, 3), Lists.newArrayList("12002","..."));
        CubeData cubeData = cubeDataBuilder.build();

        assertEquals(3,cubeData.size());
    }

    @Test
    public void subset_of_data_should_be_selected(){
        DB db = DBMaker.tempFileDB().transactionDisable().fileMmapCleanerHackEnable().make();
        CubeDataBuilder cubeDataBuilder = CubeData.builder(db);
        cubeDataBuilder.add(Lists.newArrayList(2, 2, 2), Lists.newArrayList("12002","..."));
        cubeDataBuilder.add(Lists.newArrayList(3, 3, 3), Lists.newArrayList("12002","..."));
        cubeDataBuilder.add(Lists.newArrayList(1, 2, 3), Lists.newArrayList("12002","..."));
        cubeDataBuilder.add(Lists.newArrayList(1, 1, 1), Lists.newArrayList("12002","..."));
        cubeDataBuilder.add(Lists.newArrayList(1, 1, 2), Lists.newArrayList("12002","..."));

        CubeData cubeData = cubeDataBuilder.build();

        Map<Integer, List<Integer>> query = ImmutableMap.<Integer, List<Integer>>builder()
                .put(0, Lists.newArrayList(1))
                .put(1, Lists.newArrayList(1)).build();

        Iterator<CubeEntry> it = cubeData.query(query);
        List<CubeEntry> result = Lists.newArrayList(it);
        assertEquals(2, result.size());
        assertArrayEquals(new int[]{1, 1, 1}, result.get(0).vector);
        assertArrayEquals(new int[]{1, 1, 2}, result.get(1).vector);
        assertArrayEquals(new int[]{3, 3, 3}, cubeData.getSizes());
    }

    @Test
    public void test_seek_with_missing_values(){
        DB db = DBMaker.tempFileDB().transactionDisable().fileMmapCleanerHackEnable().make();
        CubeDataBuilder cubeDataBuilder = CubeData.builder(db);
        cubeDataBuilder.add(Lists.newArrayList(2, 2, 2), Lists.newArrayList("12002","..."));
        cubeDataBuilder.add(Lists.newArrayList(3, 3, 3), Lists.newArrayList("12003","..."));
        cubeDataBuilder.add(Lists.newArrayList(4, 4, 4), Lists.newArrayList("12004","..."));
        cubeDataBuilder.add(Lists.newArrayList(5, 5, 5), Lists.newArrayList("12005","..."));
        cubeDataBuilder.add(Lists.newArrayList(1, 1, 1), Lists.newArrayList("12006","..."));

        CubeData cubeData = cubeDataBuilder.build();

        Map<Integer, List<Integer>> query = ImmutableMap.<Integer, List<Integer>>builder()
                .put(0, Lists.newArrayList(1,3)).build();

        Iterator<CubeEntry> it = cubeData.query(query);
        List<CubeEntry> result = Lists.newArrayList(it);
        assertArrayEquals(new int[]{1,1,1},result.get(0).vector);
        assertArrayEquals(new String[]{"12006", "..."}, result.get(0).metrics);
        assertArrayEquals(new int[]{3,3,3},result.get(1).vector);
        assertArrayEquals(new String[]{"12003","..."},result.get(1).metrics);
        assertEquals(2, result.size());
    }

    @Test
    public void failing_case_on_core(){
        DB db = DBMaker.tempFileDB().transactionDisable().fileMmapCleanerHackEnable().make();
        CubeDataBuilder cubeDataBuilder = CubeData.builder(db);
        cubeDataBuilder.add(Lists.newArrayList(1, 1, 1), Lists.newArrayList("12002","..."));
        cubeDataBuilder.add(Lists.newArrayList(2, 2, 1), Lists.newArrayList("12003","..."));
        cubeDataBuilder.add(Lists.newArrayList(2, 2, 2), Lists.newArrayList("12004","..."));
        cubeDataBuilder.add(Lists.newArrayList(1, 1, 2), Lists.newArrayList("12005","..."));

        CubeData cubeData = cubeDataBuilder.build();

        Map<Integer, List<Integer>> query = ImmutableMap.<Integer, List<Integer>>builder()
                .put(1, Lists.newArrayList(1)).build();

        Iterator<CubeEntry> it = cubeData.query(query);
        List<CubeEntry> result = Lists.newArrayList(it);
        assertArrayEquals(new int[]{1,1,1},result.get(0).vector);
        assertArrayEquals(new int[]{1,1,2},result.get(1).vector);
        assertEquals(2,result.size());

    }

    @Test
    public void to_metric(){
        DB db = DBMaker.tempFileDB().transactionDisable().fileMmapCleanerHackEnable().make();
        CubeDataBuilder cubeDataBuilder = CubeData.builder(db);
        cubeDataBuilder.add(Lists.newArrayList(1, 1, 1), Lists.newArrayList("12002","..."));
        cubeDataBuilder.add(Lists.newArrayList(2, 2, 1), Lists.newArrayList("12003","..."));
        cubeDataBuilder.add(Lists.newArrayList(2, 2, 2), Lists.newArrayList("12004","..."));
        cubeDataBuilder.add(Lists.newArrayList(1, 1, 2), Lists.newArrayList("12005","..."));

        CubeData cubeData = cubeDataBuilder.build();

        Map<Integer,String> values = Maps.newHashMap();
        values.put(1,"2394");
        values.put(2,"3949");
        cubeData.toMetric(1,values);
        cubeData.toMetric(1,values);

        Map<Integer, List<Integer>> query = ImmutableMap.<Integer, List<Integer>>builder()
                .put(1, Lists.newArrayList(1)).build();

        Iterator<CubeEntry> it = cubeData.query(query);
        List<CubeEntry> result = Lists.newArrayList(it);
        assertArrayEquals(new int[]{1},result.get(0).vector);
        assertArrayEquals(new int[]{2},result.get(1).vector);
        assertEquals(2,result.size());
    }
}
