package ro.uaic.info.querybackendservice.controllers;

import lombok.RequiredArgsConstructor;
import org.eclipse.rdf4j.model.IRI;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.uaic.info.querybackendservice.model.Beverage;
import ro.uaic.info.querybackendservice.model.IRILabel;
import ro.uaic.info.querybackendservice.model.Profile;
import ro.uaic.info.querybackendservice.service.ProfileService;
import ro.uaic.info.querybackendservice.service.RecommendationService;

import javax.validation.Valid;
import java.util.List;

import static org.eclipse.rdf4j.model.util.Values.iri;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;
    private final RecommendationService recommendationService;

    @GetMapping
    List<Profile> getAllProfile() {
        return null;
    }

    @GetMapping("/{id}")
    Profile getProfileById(@PathVariable String id) {
        return profileService.getProfileById(iri(IRILabel.NS, id));
    }

    @PostMapping
    Profile createProfile(@Valid @RequestBody Profile profile) {
        return profileService.createProfile(profile);
    }

    @DeleteMapping("/{id}")
    IRI deleteProfile(@PathVariable String id) {
        IRI iriId = iri(IRILabel.NS, id);
        profileService.deleteProfileById(iriId);
        return iriId;
    }
}
