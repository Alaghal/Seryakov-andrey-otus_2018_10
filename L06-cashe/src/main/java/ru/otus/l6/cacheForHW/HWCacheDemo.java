package ru.otus.l6.cacheForHW;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class HWCacheDemo {

    public static void main(String[] args) {
        new HWCacheDemo().demo();
    }

    private void demo() {
        HwCache<Integer, Integer> cache = new MyCache();
        HwListener<Integer, Integer> listener =
                (key, value, action) -> System.out.println("key:" + key + ", value:" + value + ", action:" + action);
        cache.addListener(listener);
        cache.put(1,1);
      // ((MyCache<Integer, Integer>) cache).printValueOfCahs(1);
       System.out.println(cache.get(1));
        cache.remove(1);
        cache.removeListener(listener);

    }
}
