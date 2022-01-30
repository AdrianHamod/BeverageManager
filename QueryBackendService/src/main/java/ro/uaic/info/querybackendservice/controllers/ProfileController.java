package ro.uaic.info.querybackendservice.controllers;

import lombok.RequiredArgsConstructor;
import org.eclipse.rdf4j.model.IRI;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.uaic.info.querybackendservice.api.request.PostProfileRequest;
import ro.uaic.info.querybackendservice.api.request.PutProfileRequest;
import ro.uaic.info.querybackendservice.model.BeverageContext;
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
        return profileService.listProfiles();
    }

    @GetMapping("/{id}")
    Profile getProfileById(@PathVariable String id) {
        return profileService.getProfileById(iri(IRILabel.NS, id));
    }

    @PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    Profile createProfile(@Valid @RequestBody PostProfileRequest req) {
        return profileService.createProfile(
                new Profile(
                        req.getUsername(),
                        req.getAge(),
                        req.getGender(),
                        req.getCountryCode(),
                        req.getBeveragePreferences()
                )
        );
    }

    @PutMapping(value = "/{profileId}", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    ResponseEntity<Profile> updateProfile(
            @PathVariable String profileId,
            @RequestBody PutProfileRequest req
    ) {
        Profile profile = new Profile();
        profile.setId(iri(IRILabel.NS, profileId));
        profile.setUsername(req.getUsername());
        profile.setAge(req.getAge());
        profile.setGender(req.getGender());
        profile.setCountryCode(req.getCountryCode());
        Profile updatedProfile = profileService.updateProfile(profile);
        if (updatedProfile == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(profile);
    }

    @PatchMapping("/{profileId}")
    ResponseEntity<Profile> addBeverageContext(
            @PathVariable String profileId,
            @RequestBody BeverageContext beverageContext) {
        IRI profileIri = iri(IRILabel.NS, profileId);
        Profile profile = profileService.addBeverageContext(
                profileIri,
                beverageContext
        );

        if (profile != null) {
            return ResponseEntity.ok(profile);
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{profileId}/{beverageContextId}")
    ResponseEntity<Profile> removeBeverageContext(
            @PathVariable String profileId,
            @PathVariable String beverageContextId
    ) {
        IRI profileIri = iri(IRILabel.NS, profileId);
        IRI beverageContextIri = iri(IRILabel.NS, beverageContextId);
        Profile updatedProfile = profileService.removeBeverageContext(profileIri, beverageContextIri);

        if (updatedProfile != null) {
            return ResponseEntity.ok(updatedProfile);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    IRI deleteProfile(@PathVariable String id) {
        IRI iriId = iri(IRILabel.NS, id);
        profileService.deleteProfileById(iriId);
        return iriId;
    }
}
