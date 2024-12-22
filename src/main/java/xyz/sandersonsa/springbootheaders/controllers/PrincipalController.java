package xyz.sandersonsa.springbootheaders.controllers;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/headers")
public class PrincipalController {

    @GetMapping("/list-all")
    public ResponseEntity<Map<String, String>> listAllHeaders(
            @RequestHeader Map<String, String> headers) {
        headers.forEach((key, value) -> {
            System.out.println(String.format(" ## Header '%s' => %s", key, value));
            // System.out.println(" ## Header Name :: "+key+" Header Value :: "+value);
        });

        return new ResponseEntity<Map<String, String>>(headers, HttpStatus.OK);
    }

    @GetMapping("/multi-value")
    public ResponseEntity<String> multiValue(
            @RequestHeader MultiValueMap<String, String> headers) {
        headers.forEach((key, value) -> {
            System.out.println(String.format(
                    "Header '%s' = %s", key, value.stream().collect(Collectors.joining("|"))));
        });

        return new ResponseEntity<String>(
                String.format("Listed %d headers", headers.size()), HttpStatus.OK);
    }

    @GetMapping("/get-base-url")
    public ResponseEntity<String> getBaseUrl(@RequestHeader HttpHeaders headers) {
        InetSocketAddress host = headers.getHost();
        String url = "http://" + host.getHostName() + ":" + host.getPort();
        return new ResponseEntity<String>(String.format("Base URL = %s", url), HttpStatus.OK);
    }

}
