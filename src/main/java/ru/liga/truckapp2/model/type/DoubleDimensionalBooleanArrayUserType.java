package ru.liga.truckapp2.model.type;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.StringType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;

@Slf4j
@NoArgsConstructor
public class DoubleDimensionalBooleanArrayUserType implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[]{StringType.INSTANCE.sqlType()};
    }

    @Override
    public Class returnedClass() {
        return boolean[][].class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        boolean[][] b = (boolean[][]) o;
        boolean[][] b1 = (boolean[][]) o1;
        return Arrays.deepEquals(b, b1);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return Arrays.deepHashCode((boolean[][]) o);
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet,
                              String[] names,
                              SharedSessionContractImplementor session,
                              Object owner) throws HibernateException, SQLException {

        String columnName = names[0];
        String columnValue = (String) resultSet.getObject(columnName);
        log.debug("Result set column {} value is {}", columnName, columnValue);
        return columnValue == null ? null :
                DoubleDimensionalBooleanArrayConverter.fromString(columnValue);
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement,
                            Object value,
                            int idx,
                            SharedSessionContractImplementor session) throws HibernateException, SQLException {

        if (value == null) {
            log.debug("Binding null to parameter {} ", idx);
            preparedStatement.setNull(idx, Types.VARCHAR);
        } else {
            String stringValue = DoubleDimensionalBooleanArrayConverter.toString((boolean[][]) value);
            log.debug("Binding {} to parameter {} ", stringValue, idx);
            preparedStatement.setString(idx, stringValue);
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        boolean[][] toCopy = (boolean[][]) o;
        if (toCopy == null) return null;
        if (toCopy.length == 0) return new boolean[toCopy.length][];
        boolean[][] copied = new boolean[toCopy.length][toCopy[0].length];
        for (int i = 0; i < toCopy.length; i++) {
            System.arraycopy(toCopy[i], 0, copied[i], 0, toCopy[i].length);
        }
        return copied;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return (boolean[][]) deepCopy(o);
    }

    @Override
    public Object assemble(Serializable serializable, Object owner) throws HibernateException {
        return deepCopy(serializable);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return deepCopy(original);
    }
}