package com.es.ansible.runner.playbook;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.stream.Stream;

@EnableAsync
@RestController
@RequestMapping("/api/v1/ansible/playbook")
public class PlaybookController {


    @Autowired
    private Runner runner;


    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value="/execute",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getExecute() {

        return new ResponseEntity("nonononon", HttpStatus.OK);
    }


    @CrossOrigin(origins = "http://localhost:4200")
    //https://www.airpair.com/java/posts/spring-streams-memory-efficiency
    @GetMapping(value="/executestream",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Stream<String> getExecute2() {
         return this.runner.run("yoyo");
         //String liners = new String();
         //stream.forEach(i-> System.out.println("ctrl: " + i));
         //return liners;

    }


    //https://www.airpair.com/java/posts/spring-streams-memory-efficiency
    @GetMapping(value="/executestream2")
    public void getExecute3(final HttpServletResponse response) {

        response.setHeader(
                "Content-Disposition",
                "attachment;filename=stdout.json");
        response.setContentType("application/json");

        Stream<String> stream = this.runner.run("yoyo");
        String liners = new String();
        stream.forEach(i-> {
            System.out.println("ctrl: " + i);
            try {
                response.getOutputStream().print(i);
                response.getOutputStream().flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }


    //resource: https://dzone.com/articles/streaming-data-with-spring-boot-restful-web-servic
    @GetMapping(value="/execute-httpresponse")
    public ResponseEntity<StreamingResponseBody> getExecuteStream(final HttpServletResponse response) {

        response.setHeader(
                "Content-Disposition",
                "attachment;filename=stdout.json");
        response.setContentType("application/json");


        StreamingResponseBody stream = out -> {
            OutputStream os;
            try (Writer w = new OutputStreamWriter(out, "UTF-8")) {
                this.runner.run("").forEach( line -> {
                    try {
                        System.out.println("ctrl: " + line );
                        w.write(line );
                        w.flush();
                    } catch (Exception ex) {
                        System.err.println("Error writing stream to api out");
                    }
                });
            }
        };
        //response.getOutputStream().
        return new ResponseEntity(stream, HttpStatus.OK);
    }



//    private BufferedReader runnerd(File file, String command) {
//        try {
//            Process p = new ProcessBuilder()
//                    .command(command)
//                    .directory(file)
//                    //.redirectInput(out)
//                    .start();
//            BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(p.getInputStream()));
//            return reader;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
