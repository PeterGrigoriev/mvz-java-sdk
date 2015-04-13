package com.movilizer.util.collection;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.gson.JsonObject;
import com.movilizer.util.dbc.Ensure;

import java.math.BigDecimal;
import java.util.*;

import static com.google.common.collect.Sets.newHashSet;
import static com.google.common.collect.Sets.newLinkedHashSet;
import static java.lang.Math.min;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class CollectionUtils {
    public static <T> List<T> take(List<T> list, int numberOfElements) {
        List<T> result = new ArrayList<T>();
        numberOfElements = min(list.size(), numberOfElements);
        for (int i = 0; i < numberOfElements; i++) {
            result.add(list.get(i));

        }
        return result;
    }

    public static <T> List<T> select(T[] array, Predicate<T> predicate) {
        List<T> result = new ArrayList<T>();
        for (T element : array) {
            if (predicate.apply(element)) {
                result.add(element);
            }
        }

        return result;
    }

    public static <T> List<T> select(Iterable<T> iterable, Predicate<T> predicate) {
        List<T> result = new ArrayList<T>();
        for (T element : iterable) {
            if (predicate.apply(element)) {
                result.add(element);
            }
        }

        return result;
    }

    public static <T> boolean isNullOrEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    public static String printToJSON(Map<String, String> map) {
        JsonObject object = new JsonObject();
        for (String key : map.keySet()) {
            object.addProperty(key, map.get(key));
        }
        return object.toString();
    }

    public static BigDecimal[] toBigDecimals(Iterable<Integer> integers) {
        List<BigDecimal> res = new ArrayList<BigDecimal>();
        for (Integer integer : integers) {
            res.add(new BigDecimal(integer));
        }

        return res.toArray(new BigDecimal[res.size()]);
    }

    public static BigDecimal[] stringsToBigDecimals(Iterable<String> strings) {
        return toBigDecimals(toIntegers(strings));
    }

    public static List<Integer> toIntegers(Iterable<String> strings) {
        List<Integer> res = new ArrayList<Integer>();
        for (String string : strings) {
            res.add(Integer.parseInt(string));
        }
        return res;
    }

    public static List<Integer> toIntegers(Iterable<String> strings, List<String> failedToConvert) {
        List<Integer> res = new ArrayList<Integer>();
        for (String string : strings) {
            try {
                res.add(Integer.parseInt(string));
            } catch (NumberFormatException e) {
                failedToConvert.add(string);
            }
        }
        return res;
    }

    public static <TSource, TResult> Map<TResult, List<TSource>> collectValuesMap(Iterable<TSource> sources, Function<TSource,TResult> function) {
        Map<TResult, List<TSource>> resultMap = new HashMap<TResult, List<TSource>>();
        for (TSource source : sources) {
            TResult result = function.apply(source);
            List<TSource> list = resultMap.get(result);
            if(list == null) {
                list = new ArrayList<TSource>();
                resultMap.put(result, list);
            }
            list.add(source);
        }
        return resultMap;
    }

    public static Predicate<Collection> lengthIsAtLeast(final int minLength) {
        return new Predicate<Collection>() {
            @Override
            public boolean apply(Collection collection) {
                return collection != null && collection.size() >= minLength;
            }
        };
    }

    public static <TKey, TValue> Map<TKey, TValue> filterMap(Map<TKey, TValue> map, Predicate<? super TValue> predicate) {
        if(null == map) {
            return null;
        }

        Set<TKey> keys = map.keySet();
        Map<TKey, TValue> result = new HashMap<TKey, TValue>();
        for (TKey key : keys) {
            TValue value = map.get(key);
            if(predicate.apply(value)) {
                result.put(key, value);
            }
        }

        return result;
    }

    public static <TSource, TValue> List<TValue> apply(Iterable<? extends TSource> iterable, Function<TSource, TValue> selector) {
        Ensure.ensureNotNull(iterable, "iterable");
        Ensure.ensureNotNull(selector, "selector");

        List<TValue> result = new ArrayList<TValue>();
        for (TSource source : iterable) {
            result.add(selector.apply(source));
        }
        return result;
    }

    public static <T> boolean areUnique(Collection<T> collection) {
        Ensure.ensureNotNull(collection, "collection");
        return new HashSet<T>(collection).size() == collection.size();
    }


    public static <TSource, TValue> boolean areUnique(Collection<? extends TSource> collection, Function<? super TSource, TValue> selector) {
        Ensure.ensureNotNull(collection, "collection");
        Ensure.ensureNotNull(selector, "selector");

        return areUnique(apply(collection, selector));
    }

    public static BigDecimal[] toBigDecimalArray(Collection<? extends Integer> collection) {
        List<BigDecimal> result = new ArrayList<BigDecimal>();
        for (Integer integer : collection) {
            result.add(new BigDecimal(integer));
        }
        return result.toArray(new BigDecimal[result.size()]);
    }

    public static Properties newProperties (String... strings) {
        if(null == strings) {
            return null;
        }
        Properties properties = new Properties();
        for (int i = 0; i < strings.length - 1; i+=2) {
            String key = strings[i];
            String value = strings[i+1];
            properties.setProperty(key, value);
        }
        //noinspection Contract
        return properties;
    }


    public static <T> Set<T> nullToEmptySet(Set<T> set) {
        return set == null ? new HashSet<T>() : set;
    }

    public static <T> Set<T> newLinkedSet(T... elements) {
        Set<T> set = newLinkedHashSet();
        Collections.addAll(set, elements);
        return set;
    }

    public static Set<String> toStringSet(List list) {
        Set<String> set = newHashSet();
        for (Object o : list) {
            set.add(String.valueOf(o));
        }
        return set;
    }

    public static <T> void skip(Iterator<T> iterator, int numberOfRecords) {
        int i = 0;
        while (iterator.hasNext()) {
            i++;
            if(i > numberOfRecords) {
                return;
            }
            iterator.next();
        }

    }
}
