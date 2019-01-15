package ru.otus.l6.cacheForHW;

/**
 * @author sergey
 * created on 14.12.18.
 */
public interface HwListener<K, V> {
    void notify1(K key, V value, String action);
}
