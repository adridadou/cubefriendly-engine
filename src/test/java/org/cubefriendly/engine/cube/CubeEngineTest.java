package org.cubefriendly.engine.cube;

import com.google.common.collect.Lists;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by david on 24.02.15.
 */
public class CubeEngineTest {

    @Test
    public void should_be_created_by_setting_the_name_and_data(){
        CubeDataBuilder cubeDataBuilder = CubeData.builder().name("new_name");
        cubeDataBuilder.add(Lists.newArrayList(1, 1, 1));
        cubeDataBuilder.add(Lists.newArrayList(2, 2, 2));
        cubeDataBuilder.add(Lists.newArrayList(1, 2, 3));
        CubeData cubeData = cubeDataBuilder.build();

        assertEquals("new_name",cubeData.name);
        assertEquals(3,cubeData.size());
    }
}
