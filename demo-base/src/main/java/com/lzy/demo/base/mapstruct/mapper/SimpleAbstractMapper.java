package com.lzy.demo.base.mapstruct.mapper;

import com.lzy.demo.base.mapstruct.entity.SimpleDestination;
import com.lzy.demo.base.mapstruct.entity.SimpleSource;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * Mapper可以是接口也可以是抽象类
 * componentModel为spring的时候(@Mapper(componentModel = "spring"))则表示mapper是一个spring bean
 * 这时候可以为这个Mapper注入其它的bean
 * 然后在expression中使用@Mapping(target = "name", expression = "java(springBean.method(source.getName()))")
 *
 * @author lzy
 * @version v1.0
 */
@Mapper
public abstract class SimpleAbstractMapper {

    public static final SimpleAbstractMapper INSTANCE = Mappers.getMapper(SimpleAbstractMapper.class);

    @BeforeMapping
    protected void beforeMapping(SimpleSource source, @MappingTarget SimpleDestination destination) {
        destination.setBeforeMapping("beforeMapping");
    }

    @AfterMapping
    protected void afterMapping(SimpleSource source, @MappingTarget SimpleDestination destination) {
        destination.setAfterMapping(destination.getBeforeMapping().toUpperCase());
    }

    public abstract SimpleDestination map(SimpleSource source);
}
