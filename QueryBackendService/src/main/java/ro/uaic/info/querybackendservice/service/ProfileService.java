package ro.uaic.info.querybackendservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.rdf4j.model.IRI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.uaic.info.querybackendservice.dao.BeverageContextDao;
import ro.uaic.info.querybackendservice.dao.ProfileDao;
import ro.uaic.info.querybackendservice.model.BeverageContext;
import ro.uaic.info.querybackendservice.model.Profile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {

    private final ProfileDao profileDao;
    private final BeverageContextDao beverageContextDao;

    @Transactional
    public List<Profile> listProfiles() {
        return profileDao.list();
    }

    @Transactional
    public Profile getProfileById(IRI id) {
        return profileDao.getById(id);
    }

    @Transactional
    public Profile createProfile(Profile profile) {
        if (profile.getBeveragePreferences() != null) {
            for (BeverageContext context :
                    profile.getBeveragePreferences()) {
                beverageContextDao.save(context);
            }
        }
        log.info("Profile to save: {}", profile);
        return profileDao.save(profile);
    }

    @Transactional
    public Profile updateProfile(Profile profile) {
        profileDao.getById(profile.getId());
        return profileDao.save(profile);
    }

    @Transactional
    public Profile addBeverageContext(IRI id, BeverageContext beverageContext) {
        // throws RuntimeException if it does not find; which is good
        Profile profile = profileDao.getById(id);
        if (beverageContextDao.getByIdOptional(beverageContext.getId()).isPresent()) {
            throw new RuntimeException("BeverageContext with id " + beverageContext.getId() + " already exists");
        }
        BeverageContext context = beverageContextDao.save(beverageContext);
        profile.getBeveragePreferences().add(context);
        return profileDao.save(profile);
    }

    @Transactional
    public Profile updateBeverageContext(IRI id, BeverageContext beverageContext) {
        Profile profile = profileDao.getById(id);
        if (!profile.getBeveragePreferences().removeIf(context -> context.getId() == beverageContext.getId())) {
            return null;
        }
        BeverageContext context = beverageContextDao.getById(beverageContext.getId());
        BeverageContext updatedContext = beverageContextDao.save(beverageContext);
        profile.getBeveragePreferences().add(updatedContext);
        return profileDao.save(profile);
    }

    @Transactional
    public Profile removeBeverageContext(IRI id, IRI contextId) {
        Profile profile = profileDao.getById(id);
        if (!profile.getBeveragePreferences().removeIf(context -> context.getId() == contextId)) {
            return null;
        }
        beverageContextDao.delete(contextId);
        return profileDao.save(profile);
    }

    @Transactional
    public void deleteProfileById(IRI id) {
        Profile profile = profileDao.getById(id);
        for (BeverageContext context :
                profile.getBeveragePreferences()) {
            beverageContextDao.delete(context.getId());
        }
        profileDao.delete(id);
    }
}
