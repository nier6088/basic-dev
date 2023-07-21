package io.github.nier6088.util;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class QueryDataUtil {

    /**
     * list转map
     *
     * @param list
     * @param keyMapper
     * @param valueMapper
     * @param <T>
     * @param <K>
     * @param <U>
     * @return
     * @description
     * @author wfq
     * @date 2023/5/17-9:50
     * @version 1.0
     */
    public static <T, K, U> Map<K, U> listToMap(List<T> list, Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper) {
        return list.stream()
                .filter(item -> Objects.nonNull(keyMapper.apply(item)))
                .filter(item -> Objects.nonNull(valueMapper.apply(item)))
                .collect(Collectors.toMap(keyMapper, valueMapper, (k1, k2) -> k2));
    }


    /**
     * list to map 自定义冲突合并方法
     *
     * @param list
     * @param keyMapper
     * @param valueMapper
     * @param mergeFunction
     * @param <T>
     * @param <K>
     * @param <U>
     * @return
     * @description
     * @author wfq
     * @date 2023/5/17-9:50
     * @version 1.0
     */
    public static <T, K, U> Map<K, U> listToMap(List<T> list, Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction) {
        return list.stream()
                .filter(item -> Objects.nonNull(keyMapper.apply(item)))
                .filter(item -> Objects.nonNull(valueMapper.apply(item)))
                .collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction));
    }


    /**
     * list to Map  value为item本身 如果有冲突key 则不替换 取第一个
     *
     * @param list
     * @param keyMapper
     * @param <T>
     * @param <K>
     * @return
     * @description
     * @author wfq
     * @date 2023/5/17-9:51
     * @version 1.0
     */
    public static <T, K> Map<K, T> listToKeyIdentity(List<T> list, Function<? super T, ? extends K> keyMapper) {
        return list.stream()
                .filter(item -> Objects.nonNull(keyMapper.apply(item)))
                .collect(Collectors.toMap(keyMapper, Function.identity(), (k1, k2) -> k1));
    }

    /**
     * list to Map  value为item本身 自定义冲突合并
     *
     * @param list
     * @param keyMapper
     * @param <T>
     * @param <K>
     * @return
     * @description
     * @author wfq
     * @date 2023/5/17-9:51
     * @version 1.0
     */
    public static <T, K> Map<K, T> listToKeyIdentity(List<T> list, Function<? super T, ? extends K> keyMapper, BinaryOperator<T> mergeFunction) {
        return list.stream()
                .filter(item -> Objects.nonNull(keyMapper.apply(item)))
                .collect(Collectors.toMap(keyMapper, Function.identity(), mergeFunction));
    }


    /**
     * 对象映射转换  把一个对象转换成另一个对象
     *
     * @param obj
     * @param mapping 映射方法
     * @param <T>
     * @param <K>
     * @return
     * @description
     * @author wfq
     * @date 2023/5/17-9:52
     * @version 1.0
     */
    public static <T, K> K objToMap(T obj, Function<? super T, ? extends K> mapping) {
        return Optional.ofNullable(obj).filter(item -> Objects.nonNull(mapping.apply(item))).map(mapping).orElse(null);
    }


    /**
     * list转分组map
     *
     * @param list
     * @param groupByKey
     * @param <T>
     * @param <K>
     * @return
     * @description
     * @author wfq
     * @date 2023/5/17-9:52
     * @version 1.0
     */
    public static <T, K> Map<K, List<T>> listToGroupByMap(List<T> list, Function<? super T, ? extends K> groupByKey) {
        return list.stream()
                .filter(item -> Objects.nonNull(groupByKey.apply(item)))
                .collect(Collectors.groupingBy(groupByKey));
    }


    /**
     * list 转分组map value为统计数量
     *
     * @param list
     * @param groupByKey
     * @param <T>
     * @param <K>
     * @return
     * @description
     * @author wfq
     * @date 2023/5/17-9:53
     * @version 1.0
     */
    public static <T, K> Map<K, Long> listToGroupByCountMap(List<T> list, Function<? super T, ? extends K> groupByKey) {
        return list.stream()
                .filter(item -> Objects.nonNull(groupByKey.apply(item)))
                .collect(Collectors.groupingBy(groupByKey, Collectors.counting()));
    }


    /**
     * list 中间 操作（比如对item 设置值 ）
     *
     * @param list
     * @param consumer
     * @param <T>
     * @return
     * @description
     * @author wfq
     * @date 2023/5/17-12:47
     * @version 1.0
     */
    public static <T> List<T> listProcessNewList(List<T> list, Consumer<T> consumer) {
        return list.stream()
                .peek(item -> consumer.accept(item))
                .collect(Collectors.toList());
    }

    /**
     * list 中间 操作（比如对item 设置值 ）
     *
     * @param list
     * @param consumer
     * @param <T>
     * @return
     * @description
     * @author wfq
     * @date 2023/5/17-12:47
     * @version 1.0
     */
    public static <T> void listProcess(List<T> list, Consumer<T> consumer) {
        list.stream().forEach(consumer);
    }

    public static <T> void listProcess(List<T> list, BiConsumer<List<T>, T> consumer) {
        list.stream().forEach(item -> consumer.accept(list, item));
    }

    /**
     * list 转换 T -> K  并消费
     *
     * @param list
     * @param function
     * @param consumer
     * @param <T>
     * @param <K>
     * @return
     * @description
     * @author wfq
     * @date 2023/5/17-12:48
     * @version 1.0
     */
    public static <T, K> List<K> listProcessNewList(List<T> list, Function<T, K> function, Consumer<K> consumer) {
        return list.stream()
                .map(function)
                .peek(item -> consumer.accept(item))
                .collect(Collectors.toList());
    }

    public static <T, K> List<K> listConvert(List<T> list, Function<T, K> function) {
        return list.stream()
                .map(function)
                .collect(Collectors.toList());
    }


}
