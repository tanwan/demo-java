package com.lzy.demo.base.protobuf;

import com.google.protobuf.UninitializedMessageException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class ProtoBufTest {
    /**
     * 测试required
     */
    @Test
    public void testRequired() {
        // proto2必填字段无值会抛异常
        assertThatCode(() -> SimpleProtos2.SimpleProtoBufOuter2.newBuilder().setInt64Field(64L).build())
                .isInstanceOf(UninitializedMessageException.class);

        // proto3没有必填字段
        assertThatCode(() -> SimpleProtos3.SimpleProtoBufOuter3.newBuilder().setInt64Field(64L).build())
                .doesNotThrowAnyException();
    }

    /**
     * 测试默认值
     */
    @Test
    public void testDefault() {
        // proto2才有默认值
        SimpleProtos2.SimpleProtoBufOuter2 simpleProtoBufOuter2 = SimpleProtos2.SimpleProtoBufOuter2.newBuilder().setInt64Field(64L).setInt32Field(32)
                .setSimpleProtoBufInner(SimpleProtos2.SimpleProtoBufOuter2.SimpleProtoBufInner2.newBuilder().setInt64Field(64L).build())
                .setSimpleEnum(SimpleEnumProtos.SimpleEnum.Enum1)
                .build();
        assertThat(simpleProtoBufOuter2.getStringField()).isEqualTo("default value");
    }

    /**
     * 测试序列化和反序列化
     *
     * @throws Exception exception
     */
    @Test
    public void testSerializeAndDeserialize() throws Exception {
        SimpleProtos2.SimpleProtoBufOuter2 simpleProtoBufOuter2 = SimpleProtos2.SimpleProtoBufOuter2.newBuilder().setInt64Field(64L).setInt32Field(32)
                .setFloatField(3.14F).setDoubleField(2.71).setBoolField(true)
                // addAllxxx可以直接添加集合
                .addAllRepeatedString(Arrays.asList("str1", "str2"))
                // addxxx添加单个元素
                .addRepeatedSimpleProtoBufInner(SimpleProtos2.SimpleProtoBufOuter2.SimpleProtoBufInner2.newBuilder().setInt64Field(1L).build())
                .setSimpleProtoBufInner(SimpleProtos2.SimpleProtoBufOuter2.SimpleProtoBufInner2.newBuilder().setInt64Field(2L).build())
                .setSimpleEnum(SimpleEnumProtos.SimpleEnum.Enum1)
                .build();
        System.out.println(simpleProtoBufOuter2);
        byte[] bytes = simpleProtoBufOuter2.toByteArray();
        SimpleProtos2.SimpleProtoBufOuter2 deserialized2 = SimpleProtos2.SimpleProtoBufOuter2.newBuilder().mergeFrom(bytes).build();
        System.out.println(deserialized2);
        assertThat(deserialized2).hasFieldOrPropertyWithValue("int64Field", 64L)
                .hasFieldOrPropertyWithValue("int32Field", 32);


        SimpleProtos3.SimpleProtoBufOuter3 simpleProtoBufOuter3 = SimpleProtos3.SimpleProtoBufOuter3.newBuilder().setInt64Field(64L).setInt32Field(32)
                .setFloatField(3.14F).setDoubleField(2.71).setBoolField(true)
                // addAllxxx可以直接添加集合
                .addAllRepeatedString(Arrays.asList("str1", "str2"))
                // addxxx添加单个元素
                .addRepeatedSimpleProtoBufInner(SimpleProtos3.SimpleProtoBufInner3.newBuilder().setInt64Field(1L).build())
                .setSimpleProtoBufInner(SimpleProtos3.SimpleProtoBufInner3.newBuilder().setInt64Field(2L).build())
                .setSimpleEnum(SimpleEnumProtos.SimpleEnum.Enum1)
                .build();
        System.out.println(simpleProtoBufOuter3);
        bytes = simpleProtoBufOuter3.toByteArray();
        SimpleProtos3.SimpleProtoBufOuter3 deserialized3 = SimpleProtos3.SimpleProtoBufOuter3.newBuilder().mergeFrom(bytes).build();
        System.out.println(deserialized3);
        assertThat(deserialized3).hasFieldOrPropertyWithValue("int64Field", 64L)
                .hasFieldOrPropertyWithValue("int32Field", 32);

    }
}
