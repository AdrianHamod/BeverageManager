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

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BeverageService {

    private final BeverageDao beverageDao;
    private final BeverageContextDao beverageContextDao;

    @Transactional
    public Beverage createBeverage(Beverage beverage) {
        BeverageContext beverageContext = beverageContextDao.save(beverage.getContext());
        Beverage result = beverageDao.save(beverage);
        result.setContext(beverageContext);
        return result;
    }

    @Transactional
    public List<Beverage> getAllBeverages() {
        List<Beverage> beverages = beverageDao.list();
        for (Beverage beverage:
             beverages) {
            beverage.setContext(
                    beverageContextDao.getBeverageContextById(beverage.getBeverageId())
            );
        }
        return beverages;
    }

    @Transactional
    public Beverage getBeverageById(IRI id) {
        log.info("[SearchingById] {}", id);
        return beverageDao.getById(id);
    }

    @Transactional
    public BeverageContext getBeverageContextById(IRI id) {
        log.info("[SearchingByContextId] {}", id);
        return beverageContextDao.getBeverageContextById(id);
    }

    @Transactional
    public IRI deleteBeverage(IRI id) {
        beverageDao.delete(id);
        return id;
    }

    /**
     * Calling BeverageDao::list directly does not work.
     * But this list call returns the saved beverage. (but not the others)
     * Same happens if BeverageDao::getById would be called here instead of list
     */
    @Transactional
    public List<Beverage> createThenFetchBeverages(Beverage beverage) {
        beverageContextDao.save(beverage.getContext());
        IRI id = beverageDao.saveAndReturnId(beverage, beverage.getBeverageId());
        log.info("[SearchingById] {}", id);
        return beverageDao.list();
    }
}
