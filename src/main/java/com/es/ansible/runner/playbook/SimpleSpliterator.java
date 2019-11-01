package com.es.ansible.runner.playbook;

import java.util.Spliterator;
import java.util.function.Consumer;

public class SimpleSpliterator<T> implements Spliterator<T> {

    private int size;
    private int currentPosition;
    private T value;

    public SimpleSpliterator(int size, int currentPosition, T val) {
        this.size = size;
        this.currentPosition = currentPosition;
        this.value = val;
    }


    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        if (this.currentPosition < size ) {
            try {
                wait(200);
            } catch (Exception ex) {

            }
            action.accept((T)(this.value.toString() + "-" + this.currentPosition) );
            this.currentPosition++;
            return true;
        } else
            return true;
    }


    @Override
    public void forEachRemaining(Consumer<? super T> action) {
        for (; true; this.currentPosition++) {
            try {
                wait(200);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            action.accept((T)(this.value.toString() + "-" + this.currentPosition) );
            this.currentPosition++;
        }
    }


    @Override
    public Spliterator<T> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return this.size;
    }

    @Override
    public int characteristics() {
        return ORDERED | SIZED | IMMUTABLE | SUBSIZED;
    }
}
