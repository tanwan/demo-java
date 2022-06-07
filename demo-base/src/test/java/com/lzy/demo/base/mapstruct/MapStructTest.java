package com.lzy.demo.base.mapstruct;

import com.lzy.demo.base.mapstruct.entity.SimpleContainerDestination;
import com.lzy.demo.base.mapstruct.entity.SimpleContainerSource;
import com.lzy.demo.base.mapstruct.entity.SimpleDestination;
import com.lzy.demo.base.mapstruct.entity.SimpleEnumDestination;
import com.lzy.demo.base.mapstruct.entity.SimpleEnumSource;
import com.lzy.demo.base.mapstruct.entity.SimpleMulti;
import com.lzy.demo.base.mapstruct.entity.SimpleSource;
import com.lzy.demo.base.mapstruct.mapper.MultiSourceMapper;
import com.lzy.demo.base.mapstruct.mapper.SimpleAbstractMapper;
import com.lzy.demo.base.mapstruct.mapper.SimpleContainerMapper;
import com.lzy.demo.base.mapstruct.mapper.SimpleMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * 测试MapStruct
 *
 * @author lzy
 * @version v1.0
 */
public class MapStructTest {

    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2022, 1, 1, 1, 2, 3);
    private static final String DATE_TIME_STR = "2022-01-01 01:02:03";

    /**
     * 测试 source to destination
     */
    @Test
    public void testSourceToDestination() {
        SimpleSource source = SimpleSource.builder().integer(3).str("source").localDateTime(DATE_TIME)
                .differentSource("differentSource").simpleEnum(SimpleEnumSource.ENUM1).ignore("ignore").decuple(1).build();

        SimpleDestination destination = SimpleMapper.INSTANCE.map(source);

        System.out.println(destination);

        assertEquals(source.getInteger(), destination.getInteger());
        assertEquals(source.getStr(), destination.getStr());
        assertEquals(source.getLocalDateTime(), destination.getLocalDateTime());
        assertEquals(DATE_TIME_STR, destination.getStringLocalDateTime());
        assertEquals(source.getDifferentSource(), destination.getDifferentDestination());
        assertEquals(source.getSimpleEnum().name(), destination.getSimpleEnum().name());
        assertNull(destination.getIgnore());
        assertEquals(10, destination.getDecuple());
    }

    /**
     * 测试 destination to source
     */
    @Test
    public void testDestinationToSource() {
        SimpleDestination destination = new SimpleDestination();
        destination.setInteger(3);
        destination.setStr("destination");
        destination.setStringLocalDateTime(DATE_TIME_STR);
        destination.setDifferentDestination("differentDestination");
        destination.setSimpleEnum(SimpleEnumDestination.ENUM1);

        SimpleSource source = SimpleMapper.INSTANCE.map(destination);

        System.out.println(source);
        assertEquals(source.getInteger(), destination.getInteger());
        assertEquals(source.getStr(), destination.getStr());
        assertEquals(DATE_TIME, source.getLocalDateTime());
        assertEquals(source.getDifferentSource(), destination.getDifferentDestination());
        assertEquals(source.getSimpleEnum().name(), destination.getSimpleEnum().name());
    }

    /**
     * 测试容器
     */
    @Test
    public void testContainer() {
        SimpleContainerSource sourceContainer = new SimpleContainerSource();
        SimpleSource source = SimpleSource.builder().integer(3).str("source").localDateTime(DATE_TIME)
                .differentSource("differentSource").simpleEnum(SimpleEnumSource.ENUM1).build();
        sourceContainer.setSimpleSource(source);
        sourceContainer.setList(Collections.singletonList(source));
        sourceContainer.setSet(Collections.singleton(source));
        sourceContainer.setMap(Collections.singletonMap("source", source));

        SimpleContainerDestination destinationContainer = SimpleContainerMapper.INSTANCE.map(sourceContainer);
        System.out.println(destinationContainer);

        assertEquals(DATE_TIME_STR, destinationContainer.getSimpleDestination().getStringLocalDateTime());
        assertEquals(DATE_TIME_STR, destinationContainer.getList().get(0).getStringLocalDateTime());
        assertEquals(DATE_TIME_STR, new ArrayList<>(destinationContainer.getSet()).get(0).getStringLocalDateTime());
        assertEquals(DATE_TIME_STR, destinationContainer.getMap().get("source").getStringLocalDateTime());

    }


    /**
     * 测试before和after
     */
    @Test
    public void testBeforeAfterMapping() {
        SimpleSource source = SimpleSource.builder().integer(3).str("source").localDateTime(LocalDateTime.now()).build();
        SimpleDestination destination = SimpleAbstractMapper.INSTANCE.map(source);
        System.out.println(destination);
        assertEquals("beforeMapping", destination.getBeforeMapping());
        assertEquals(destination.getBeforeMapping().toUpperCase(), destination.getAfterMapping());
    }


    /**
     * 测试多个映射成一个
     */
    @Test
    public void testMultiMap() {
        SimpleSource source1 = SimpleSource.builder().integer(1).str("source1").build();
        SimpleSource source2 = SimpleSource.builder().integer(2).str("source2").build();

        SimpleMulti destination = MultiSourceMapper.INSTANCE.map(source1, source2);

        System.out.println(destination);
        assertEquals(source1.getStr(), destination.getStr());
        assertEquals(source2.getInteger(), destination.getInteger());
        assertEquals(source1.getStr(), destination.getDestination().getStr());
        assertEquals(source2.getInteger(), destination.getDestination().getInteger());
    }
}
