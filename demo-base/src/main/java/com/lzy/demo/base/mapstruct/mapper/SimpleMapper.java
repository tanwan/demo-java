package com.lzy.demo.base.mapstruct.mapper;

import com.lzy.demo.base.mapstruct.entity.SimpleDestination;
import com.lzy.demo.base.mapstruct.entity.SimpleSource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

/**
 * Mapper可以是接口也可以是抽象类
 * 本质是生成该接口的实现类
 *
 * @author lzy
 * @version v1.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SimpleMapper {

    SimpleMapper INSTANCE = Mappers.getMapper(SimpleMapper.class);

    /**
     * source=""和target=""用户映射名称不一样的字段
     * dateFormat=""可以指定格式化的样式
     * defaultValue=""可以指定默认值
     * expression=""可以使用表达式
     * defaultExpression=""可以设置默认值的表达式
     * ignore=true忽略字段
     * qualifiedByName=""使用@Named注解的方法对值进行映射
     * 详细请看Mapping的javadoc
     *
     * @param source the source
     * @return the simple destination
     */
    @Mapping(source = "source.differentSource", target = "differentDestination")
    @Mapping(source = "localDateTime", target = "stringLocalDateTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(source = "decuple", target = "decuple", qualifiedByName = "decuple")
    @Mapping(target = "ignore", ignore = true)
    SimpleDestination map(SimpleSource source);

    @Mapping(source = "destination.differentDestination", target = "differentSource")
    @Mapping(source = "stringLocalDateTime", target = "localDateTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    SimpleSource map(SimpleDestination destination);

    @Named("decuple")
    static Integer decuple(Integer source) {
        return Optional.ofNullable(source).map(s -> 10 * s).orElse(null);
    }
}
