package ro.uaic.info.querybackendservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.rdf4j.model.IRI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.uaic.info.querybackendservice.dao.BeverageContextDao;
import ro.uaic.info.querybackendservice.dao.BeverageDao;
import ro.uaic.info.querybackendservice.model.Beverage;
import ro.uaic.info.querybackendservice.model.BeverageContext;

import java.io.FileNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BeverageService {

    private final BeverageDao beverageDao;
    private final BeverageContextDao beverageContextDao;

    @Transactional
    public Beverage createBeverage(Beverage beverage) {
        return beverageDao.save(beverage);
    }

    @Transactional
    public List<Beverage> getAllBeverages() {
        return beverageDao.list();
    }

    @Transactional
    public Beverage getBeverageById(IRI id) {
        return beverageDao.getById(id);
    }

    @Transactional
    public BeverageContext getBeverageContextById(IRI id) {
        return beverageContextDao.getById(id);
    }

    @Transactional
    public IRI deleteBeverage(IRI id) {
        beverageDao.delete(id);
        return id;
    }

    @Transactional
    public List<Beverage> fullTextSearchOnDescription(String term) {
        return beverageDao.searchBeveragesMatchingDescription(term);
    }

    @Transactional
    public void loadData() throws FileNotFoundException {
        beverageDao.loadFromResource();
    }
}
