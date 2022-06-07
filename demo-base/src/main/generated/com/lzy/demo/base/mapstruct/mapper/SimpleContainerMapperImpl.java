package com.lzy.demo.base.mapstruct.mapper;

import com.lzy.demo.base.mapstruct.entity.SimpleContainerDestination;
import com.lzy.demo.base.mapstruct.entity.SimpleContainerSource;
import com.lzy.demo.base.mapstruct.entity.SimpleDestination;
import com.lzy.demo.base.mapstruct.entity.SimpleSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.mapstruct.factory.Mappers;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-06-06T15:02:48+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.3.1 (Oracle Corporation)"
)
public class SimpleContainerMapperImpl implements SimpleContainerMapper {

    private final SimpleMapper simpleMapper = Mappers.getMapper( SimpleMapper.class );

    @Override
    public SimpleContainerDestination map(SimpleContainerSource source) {
        if ( source == null ) {
            return null;
        }

        SimpleContainerDestination simpleContainerDestination = new SimpleContainerDestination();

        simpleContainerDestination.setSimpleDestination( simpleMapper.map( source.getSimpleSource() ) );
        simpleContainerDestination.setList( simpleSourceListToSimpleDestinationList( source.getList() ) );
        simpleContainerDestination.setSet( simpleSourceSetToSimpleDestinationSet( source.getSet() ) );
        simpleContainerDestination.setMap( stringSimpleSourceMapToStringSimpleDestinationMap( source.getMap() ) );

        return simpleContainerDestination;
    }

    protected List<SimpleDestination> simpleSourceListToSimpleDestinationList(List<SimpleSource> list) {
        if ( list == null ) {
            return null;
        }

        List<SimpleDestination> list1 = new ArrayList<SimpleDestination>( list.size() );
        for ( SimpleSource simpleSource : list ) {
            list1.add( simpleMapper.map( simpleSource ) );
        }

        return list1;
    }

    protected Set<SimpleDestination> simpleSourceSetToSimpleDestinationSet(Set<SimpleSource> set) {
        if ( set == null ) {
            return null;
        }

        Set<SimpleDestination> set1 = new HashSet<SimpleDestination>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( SimpleSource simpleSource : set ) {
            set1.add( simpleMapper.map( simpleSource ) );
        }

        return set1;
    }

    protected Map<String, SimpleDestination> stringSimpleSourceMapToStringSimpleDestinationMap(Map<String, SimpleSource> map) {
        if ( map == null ) {
            return null;
        }

        Map<String, SimpleDestination> map1 = new HashMap<String, SimpleDestination>( Math.max( (int) ( map.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<String, SimpleSource> entry : map.entrySet() ) {
            String key = entry.getKey();
            SimpleDestination value = simpleMapper.map( entry.getValue() );
            map1.put( key, value );
        }

        return map1;
    }
}
