package com.lzy.demo.base.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lzy.demo.base.jackson.annotation.SimpleJacksonInject;
import com.lzy.demo.base.jackson.annotation.SimpleJsonAny;
import com.lzy.demo.base.jackson.annotation.SimpleJsonCreator;
import com.lzy.demo.base.jackson.annotation.SimpleJsonIgnore;
import com.lzy.demo.base.jackson.annotation.SimpleJsonInclude;
import com.lzy.demo.base.jackson.annotation.SimpleJsonProperty;
import com.lzy.demo.base.jackson.annotation.SimpleJsonPropertyOrder;
import com.lzy.demo.base.jackson.annotation.SimpleJsonRawValue;
import com.lzy.demo.base.jackson.annotation.SimpleJsonRootName;
import com.lzy.demo.base.jackson.annotation.SimpleJsonSerializer;
import com.lzy.demo.base.jackson.annotation.SimpleJsonType;
import com.lzy.demo.base.jackson.annotation.SimpleJsonUnwrapped;
import com.lzy.demo.base.jackson.annotation.SimpleJsonValue;
import com.lzy.demo.base.jackson.annotation.SimpleJsonView;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The type Annotation test.
 *
 * @author lzy
 * @version v1.0
 */
public class AnnotationTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        objectMapper = new ObjectMapper();
    }


    /**
     * 测试@JsonAnySetter和@JsonAnyGetter
     * 适用于统一将未匹配的字段放到map里
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void testJsonAny() throws JsonProcessingException {
        SimpleJsonAny simpleJsonAny = new SimpleJsonAny();
        simpleJsonAny.add("attr1", "val1");
        simpleJsonAny.add("attr2", "val2");
        String result = objectMapper.writeValueAsString(simpleJsonAny);
        //序列化时,attr1和attr2直接晋升为SimpleJSONAny的属性,不会出现others
        Assertions.assertThat(result).doesNotContain("others").contains("attr1").contains("attr2");
        //反序列化时,attr1和attr2未匹配上SimpleJSONAny的属性,就放到oathers里
        simpleJsonAny = objectMapper.readValue(result, SimpleJsonAny.class);
        Assertions.assertThat(simpleJsonAny.getOthers()).containsOnlyKeys("attr1", "attr2");
    }


    /**
     * 测试@JsonProperty,@JsonGetter, @JsonSetter
     * 适用于key跟bean的属性不一致
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void testJsonProperty() throws JsonProcessingException {
        SimpleJsonProperty simpleJsonProperty = new SimpleJsonProperty("property1", "property2");
        String result = objectMapper.writeValueAsString(simpleJsonProperty);
        //序列化
        Assertions.assertThat(result).contains("renameProperty1").contains("renameGetProperty2");
        //反序列化
        simpleJsonProperty = objectMapper.readValue(result.replace("renameGetProperty2", "renameSetProperty2"), SimpleJsonProperty.class);
        Assertions.assertThat(simpleJsonProperty).hasFieldOrPropertyWithValue("property1", "property1")
                .hasFieldOrPropertyWithValue("property2", "property2");

    }

    /**
     * 测试@JsonPropertyOrder
     * 适用于序列化时,字段需要排序
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void testJsonPropertyOrder() throws JsonProcessingException {
        SimpleJsonPropertyOrder simpleJsonPropertyOrder = new SimpleJsonPropertyOrder("property1", "property2");
        String result = objectMapper.writeValueAsString(simpleJsonPropertyOrder);
        //序列化出来的key是有顺序的
        Assertions.assertThat(result.indexOf("property2") < result.indexOf("property1")).isTrue();
    }


    /**
     * 测试@JsonRawValue
     * 适用于序列化时,字段有json值的
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void testJsonRawValue() throws JsonProcessingException {
        SimpleJsonRawValue simpleJsonRawValue = new SimpleJsonRawValue("{\"attr\":false}", "{\"attr\":false}");
        String result = objectMapper.writeValueAsString(simpleJsonRawValue);
        //序列化后,property2的值也是json,而不是字符串
        Assertions.assertThat(result).contains("\"property2\":{\"attr\":false}");
    }

    /**
     * 测试@JsonRootName,需要SerializationFeature.WRAP_ROOT_VALUE
     * 适用于为json包裹最外层
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void testJsonRootName() throws JsonProcessingException {
        SimpleJsonRootName simpleJsonRootName = new SimpleJsonRootName("property");
        //需要开启SerializationFeature.WRAP_ROOT_VALUE
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        String result = objectMapper.writeValueAsString(simpleJsonRootName);
        Assertions.assertThat(result).contains("simpleJsonRootName");
    }

    /**
     * 测试@JsonValue
     * 适用于枚举序列化
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void testJsonValue() throws JsonProcessingException {
        SimpleJsonValue simpleJsonValue = new SimpleJsonValue();
        simpleJsonValue.setSimpleJsonValueEnum(SimpleJsonValue.SimpleJsonValueEnum.SIMPLE_JSON_VALUE2);
        String result = objectMapper.writeValueAsString(simpleJsonValue);
        Assertions.assertThat(result).isEqualTo("{\"simpleJsonValueEnum\":\"value\"}");

        //使用@JsonValue时,当不同对象的序列化结果是相同的,此时反序列化是不准确的
        simpleJsonValue = objectMapper.readValue(result, SimpleJsonValue.class);
        Assertions.assertThat(simpleJsonValue.getSimpleJsonValueEnum()).isEqualTo(SimpleJsonValue.SimpleJsonValueEnum.SIMPLE_JSON_VALUE);
    }

    /**
     * 测试@JsonDeserialize,@JsonSerialize
     * 适用于需要特定的序列化和反序列化
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void testJsonSerialize() throws JsonProcessingException {
        LocalDateTime now = LocalDateTime.now().withNano(0);
        SimpleJsonSerializer simpleJsonSerializer = new SimpleJsonSerializer("property", now);
        String result = objectMapper.writeValueAsString(simpleJsonSerializer);
        Assertions.assertThat(result).contains("customproperty").contains(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(now));

        simpleJsonSerializer = objectMapper.readValue(result, SimpleJsonSerializer.class);
        Assertions.assertThat(simpleJsonSerializer.getProperty()).isEqualTo("property");
        Assertions.assertThat(simpleJsonSerializer.getLocalDateTime()).isEqualTo(now);
    }


    /**
     * 测试@JsonCreator
     * 适用于没有默认构造函数
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void testJsonCreator() throws JsonProcessingException {
        SimpleJsonCreator simpleJsonCreator = objectMapper.readValue("{\"property\":\"property\"}", SimpleJsonCreator.class);
        Assertions.assertThat(simpleJsonCreator.getProperty()).isEqualTo("property");
    }

    /**
     * 测试@JacksonInject
     * 适用于需要额外添加值
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void testJacksonInject() throws JsonProcessingException {
        String json = "{\"property1\":\"property1\"}";
        //如果json没有指定property2的值,则么inject会为它设置值
        final InjectableValues inject = new InjectableValues.Std().addValue("property2", "property2");

        final SimpleJacksonInject simpleJacksonInject = objectMapper.reader(inject)
                .forType(SimpleJacksonInject.class)
                .readValue(json);
        Assertions.assertThat(simpleJacksonInject.getProperty2()).isEqualTo("property2");
    }


    /**
     * 测试@JsonIgnoreProperties,@JsonIgnore,@JsonIgnoreType
     * 适用于序列化和反序列化忽略属性
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void testJsonIgnore() throws JsonProcessingException {
        SimpleJsonIgnore simpleJsonIgnore = new SimpleJsonIgnore("property1", "property2");
        //序列化会忽略此字段
        String result = objectMapper.writeValueAsString(simpleJsonIgnore);
        Assertions.assertThat(result).doesNotContain("property1").doesNotContain("property2").doesNotContain("name");

        //反序列化也会忽略此字段
        simpleJsonIgnore = objectMapper.readValue("{\"property1\":\"property1\",\"property2\":\"property2\",\"name\":{\"firstName\":\"demo\"}}", SimpleJsonIgnore.class);
        Assertions.assertThat(simpleJsonIgnore).hasAllNullFieldsOrProperties();
    }

    /**
     * 测试@JsonInclude
     * 适用于序列化策略(Include.ALWAYS:序列化所有字段,Include.NON_NULL:只序列化非空字段)
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void testJsonInclude() throws JsonProcessingException {
        SimpleJsonInclude simpleJsonInclude = new SimpleJsonInclude("property1");
        //空字段不进行序列化
        String result = objectMapper.writeValueAsString(simpleJsonInclude);
        Assertions.assertThat(result).contains("property1").doesNotContain("property2");
    }


    /**
     * 测试@JsonTypeInfo,@JsonSubTypes,@JsonTypeName
     * 适用于多子类
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void testJsonType() throws JsonProcessingException {
        SimpleJsonType simpleJsonType = new SimpleJsonType(new SimpleJsonType.Sub1("sub1"));
        //序列化会多了type来标识是哪个子类
        String result = objectMapper.writeValueAsString(simpleJsonType);
        Assertions.assertThat(result).contains("type");

        //根据type会自动序列化为相应的子类
        simpleJsonType = objectMapper.readValue(result, SimpleJsonType.class);
        Assertions.assertThat(simpleJsonType.getSubType()).isInstanceOf(SimpleJsonType.Sub1.class);
    }

    /**
     * 使用@JsonUnwrapped
     * 适用于json字段是平级的,bean是分级的
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void testJsonUnwrapped() throws JsonProcessingException {
        SimpleJsonUnwrapped simpleJsonUnwrapped = new SimpleJsonUnwrapped("property");
        //序列化将subProperty提升给外层
        String result = objectMapper.writeValueAsString(simpleJsonUnwrapped);
        Assertions.assertThat(result).contains("subProperty").doesNotContain("unwrappedType");

        //反序列化会自动封装为UnwrappedType
        simpleJsonUnwrapped = objectMapper.readValue(result, SimpleJsonUnwrapped.class);
        Assertions.assertThat(simpleJsonUnwrapped.getUnwrappedType().getSubProperty()).isEqualTo("subProperty");
    }

    /**
     * 使用@JsonView
     * 适用于通过级别控制是否序列化和反序列化,可以跟spring mvc配合使用
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void testJsonView() throws JsonProcessingException {
        SimpleJsonView simpleJsonView = new SimpleJsonView("property1", "property2", "property3");
        //序列化只会序列化符合条件的字段
        String result = objectMapper.writerWithView(SimpleJsonView.Middle.class).writeValueAsString(simpleJsonView);
        Assertions.assertThat(result).contains("property1").contains("property2").doesNotContain("property3");

        //反序列化只会反序列化符合条件的字段
        simpleJsonView = objectMapper.readerWithView(SimpleJsonView.Low.class)
                .forType(SimpleJsonView.class).readValue(result);
        Assertions.assertThat(simpleJsonView)
                .hasAllNullFieldsOrPropertiesExcept("property1")
                .hasNoNullFieldsOrPropertiesExcept("property2", "property3");
    }
}
