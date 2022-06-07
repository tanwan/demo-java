package com.lzy.demo.base.mapstruct.mapper;

import com.lzy.demo.base.mapstruct.entity.SimpleMulti;
import com.lzy.demo.base.mapstruct.entity.SimpleSource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MultiSourceMapper {

    MultiSourceMapper INSTANCE = Mappers.getMapper(MultiSourceMapper.class);


    /**
     * 从多个类映射成一个
     *
     * @param source1 the source 1
     * @param source2 the source 2
     * @return the simple multi
     */
    @Mapping(source = "source1.str", target = "str")
    @Mapping(source = "source2.integer", target = "integer")
    @Mapping(source = "source1.str", target = "destination.str")
    @Mapping(source = "source2.integer", target = "destination.integer")
    SimpleMulti map(SimpleSource source1, SimpleSource source2);
}
