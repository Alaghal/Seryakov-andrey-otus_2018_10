package ru.otus.l3.Collection;

import java.util.*;
import java.util.function.Consumer;

public class MyArrayList<E> implements List<E> {

    private static final int DEFAULT_CAPACITY = 10;
    protected transient int modCount = 0;
    private int size;
    private transient E[] data;

    public MyArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public MyArrayList(int capacity) {

        if (capacity < 0)
            throw new IllegalArgumentException();
        data = (E[]) new Object[capacity];
    }

    static <E> E elementAt(Object[] es, int index) {
        return (E) es[index];
    }

    public int size() {

        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {

        return indexOf(o) != -1;
    }

    public Iterator<E> iterator() {
        return new MyArrayList.Itr();
    }


    public Object[] toArray() {
        return Arrays.copyOf(data, size);
    }


    public boolean add(E o) {
        modCount++;
        if (size == data.length)
            ensureCapacity(size + 1);
        data[size++] = o;
        return true;
    }

    public void ensureCapacity(int minCapacity) {

        int current = data.length;

        if (minCapacity > current) {

            E[] newData = (E[]) new Object[Math.max(current * 2, minCapacity)];
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }

    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);
    }

    public boolean addAll(int index, Collection c) {

        Iterator<? extends E> itr = c.iterator();
        int csize = c.size();

        modCount++;
        if (csize + size > data.length)
            ensureCapacity(size + csize);
        int end = index + csize;
        if (size > 0 && index != size)
            System.arraycopy(data, index, data, end, size - index);
        size += csize;
        for (; index < end; index++)
            data[index] = itr.next();
        return csize > 0;
    }

    public void clear() {

        if (size > 0) {
            modCount++;
            Arrays.fill(data, 0, size, null);
            size = 0;
        }

    }


    public E get(int index) {

        return data[index];
    }

    public E set(int index, E element) {

        E result = data[index];
        data[index] = element;
        return result;
    }

    public void add(int index, E element) {
        modCount++;
        if (size == data.length)
            ensureCapacity(size + 1);
        if (index != size)
            System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        size++;

    }

    public E remove(int index) {
        E r = data[index];
        modCount++;
        if (index != --size)
            System.arraycopy(data, index + 1, data, index, size - index);
        data[size] = null;
        return r;
    }

    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    public int lastIndexOf(Object o) {
        return 0;
    }

    public ListIterator<E> listIterator() {
        return new ListItr(0);
    }

    public ListIterator<E> listIterator(int index) {
        return new ListItr(index);
    }

    public List subList(int fromIndex, int toIndex) {
        return null;
    }

    public boolean retainAll(Collection c) {
        return false;
    }

    public boolean removeAll(Collection c) {
        return false;
    }

    public boolean containsAll(Collection c) {
        return false;
    }

    public Object clone() {
        MyArrayList clone = null;
        try {
            clone = (MyArrayList<E>) super.clone();
            (clone).data = data.clone();
        } catch (Exception e) {

        }
        return clone;
    }


    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    public void sort(Comparator<? super E> c) {
        final int expectedModCount = modCount;
        Arrays.sort((E[]) data, 0, size, c);
        if (modCount != expectedModCount)
            throw new ConcurrentModificationException();
        modCount++;
    }

    private class Itr implements Iterator<E> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        int expectedModCount = modCount;

        // prevent creating a synthetic constructor
        Itr() {
        }

        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            checkForComodification();
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = MyArrayList.this.data;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                MyArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            final int size = MyArrayList.this.size;
            int i = cursor;
            if (i < size) {
                final Object[] es = data;
                if (i >= es.length)
                    throw new ConcurrentModificationException();
                for (; i < size && modCount == expectedModCount; i++)
                    action.accept(elementAt(es, i));
                // update once at end to reduce heap write traffic
                cursor = i;
                lastRet = i - 1;
                checkForComodification();
            }
        }


        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    private class ListItr extends Itr implements ListIterator<E> {
        ListItr(int index) {
            super();
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        @SuppressWarnings("unchecked")
        public E previous() {
            checkForComodification();
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            Object[] elementData = MyArrayList.this.data;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i;
            return (E) elementData[lastRet = i];
        }

        public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                MyArrayList.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(E e) {
            checkForComodification();

            try {
                int i = cursor;
                MyArrayList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }
}

