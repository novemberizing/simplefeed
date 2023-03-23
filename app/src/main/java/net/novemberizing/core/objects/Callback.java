package net.novemberizing.core.objects;

public interface Callback<T> {
    void on(T o, Throwable e);
}
