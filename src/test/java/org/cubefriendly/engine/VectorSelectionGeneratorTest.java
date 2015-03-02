package org.cubefriendly.engine;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Cubefriendly
 * Created by david on 27.02.15.
 */
public class VectorSelectionGeneratorTest {

    @Test
    public void should_be_able_to_set_new_value() {
        List<Integer> sizes = Lists.newArrayList(5,5,2,2);
        Map<Integer,List<Integer>> query = ImmutableMap.<Integer,List<Integer>>builder()
                .put(0, Lists.newArrayList(1, 2))
                .put(1,Lists.newArrayList(2))
                .put(2,Lists.newArrayList(0)).build();
        VectorSelectionGenerator generator = new VectorSelectionGenerator(query,sizes);
        generator.seek(new int[]{1,2,1,1});
        assertArrayEquals(new int[]{2,2,0,0},generator.getVector());
        assertTrue(generator.hasNext());
    }

    @Test
    public void should_be_able_to_set_new_value_even_if_one_index_does_not_exist() {
        List<Integer> sizes = Lists.newArrayList(5,5,2,2);
        Map<Integer,List<Integer>> query = ImmutableMap.<Integer,List<Integer>>builder()
                .put(0, Lists.newArrayList(1, 2))
                .put(1,Lists.newArrayList(2))
                .put(2,Lists.newArrayList(0)).build();
        VectorSelectionGenerator generator = new VectorSelectionGenerator(query,sizes);
        generator.seek(new int[]{1,2,0,7});
        assertArrayEquals(new int[]{2, 2, 0, 0}, generator.getVector());
        assertTrue(generator.hasNext());
    }

    @Test
    public void should_set_eof_if_seek_gets_too_far() {
        List<Integer> sizes = Lists.newArrayList(5,5,2,2);
        Map<Integer,List<Integer>> query = ImmutableMap.<Integer,List<Integer>>builder()
                .put(0, Lists.newArrayList(1, 2))
                .put(1,Lists.newArrayList(2))
                .put(2,Lists.newArrayList(0)).build();
        VectorSelectionGenerator generator = new VectorSelectionGenerator(query,sizes);
        generator.seek(new int[]{2,2,1,1});
        assertFalse(generator.hasNext());
    }

    @Test
    public void should_set_eof_if_seek_gets_too_far_from_the_beginning() {
        List<Integer> sizes = Lists.newArrayList(5,5,2,2);
        Map<Integer,List<Integer>> query = ImmutableMap.<Integer,List<Integer>>builder()
                .put(0, Lists.newArrayList(1, 2))
                .put(1,Lists.newArrayList(2))
                .put(2,Lists.newArrayList(0)).build();
        VectorSelectionGenerator generator = new VectorSelectionGenerator(query,sizes);
        generator.seek(new int[]{7,2,1,1});
        assertFalse(generator.hasNext());
    }
}
