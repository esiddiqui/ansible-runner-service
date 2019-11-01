package com.es.ansible.runner.playbook;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class AnsibleRunner implements Runner {

    private static Logger logger = LoggerFactory.getLogger(AnsibleRunner.class);


    @Async
    @Override
    public Stream<String> runSimple(String cmd) {
        System.out.println("AnsibleRunner.runSimple() called");
        SimpleSpliterator<String> spliterator =
                new SimpleSpliterator<String>(100000,0,cmd);
        return StreamSupport.stream(spliterator,true);
    }



    @Async
    @Override
    public Stream<String> runFile(String cmd) {
        System.out.println("AnsibleRunner.runFile() called");
        File file = new File("/Users/ehteshamsiddiqui/hs_err_pid37713.txt");
        FileOutputSpliterator<String> spliterator =
                new FileOutputSpliterator<>(file);
        return StreamSupport.stream(spliterator,true);
    }


    //@Async
    @Override
    public Stream<String> run(String cmd) {

        logger.debug("AnsibleRunner.run() called");

//        File workingDirectory =
//                new File("/Users/ehteshamsiddiqui/Projects");
//        String[] command = new String[] {"./runner.bat"};


        //String path = "/Users/ehteshamsiddiqui/Repos/bibliocommons/bibliographics";
        //String[] command = new String[] {"./gradlew", "clean", "test"};

        //String path = "/"
        //String[] command = new String[] {"docker", "images"};

        String path = "/Users/ehteshamsiddiqui/Repos/bibliocommons/bc-deploy-provision";
        String[] command = {"ansible", "qa",  "-v", "-i", "ansible/inventory/qa", "-a", "\"pwd\""};

        File workingDirectory = new File(path);

        ProcessOutputSpliterator<String> spliterator =
                new ProcessOutputSpliterator<>(workingDirectory,command);
        return StreamSupport.stream(spliterator,false);
    }



//    //@Async
//    @Override
//    public Stream<String> runAsync(String cmd) {
//
//        System.out.println("AnsibleRunner.runAsync() called");
//        String path = "/Users/ehteshamsiddiqui/Repos/bibliocommons/bc-deploy-provision";
//        String[] command = {"ansible", "qa",  "-v", "-i", "ansible/inventory/qa", "-a", "\"pwd\""};
//
//        File workingDirectory = new File(path);
//        ProcessOutputSpliterator<String> spliterator =
//                new ProcessOutputSpliterator<>(workingDirectory,command);
//        //Stream<T]] > stream StreamSupport.stream(spliterator,false);
//
//
//    }
}

