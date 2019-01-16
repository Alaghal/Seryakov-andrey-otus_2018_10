package ru.otus.l6.cacheForHW;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {
//Надо реализовать эти методы
    private final Map<K,SoftValue <V>> elementCache;
    private ReferenceQueue<V> queue = new ReferenceQueue<V>();
    private final List<HwListener> listenersList;

    MyCache(){
        elementCache  = new HashMap<>();
        listenersList = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    private void processQueue() {
        while (true) {
            Reference<? extends V> o = queue.poll();
            if (o == null) {
                return;
            }
            SoftValue<V> k = (SoftValue<V>) o;
            Object key = k.key;
            elementCache.remove(key);
        }
    }

    @Override
    public void put(K key, V value) {
        processQueue();
        elementCache.put(key, new SoftValue<V>(value, queue, key));
        listenersList.forEach(i-> i.notify(key,value,"Put"));

    }

    @Override
    public void remove(K key) {
        listenersList.forEach(i->i.notify(key, get(key),"Remove") );
        processQueue();
        elementCache.remove(key);

    }

    @Override
    public V get(Object key) {
        processQueue();
        SoftReference<V> o = elementCache.get(key);
        if (o == null) {
            return null;
        }
        return o.get();
    }


    @Override
    public void addListener(HwListener listener) {
         listenersList.add(listener);
    }

    @Override
    public void removeListener(HwListener listener) {
         listenersList.remove(listener);
    }


    private static class SoftValue<T> extends SoftReference<T> {
        final Object key;

        public SoftValue(T ref, ReferenceQueue<T> q, Object key) {
            super(ref, q);
            this.key = key;
        }

    }
}

