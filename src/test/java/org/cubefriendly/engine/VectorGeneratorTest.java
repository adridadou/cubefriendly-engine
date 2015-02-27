package org.cubefriendly.engine;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by david on 26.02.15.
 */
public class VectorGeneratorTest {

    @Test
    public void should_take_sizes_as_constructor() {
        List<Integer> sizes = Lists.newArrayList(5,5,5);
        VectorGenerator vectorGenerator = new VectorGenerator(sizes);
        assertEquals(125,vectorGenerator.length.intValue());
    }

    @Test
    public void should_iterate_from_right_to_left() {
        List<Integer> sizes = Lists.newArrayList(2,2,2);
        VectorGenerator vectorGenerator = new VectorGenerator(sizes);
        List<int[]> expectedResult = Lists.newArrayList(new int[]{0,0,0},
        new int[]{0,0,1},
        new int[]{0,1,0},
        new int[]{0,1,1},
        new int[]{1,0,0},
        new int[]{1,0,1},
        new int[]{1,1,0},
        new int[]{1,1,1});
        for(int i = 0 ; i < vectorGenerator.length.intValue(); i++){
            int[] result = vectorGenerator.next();
            assertArrayEquals(expectedResult.get(i),result);
        }
        assertFalse(vectorGenerator.hasNext());
    }


}
