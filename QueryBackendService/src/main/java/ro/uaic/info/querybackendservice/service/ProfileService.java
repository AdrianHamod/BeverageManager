package ro.uaic.info.querybackendservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.rdf4j.model.IRI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.uaic.info.querybackendservice.dao.ProfileDao;
import ro.uaic.info.querybackendservice.model.Profile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {

    private final ProfileDao profileDao;

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
        return profileDao.save(profile);
    }

    @Transactional
    public void deleteProfileById(IRI id) {
        profileDao.delete(id);
    }
}
