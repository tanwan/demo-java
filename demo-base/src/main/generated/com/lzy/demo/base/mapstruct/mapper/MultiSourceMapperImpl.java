package com.lzy.demo.base.mapstruct.mapper;

import com.lzy.demo.base.mapstruct.entity.SimpleDestination;
import com.lzy.demo.base.mapstruct.entity.SimpleMulti;
import com.lzy.demo.base.mapstruct.entity.SimpleSource;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-06-06T15:02:49+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.3.1 (Oracle Corporation)"
)
public class MultiSourceMapperImpl implements MultiSourceMapper {

    @Override
    public SimpleMulti map(SimpleSource source1, SimpleSource source2) {
        if ( source1 == null && source2 == null ) {
            return null;
        }

        SimpleMulti simpleMulti = new SimpleMulti();

        if ( source1 != null ) {
            if ( simpleMulti.getDestination() == null ) {
                simpleMulti.setDestination( new SimpleDestination() );
            }
            simpleSourceToSimpleDestination( source1, simpleMulti.getDestination() );
            simpleMulti.setStr( source1.getStr() );
        }
        if ( source2 != null ) {
            if ( simpleMulti.getDestination() == null ) {
                simpleMulti.setDestination( new SimpleDestination() );
            }
            simpleSourceToSimpleDestination1( source2, simpleMulti.getDestination() );
            simpleMulti.setInteger( source2.getInteger() );
        }

        return simpleMulti;
    }

    protected void simpleSourceToSimpleDestination(SimpleSource simpleSource, SimpleDestination mappingTarget) {
        if ( simpleSource == null ) {
            return;
        }

        mappingTarget.setStr( simpleSource.getStr() );
    }

    protected void simpleSourceToSimpleDestination1(SimpleSource simpleSource, SimpleDestination mappingTarget) {
        if ( simpleSource == null ) {
            return;
        }

        mappingTarget.setInteger( simpleSource.getInteger() );
    }
}
