/*
 * This file is generated by jOOQ.
 */
package com.lzy.demo.jooq.tables.pojos;


import com.lzy.demo.jooq.enums.SimpleJdbiUseStringEnum;
import com.lzy.demo.jooq.tables.interfaces.ISimpleJdbi;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class SimpleJdbi implements ISimpleJdbi {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private SimpleJdbiUseStringEnum useStringEnum;
    private Integer useEnumValueEnum;

    public SimpleJdbi() {}

    public SimpleJdbi(ISimpleJdbi value) {
        this.id = value.getId();
        this.name = value.getName();
        this.useStringEnum = value.getUseStringEnum();
        this.useEnumValueEnum = value.getUseEnumValueEnum();
    }

    public SimpleJdbi(
        Integer id,
        String name,
        SimpleJdbiUseStringEnum useStringEnum,
        Integer useEnumValueEnum
    ) {
        this.id = id;
        this.name = name;
        this.useStringEnum = useStringEnum;
        this.useEnumValueEnum = useEnumValueEnum;
    }

    /**
     * Getter for <code>demo.simple_jdbi.id</code>.
     */
    @Override
    public Integer getId() {
        return this.id;
    }

    /**
     * Setter for <code>demo.simple_jdbi.id</code>.
     */
    @Override
    public SimpleJdbi setId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Getter for <code>demo.simple_jdbi.name</code>.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Setter for <code>demo.simple_jdbi.name</code>.
     */
    @Override
    public SimpleJdbi setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Getter for <code>demo.simple_jdbi.use_string_enum</code>.
     */
    @Override
    public SimpleJdbiUseStringEnum getUseStringEnum() {
        return this.useStringEnum;
    }

    /**
     * Setter for <code>demo.simple_jdbi.use_string_enum</code>.
     */
    @Override
    public SimpleJdbi setUseStringEnum(SimpleJdbiUseStringEnum useStringEnum) {
        this.useStringEnum = useStringEnum;
        return this;
    }

    /**
     * Getter for <code>demo.simple_jdbi.use_enum_value_enum</code>.
     */
    @Override
    public Integer getUseEnumValueEnum() {
        return this.useEnumValueEnum;
    }

    /**
     * Setter for <code>demo.simple_jdbi.use_enum_value_enum</code>.
     */
    @Override
    public SimpleJdbi setUseEnumValueEnum(Integer useEnumValueEnum) {
        this.useEnumValueEnum = useEnumValueEnum;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final SimpleJdbi other = (SimpleJdbi) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.name == null) {
            if (other.name != null)
                return false;
        }
        else if (!this.name.equals(other.name))
            return false;
        if (this.useStringEnum == null) {
            if (other.useStringEnum != null)
                return false;
        }
        else if (!this.useStringEnum.equals(other.useStringEnum))
            return false;
        if (this.useEnumValueEnum == null) {
            if (other.useEnumValueEnum != null)
                return false;
        }
        else if (!this.useEnumValueEnum.equals(other.useEnumValueEnum))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + ((this.useStringEnum == null) ? 0 : this.useStringEnum.hashCode());
        result = prime * result + ((this.useEnumValueEnum == null) ? 0 : this.useEnumValueEnum.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SimpleJdbi (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(useStringEnum);
        sb.append(", ").append(useEnumValueEnum);

        sb.append(")");
        return sb.toString();
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    @Override
    public void from(ISimpleJdbi from) {
        setId(from.getId());
        setName(from.getName());
        setUseStringEnum(from.getUseStringEnum());
        setUseEnumValueEnum(from.getUseEnumValueEnum());
    }

    @Override
    public <E extends ISimpleJdbi> E into(E into) {
        into.from(this);
        return into;
    }
}
