package com.realdolmen.rdtravel.persistence;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by JSTAX29 on 8/10/2015.
 * A class that will test different annotations through reflection with generic types.
 * This saves me the effort of writing 500 tests which all do exactly the same, namely simple things like testing on null values or min and max size.
 */
public class GenericMethodsPersistence extends DataSetPersistenceTest {

    public <T, PK extends Serializable> void testNoNullsAllowed(GenericDao<T, PK> genericDao, T objectWithNotNullConstraints) {
        for (Field field : objectWithNotNullConstraints.getClass().getDeclaredFields()) {
            String fieldName = field.getName();
            Annotation[] annotations = field.getDeclaredAnnotations();

            for (Annotation annotation : annotations) {
                if (annotation instanceof NotNull) {
                    try {
                        invokeSetter(objectWithNotNullConstraints, fieldName, null);
                        //Persist and see if it fails
                        genericDao.update(objectWithNotNullConstraints);
                        flushAndClear();

                        //If no exception is thrown here, there is an error.
                        Assert.fail("The field " + fieldName + " should throw an exception when given a null value.");
                    } catch (ConstraintViolationException e) {
                        //If the constraint violation is thrown, it's a good thing!
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }

    public <T, PK extends Serializable> void testSizes(GenericDao<T, PK> genericDao, T objectWithSizeConstraints) {
        for (Field field : objectWithSizeConstraints.getClass().getDeclaredFields()) {
            String fieldName = field.getName();
            Annotation[] annotations = field.getDeclaredAnnotations();

            for (Annotation annotation : annotations) {
                if (annotation instanceof Size) {
                    Size sizeAnnotation = (Size) annotation;

                    try {
                        testMaxAllowedSize(sizeAnnotation, fieldName, genericDao, objectWithSizeConstraints);
                    } catch (ConstraintViolationException e) {
                        //If the constraint violation is thrown, it's actually a good thing!
                        System.out.println(e.getMessage());
                    }

                    try {
                        testMinAllowedSize(sizeAnnotation, fieldName, genericDao, objectWithSizeConstraints);
                    } catch (ConstraintViolationException e) {
                        //If the constraint violation is thrown, it's actually a good thing!
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }

    private <T, PK extends Serializable> void testMinAllowedSize(Size sizeAnnotation, String fieldName, GenericDao<T, PK> genericDao, T objectWithSizeConstraints) {
        int min = sizeAnnotation.min();
        //Test the min size
        String underMinAllowedString = StringUtils.leftPad("", min - 1, '*');
        invokeSetter(objectWithSizeConstraints, fieldName, underMinAllowedString);
        //Persist and see if it fails
        genericDao.update(objectWithSizeConstraints);
        flushAndClear();

        //If no exception is thrown here, there is an error.
        Assert.fail("This test tests for min size for field " + fieldName + " , but for size " + min + " a string with length " + underMinAllowedString.length() + " was passed.");
    }

    private <T, PK extends Serializable> void testMaxAllowedSize(Size sizeAnnotation, String fieldName, GenericDao<T, PK> genericDao, T objectWithSizeConstraints) {
        int max = sizeAnnotation.max();
        //Test the max size
        String overMaxAllowedString = StringUtils.leftPad("", max + 1, '*');
        invokeSetter(objectWithSizeConstraints, fieldName, overMaxAllowedString);
        //Persist and see if it fails
        genericDao.update(objectWithSizeConstraints);
        flushAndClear();

        //If no exception is thrown here, there is an error.
        Assert.fail("This test tests for max size for field " + fieldName + " , but for size " + max + " a string with length " + overMaxAllowedString.length() + " was passed.");
    }

    private void invokeSetter(Object obj, String variableName, Object variableValue) {
      /* variableValue is Object because value can be an Object, Integer, String, etc... */
        try {
            /**
             * Get object of PropertyDescriptor using variable name and class
             * Note: To use PropertyDescriptor on any field/variable, the field must have both `Setter` and `Getter` method.
             */
            PropertyDescriptor objPropertyDescriptor = new PropertyDescriptor(variableName, obj.getClass());
            /* Set field/variable value using getWriteMethod() */
            objPropertyDescriptor.getWriteMethod().invoke(obj, variableValue);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | IntrospectionException e) {
            /* Java 8: Multiple exception in one catch. Use Different catch block for lower version. */
            e.printStackTrace();
            Assert.fail("A setter could not be invoked for " + variableName + ".");
        }
    }
}
