package io.github.nier6088.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatchUtil {

    /**
     * @param batchSize
     * @param item
     * @param <T>
     * @return
     */
    public static <T> BatchIterator<T> batch(int batchSize, List<T> item) {
        int size = item.size();

        HashMap<Integer, List<T>> map = new HashMap<>();
        BatchIterator<T> batchIterator = new BatchIterator<>(map);
        if (size > batchSize) {
            //计算有几次
            int count = size / batchSize;
            int remaining = size % batchSize;
            for (int index = 0; index < count; index++) {
                List<T> list = new ArrayList();
                for (int i = 0; i < batchSize; i++) {
                    list.add(item.get(index * batchSize + i));
                }
                map.put(index, list);
            }

            //计算剩余的
            int next = count;
            List<T> list = new ArrayList();
            int index = count * batchSize;
            for (int i = 0; i < remaining; i++) {
                list.add(item.get(index + i));
            }
            map.put(next, list);
        } else {
            map.put(0, item);
        }

        return batchIterator;

    }

    //例子
    public static void main(String[] args) {

        List<Long> list = new ArrayList<>();
        for (int i = 0; i < 1023; i++) {
            list.add((long) i);
        }

        BatchIterator<Long> batch = BatchUtil.batch(30, list);
        while (batch.hasNext()) {
            List<Long> next = batch.next();
            next.stream().forEach(System.out::println);
        }

        batch.rest();

        while (batch.hasNext()) {
            List<Long> next = batch.next();
            next.stream().forEach(System.out::println);
        }

    }

    public static class BatchIterator<T> {

        private Map<Integer, List<T>> map;
        private int index;

        public BatchIterator(Map<Integer, List<T>> listMap) {
            map = listMap;
            index = 0;
        }


        public boolean hasNext() {
            return index < map.size();
        }

        public List<T> next() {
            List<T> t = map.get(index);
            index++;
            System.out.println("分段: " + index);
            return t;
        }

        /**
         * 重置数据， 可以再次使用hasNext
         */
        public void rest() {
            index = 0;
        }

        /**
         * 获取分组后的数据Map结构
         *
         * @return
         */
        public Map<Integer, List<T>> getGroupData() {
            return this.map;
        }

    }
}
