package com.lzy.demo.jooq.tables.records;


import com.lzy.demo.jooq.tables.NewTable;
import com.lzy.demo.jooq.tables.interfaces.INewTable;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class NewTableRecord extends UpdatableRecordImpl<NewTableRecord> implements Record3<Integer, String, String>, INewTable {

    private static final long serialVersionUID = -372962572;

    /**
     * Setter for <code>demo.new_table.id</code>.
     */
    @Override
    public NewTableRecord setId(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>demo.new_table.id</code>.
     */
    @Override
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>demo.new_table.fdas</code>.
     */
    @Override
    public NewTableRecord setFdas(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>demo.new_table.fdas</code>.
     */
    @Override
    public String getFdas() {
        return (String) get(1);
    }

    /**
     * Setter for <code>demo.new_table.fda</code>.
     */
    @Override
    public NewTableRecord setFda(String value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>demo.new_table.fda</code>.
     */
    @Override
    public String getFda() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Integer, String, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return NewTable.NEW_TABLE.ID;
    }

    @Override
    public Field<String> field2() {
        return NewTable.NEW_TABLE.FDAS;
    }

    @Override
    public Field<String> field3() {
        return NewTable.NEW_TABLE.FDA;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getFdas();
    }

    @Override
    public String component3() {
        return getFda();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getFdas();
    }

    @Override
    public String value3() {
        return getFda();
    }

    @Override
    public NewTableRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public NewTableRecord value2(String value) {
        setFdas(value);
        return this;
    }

    @Override
    public NewTableRecord value3(String value) {
        setFda(value);
        return this;
    }

    @Override
    public NewTableRecord values(Integer value1, String value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    @Override
    public void from(INewTable from) {
        setId(from.getId());
        setFdas(from.getFdas());
        setFda(from.getFda());
    }

    @Override
    public <E extends INewTable> E into(E into) {
        into.from(this);
        return into;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached NewTableRecord
     */
    public NewTableRecord() {
        super(NewTable.NEW_TABLE);
    }

    /**
     * Create a detached, initialised NewTableRecord
     */
    public NewTableRecord(Integer id, String fdas, String fda) {
        super(NewTable.NEW_TABLE);

        set(0, id);
        set(1, fdas);
        set(2, fda);
    }
}
