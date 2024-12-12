package org.splitter;

public class TypeStatistics<T extends Number & Comparable> {
    //T t;
    private T sum;
    private T min;
    private T max;
    private long size = 0;
    
    T findMin(T t, T d) {
        if (t.compareTo(d) == -1) {
            return t;
        } else {
            return d;
        }
    }
    T findMax(T t, T d) {
        if (t.compareTo(d) == 1) {
            return t;
        } else {
            return d;
        }
    }
    
    public static <B extends Number> B makeSum(B t, B d) {
        B b = null;
        if (t.getClass().equals(Double.class)) {
            b = (B) new Double(t.doubleValue() + d.doubleValue());
        }
        if (t.getClass().equals(Long.class)) {
            b = (B) new Long(t.longValue() + d.longValue());
        }
        if (t.getClass().equals(Integer.class)) {
            b = (B) new Integer(t.intValue() + d.intValue());
        }
        return b;
    }
    
    void add(T t) {
        if (size == 0) {
            sum = t;
            min = t;
            max = t;
        } else {
            sum = makeSum(sum,t);
            min = findMin(t, min);
            max = findMax(t, max);
        }
        size += 1;
    }
    T getSum () {
        return sum;
    }
    T getMin () {
        return min;
    }
    T getMax () {
        return max;
    }
}
