package com.lzy.demo.mybatis.typehandler;

import com.lzy.demo.mybatis.enums.UseIndexEnum;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import org.apache.ibatis.type.MappedTypes;

/**
 * 由于typeHandler泛型的限制,不能直接使用EnumOrdinalTypeHandler,因此需要继承EnumOrdinalTypeHandler
 * @author lzy
 * @version v1.0
 */
@MappedTypes(UseIndexEnum.class)
public class CustomUseIndexEnumTypeHandler extends EnumOrdinalTypeHandler<UseIndexEnum> {
    public CustomUseIndexEnumTypeHandler(Class<UseIndexEnum> type) {
        super(type);
    }
}
