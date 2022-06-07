package com.lzy.demo.base.mapstruct.mapper;

import com.lzy.demo.base.mapstruct.entity.SimpleDestination;
import com.lzy.demo.base.mapstruct.entity.SimpleEnumDestination;
import com.lzy.demo.base.mapstruct.entity.SimpleEnumSource;
import com.lzy.demo.base.mapstruct.entity.SimpleSource;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-06-06T15:02:48+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.3.1 (Oracle Corporation)"
)
public class SimpleAbstractMapperImpl extends SimpleAbstractMapper {

    @Override
    public SimpleDestination map(SimpleSource source) {
        if ( source == null ) {
            return null;
        }

        SimpleDestination simpleDestination = new SimpleDestination();

        beforeMapping( source, simpleDestination );

        simpleDestination.setStr( source.getStr() );
        simpleDestination.setInteger( source.getInteger() );
        simpleDestination.setLocalDateTime( source.getLocalDateTime() );
        simpleDestination.setSimpleEnum( simpleEnumSourceToSimpleEnumDestination( source.getSimpleEnum() ) );
        simpleDestination.setIgnore( source.getIgnore() );
        simpleDestination.setDecuple( source.getDecuple() );

        afterMapping( source, simpleDestination );

        return simpleDestination;
    }

    protected SimpleEnumDestination simpleEnumSourceToSimpleEnumDestination(SimpleEnumSource simpleEnumSource) {
        if ( simpleEnumSource == null ) {
            return null;
        }

        SimpleEnumDestination simpleEnumDestination;

        switch ( simpleEnumSource ) {
            case ENUM1: simpleEnumDestination = SimpleEnumDestination.ENUM1;
            break;
            case ENUM2: simpleEnumDestination = SimpleEnumDestination.ENUM2;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + simpleEnumSource );
        }

        return simpleEnumDestination;
    }
}
