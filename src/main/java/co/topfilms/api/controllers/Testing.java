package co.topfilms.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class Testing {

    @GetMapping()
    public ResponseEntity<Test> test() {
        return new ResponseEntity<>(new Test("Testing API!"), HttpStatus.OK);
    }

    public static class Test {
        private String data;

        public Test() {}

        public Test(String data) {
            this.data = data;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

}
