package com.lzy.demo.base.mapstruct.mapper;

import com.lzy.demo.base.mapstruct.entity.SimpleDestination;
import com.lzy.demo.base.mapstruct.entity.SimpleEnumDestination;
import com.lzy.demo.base.mapstruct.entity.SimpleEnumSource;
import com.lzy.demo.base.mapstruct.entity.SimpleSource;
import com.lzy.demo.base.mapstruct.entity.SimpleSource.SimpleSourceBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-06-06T15:02:49+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.3.1 (Oracle Corporation)"
)
public class SimpleMapperImpl implements SimpleMapper {

    @Override
    public SimpleDestination map(SimpleSource source) {
        if ( source == null ) {
            return null;
        }

        SimpleDestination simpleDestination = new SimpleDestination();

        simpleDestination.setDifferentDestination( source.getDifferentSource() );
        if ( source.getLocalDateTime() != null ) {
            simpleDestination.setStringLocalDateTime( DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" ).format( source.getLocalDateTime() ) );
        }
        simpleDestination.setDecuple( SimpleMapper.decuple( source.getDecuple() ) );
        simpleDestination.setStr( source.getStr() );
        simpleDestination.setInteger( source.getInteger() );
        simpleDestination.setLocalDateTime( source.getLocalDateTime() );
        simpleDestination.setSimpleEnum( simpleEnumSourceToSimpleEnumDestination( source.getSimpleEnum() ) );

        return simpleDestination;
    }

    @Override
    public SimpleSource map(SimpleDestination destination) {
        if ( destination == null ) {
            return null;
        }

        SimpleSourceBuilder simpleSource = SimpleSource.builder();

        simpleSource.differentSource( destination.getDifferentDestination() );
        if ( destination.getStringLocalDateTime() != null ) {
            simpleSource.localDateTime( LocalDateTime.parse( destination.getStringLocalDateTime(), DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" ) ) );
        }
        simpleSource.str( destination.getStr() );
        simpleSource.integer( destination.getInteger() );
        simpleSource.simpleEnum( simpleEnumDestinationToSimpleEnumSource( destination.getSimpleEnum() ) );
        simpleSource.ignore( destination.getIgnore() );
        simpleSource.decuple( destination.getDecuple() );

        return simpleSource.build();
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

    protected SimpleEnumSource simpleEnumDestinationToSimpleEnumSource(SimpleEnumDestination simpleEnumDestination) {
        if ( simpleEnumDestination == null ) {
            return null;
        }

        SimpleEnumSource simpleEnumSource;

        switch ( simpleEnumDestination ) {
            case ENUM1: simpleEnumSource = SimpleEnumSource.ENUM1;
            break;
            case ENUM2: simpleEnumSource = SimpleEnumSource.ENUM2;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + simpleEnumDestination );
        }

        return simpleEnumSource;
    }
}
