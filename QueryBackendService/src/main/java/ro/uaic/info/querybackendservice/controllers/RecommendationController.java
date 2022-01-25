package ro.uaic.info.querybackendservice.controllers;

import lombok.RequiredArgsConstructor;
import org.eclipse.rdf4j.model.IRI;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.uaic.info.querybackendservice.model.IRILabel;
import ro.uaic.info.querybackendservice.service.RecommendationService;

import java.util.Set;

import static org.eclipse.rdf4j.model.util.Values.iri;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping
    Set<IRI> getRecommendations(@RequestParam String user, @RequestParam(required = false) Integer count) {
        IRI profile = iri(IRILabel.NS + user);
        if (count == null) {
            return recommendationService.getSearchbarRecommendations(profile);
        }
        return recommendationService.getSearchbarRecommendations(profile, count);
    }
}
