package ro.uaic.info.querybackendservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.uaic.info.querybackendservice.service.ResourceService;

import java.io.FileNotFoundException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/load")
public class ResourceLoader {

    private final ResourceService resourceService;

    @PostMapping
    public void loadDataFromResource() throws FileNotFoundException {
        resourceService.loadData();
    }

    @GetMapping("/show")
    public void printAll() {
        resourceService.getAllData();
    }
}
