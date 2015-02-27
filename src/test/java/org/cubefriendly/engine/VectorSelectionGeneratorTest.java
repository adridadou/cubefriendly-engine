package org.cubefriendly.engine;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by david on 27.02.15.
 */
public class VectorSelectionGeneratorTest {

    @Test
    public void should_returned_the_vector_according_to_selection(){
        List<Integer> dim1 = Lists.newArrayList(1,2);
        List<Integer> dim2 = Lists.newArrayList(2,3);
        List<Integer> dim3 = Lists.newArrayList(0,4);
        VectorSelectionGenerator generator = new VectorSelectionGenerator(Lists.newArrayList(dim1,dim2,dim3));
        List<int[]> expectedResult = Lists.newArrayList(new int[]{1,2,0},
                new int[]{1,2,4},
                new int[]{1,3,0},
                new int[]{1,3,4},
                new int[]{2,2,0},
                new int[]{2,2,4},
                new int[]{2,3,0},
                new int[]{2,3,4});
        for(int i = 0 ; i < generator.length.intValue(); i++){
            int[] result = generator.next();
            assertArrayEquals(expectedResult.get(i),result);
        }
        assertFalse(generator.hasNext());
    }
}
