package com.es.ansible.runner.playbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Spliterator;
import java.util.function.Consumer;

public class FileOutputSpliterator<T> implements Spliterator<T> {


    private BufferedReader reader;


    public FileOutputSpliterator(File file) {
        try {
           reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        try {
            String line = reader.readLine();
            if (line == null)
                return false;
            else {
                System.out.println("ta:" + line);
                action.accept((T) line);
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }


    @Override
    public void forEachRemaining(Consumer<? super T> action) {
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                System.out.println("fer: " + line);
                action.accept((T)line);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public Spliterator<T> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return  Long.MAX_VALUE;
    }

    @Override
    public int characteristics() {
        return ORDERED | SIZED | IMMUTABLE | SUBSIZED;
    }
}
