package ro.uaic.info.querybackendservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.uaic.info.querybackendservice.api.request.PostBeverageRequest;
import ro.uaic.info.querybackendservice.api.response.DeleteBeverageByIdResponse;
import ro.uaic.info.querybackendservice.api.response.GetAllBeveragesResponse;
import ro.uaic.info.querybackendservice.api.response.GetBeverageByIdResponse;
import ro.uaic.info.querybackendservice.api.response.PostBeverageResponse;
import ro.uaic.info.querybackendservice.model.Beverage;
import ro.uaic.info.querybackendservice.model.BeverageContext;
import ro.uaic.info.querybackendservice.model.IRILabel;
import ro.uaic.info.querybackendservice.service.BeverageService;
import ro.uaic.info.querybackendservice.service.ResourceService;

import javax.validation.Valid;
import java.util.List;

import static org.eclipse.rdf4j.model.util.Values.iri;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/beverages")
public class BeverageController {

    private final BeverageService beverageService;
    private final ResourceService resourceService;

    /**
     * Returns empty list each time
     */
    @GetMapping
    public ResponseEntity<GetAllBeveragesResponse> getAllBeverages() {
        return ResponseEntity.ok(
                GetAllBeveragesResponse.builder().beverages(beverageService.getAllBeverages()).build());
    }

    /**
     * Does not fetch anything and throws an exception
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetBeverageByIdResponse> getBeverageById(@PathVariable String id) {
        return ResponseEntity.ok(
                GetBeverageByIdResponse.builder().beverage(beverageService.getBeverageById(iri(
                        "http://www.bem.ro/bem-schema#" + id))).build()
        );
    }

    @GetMapping("/context/{id}")
    public ResponseEntity<BeverageContext> getBeverageContextById(@PathVariable String id) {
        return ResponseEntity.ok(
                beverageService.getBeverageContextById(iri(
                        "http://www.bem.ro/bem-schema#" + id)));
    }

    /**
     * This is just to confirm that read works withing the same transaction with a write.
     */

    @PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<PostBeverageResponse> createBeverage(@Valid @RequestBody PostBeverageRequest req) {
        Beverage beverage = new Beverage();
        beverage.setBeverageId(iri(IRILabel.NS + req.getBeverageName()));
        beverage.setDescription(req.getDescription());
        beverage.setName(req.getBeverageName());
        beverage.setAllergens(req.getAllergens());
        if (req.getParentName() != null && !req.getParentName().isEmpty()) {
            beverage.setParent(iri(IRILabel.NS + req.getParentName()));
        }

        log.info("{}", beverage);
        return ResponseEntity.ok(
                PostBeverageResponse.builder().beverage(beverageService.createBeverage(beverage)).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteBeverageByIdResponse> deleteBeverageById(@PathVariable String id) {
        return ResponseEntity.ok(
                DeleteBeverageByIdResponse.builder().id(beverageService.deleteBeverage(
                        iri("http://www.bem.ro/bem-schema#" + id)))
                        .build());
    }

    @GetMapping("/search/{term}")
    public List<Beverage> getBeveragesByTermInDescription(@PathVariable String term) {
        return beverageService.fullTextSearchOnDescription(term);
    }
}
