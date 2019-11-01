package com.es.ansible.runner.playbook;

import java.util.stream.Stream;

public interface Runner {

    Stream<String> run(String cmd);
    Stream<String> runSimple(String cmd);
    Stream<String> runFile(String cmd);
}
