/*
 * Copyright (c) 2002-2015, JIDE Software Inc. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package jidefx.utils.comparator;

import java.util.Comparator;

/**
 * Comparator for Number type which supports a context to compare the absolute values. This is a singleton class. Call
 * getInstance() to get the comparator.
 */
public class NumberComparator implements Comparator<Object> {
    /**
     * Comparator Context to compare two values using the absolute value.
     */
    public static final ComparatorContext CONTEXT_ABSOLUTE = new ComparatorContext("AbsoluteValue"); //NON-NLS

    private static NumberComparator singleton = null;

    private boolean _absolute = false;

    /**
     * Constructor.
     * <p/>
     * Has protected access to prevent other clients creating instances of the class ... it is stateless so we need only
     * one instance.
     */
    protected NumberComparator() {
    }

    /**
     * Returns <tt>NumberComparator</tt> singleton.
     *
     * @return an instance of NumberComparator.
     */
    public static NumberComparator getInstance() {
        if (singleton == null)
            singleton = new NumberComparator();
        return singleton;
    }

    /**
     * Compares two <tt>Number</tt>s.
     *
     * @param o1 the first object to be compared
     * @param o2 the second object to be compared
     * @return 0 if a and b are equal, -1 if a is less than b, 1 if a is more than b.
     */
    public int compare(Object o1, Object o2) {
        if (o1 == null && o2 == null) {
            return 0;
        }
        else if (o1 == null) {
            return -1;
        }
        else if (o2 == null) {
            return 1;
        }

        if (o1 instanceof Number) {
            if (o2 instanceof Number) {
                long l1 = 0;
                long l2 = 0;
                double d1 = 0;
                double d2 = 0;
                if (o1 instanceof Long) {
                    l1 = ((Number) o1).longValue();
                }
                else {
                    d1 = ((Number) o1).doubleValue();
                }
                if (o2 instanceof Long) {
                    l2 = ((Number) o2).longValue();
                }
                else {
                    d2 = ((Number) o2).doubleValue();
                }

                if (isAbsolute()) {
                    if (d1 < 0) {
                        d1 = -d1;
                    }
                    if (d2 < 0) {
                        d2 = -d2;
                    }
                    if (l1 < 0) {
                        l1 = -l1;
                    }
                    if (l2 < 0) {
                        l2 = -l2;
                    }
                }

                if (o1 instanceof Long && o2 instanceof Long) {
                    if (l1 < l2)
                        return -1;
                    else if (l1 > l2)
                        return 1;
                    else
                        return 0;
                }
                else if (o1 instanceof Long) {
                    if (l1 < d2)
                        return -1;
                    else if (l1 > d2)
                        return 1;
                    else
                        return 0;
                }
                else if (o2 instanceof Long) {
                    if (d1 < l2)
                        return -1;
                    else if (d1 > l2)
                        return 1;
                    else
                        return 0;
                }
                else {
                    if (d1 < d2)
                        return -1;
                    else if (d1 > d2)
                        return 1;
                    else
                        return 0;
                }
            }
            else {
                // o2 wasn't Number
                throw new ClassCastException("The first argument of this method was not a Number but " + o2.getClass().getName());
            }
        }
        else if (o2 instanceof Number) {
            // o1 wasn't Number
            throw new ClassCastException("The second argument of this method was not a Number but " + o1.getClass().getName()
            );
        }
        else {
            // neither were Number
            throw new ClassCastException("Both arguments of this method were not Numbers. They are " + o1.getClass().getName() + " and " + o2.getClass().getName()
            );
        }
    }

    /**
     * Checks if if the values are compared using the absolute values.
     *
     * @return true or false.
     */
    public boolean isAbsolute() {
        return _absolute;
    }

    /**
     * Sets the flag to compare the values using the absolute value.
     *
     * @param absolute true or false.
     */
    public void setAbsolute(boolean absolute) {
        _absolute = absolute;
    }
}
