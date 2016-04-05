package org.byteam.superadapter.internal;

import java.util.List;

/**
 * <p>Select, insert, update, delete.</p>
 * Created by Cheney on 16/4/1.
 */
public interface CRUD<T> {
    void add(T item);

    void insert(int index, T item);

    void addAll(List<T> items);

    void remove(T item);

    void remove(int index);

    void set(T oldItem, T newItem);

    void set(int index, T item);

    void replaceAll(List<T> items);

    boolean contains(T item);

    void clear();
}
