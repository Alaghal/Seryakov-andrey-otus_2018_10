package ru.otus.l6.cacheForHW;


import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyCache<K, V> implements HwCache<K, V> {

    private final Map<K,SoftReference<V>> elementCache;
    private final List<HwListener> listenersList;

    MyCache(){
        elementCache  = new HashMap<>();
        listenersList = new ArrayList<>();
    }

    @Override
    public void put(K key, V value) {
        elementCache.put( key, new SoftReference (value));
        try{
            listenersList.forEach(i-> i.notify(key,value,"Put"));
        }catch(Exception e) {
            System.out.println("error" + e.getMessage());
        }

    }

    @Override
    public void remove(K key) {
        try{
            listenersList.forEach(i->i.notify(key, get(key),"Remove") );
        }catch(Exception e) {
            System.out.println("error" + e.getMessage());
        }

        elementCache.remove(key);

    }

    @Override
    public V get(Object key) {
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

}

