package ro.uaic.info.querybackendservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.rdf4j.model.IRI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.uaic.info.querybackendservice.api.errors.DefaultError;
import ro.uaic.info.querybackendservice.api.errors.ErrorData;
import ro.uaic.info.querybackendservice.api.response.DeleteBeverageByIdResponse;
import ro.uaic.info.querybackendservice.api.response.GetAllBeveragesResponse;
import ro.uaic.info.querybackendservice.api.response.GetBeverageByIdResponse;
import ro.uaic.info.querybackendservice.api.response.PostBeverageResponse;
import ro.uaic.info.querybackendservice.model.Beverage;
import ro.uaic.info.querybackendservice.model.BeverageContext;
import ro.uaic.info.querybackendservice.service.BeverageService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.eclipse.rdf4j.model.util.Values.iri;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/beverages")
public class BeverageController {

    private final BeverageService beverageService;

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

    @PostMapping
    public ResponseEntity<PostBeverageResponse> createBeverage(HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();

        if (parameters.get("name") == null) {
            return ResponseEntity.badRequest().body(PostBeverageResponse.builder()
                    .errorData(new ErrorData(
                            Collections.singletonList(DefaultError.InvalidInputMissingBeverageName.details),
                            LocalDateTime.now()
                    ))
                    .build());
        }

        if (parameters.get("name").length > 1) {
            return ResponseEntity.badRequest().body(PostBeverageResponse.builder()
                            .errorData(new ErrorData(
                                    Collections.singletonList(DefaultError.InvalidInputMultipleBeverageNames.details),
                                    LocalDateTime.now()
                            ))
                    .build());
        }

        String name = parameters.get("name")[0];

        BeverageContext beverageContext = new BeverageContext(
                iri("http://www.bem.ro/bem-schema#" + name + "Context"),
                parameters.get("event")[0],
                parameters.get("location")[0],
                parameters.get("season")[0],
                Arrays.stream(parameters.get("health_restrictions")).collect(Collectors.toSet())
        );
        IRI parent = null;
        String[] parentValues = parameters.get("parent");
        if (parentValues != null) {
            parent = iri(parentValues[0]);
        }
        Beverage beverage = new Beverage(
                iri("http://www.bem.ro/bem-schema#" + name),
                name,
                parent,
                parameters.get("description")[0],
                beverageContext
        );
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
