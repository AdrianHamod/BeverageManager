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
import java.util.Map;

import static org.eclipse.rdf4j.model.util.Values.iri;

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
        Map<IRI, List<BeverageContext>> beverageContexts = beverageContextDao.groupById();

        for (Beverage beverage :
                beverages) {
            List<BeverageContext> context = beverageContexts.get(
                    iri(beverage.getBeverageId().stringValue() + "Context"));

            if (context == null || context.isEmpty()) {
                continue;
            }
            beverage.setContext(context.get(0));
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
        return beverageContextDao.getById(id);
    }

    @Transactional
    public IRI deleteBeverage(IRI id) {
        beverageDao.delete(id);
        beverageContextDao.delete(iri(id.stringValue() + "Context"));
        return id;
    }
}
