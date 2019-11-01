package com.es.ansible.runner.playbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ProcessOutputSpliterator<T> implements Spliterator<T> {


    private static Logger logger = LoggerFactory.getLogger(ProcessOutputSpliterator.class);

    private BufferedReader reader;


    public ProcessOutputSpliterator(File file, String... command) {
        try {
            //File out = new File("/Users/ehteshamsiddiqui/Projects/p.out");
            Process p = new ProcessBuilder()
                    .command(command)
                    .directory(file)
                    //.redirectInput(out)
                    .start();
           reader = new BufferedReader(
                   new InputStreamReader(p.getInputStream()));
           //p.waitFor();
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
                logger.debug("tru: {}", line);
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
                logger.debug("forEch: {}", line);
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
