package com.lzy.demo.base.mapstruct.mapper;

import com.lzy.demo.base.mapstruct.entity.SimpleContainerDestination;
import com.lzy.demo.base.mapstruct.entity.SimpleContainerSource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * uses可以使用其它的Mapper
 *
 * @author lzy
 * @version v1.0
 */
@Mapper(uses = {SimpleMapper.class})
public interface SimpleContainerMapper {

    SimpleContainerMapper INSTANCE = Mappers.getMapper(SimpleContainerMapper.class);

    @Mapping(source = "source.simpleSource", target = "simpleDestination")
    SimpleContainerDestination map(SimpleContainerSource source);
}
