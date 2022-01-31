package ro.uaic.info.querybackendservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.rdf4j.model.IRI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.uaic.info.querybackendservice.dao.BeverageContextDao;
import ro.uaic.info.querybackendservice.dao.BeverageDao;
import ro.uaic.info.querybackendservice.dao.ProfileDao;
import ro.uaic.info.querybackendservice.model.Beverage;
import ro.uaic.info.querybackendservice.model.BeverageContext;
import ro.uaic.info.querybackendservice.model.Profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationService {

    public final static int MAX_RECOMMENDATIONS = 100;

    private final BeverageDao beverageDao;
    private final BeverageContextDao beverageContextDao;
    private final ProfileDao profileDao;

    @Transactional
    public Set<IRI> getSearchbarRecommendations(IRI profileId) {
        return getSearchbarRecommendations(profileId, MAX_RECOMMENDATIONS);
    }

    @Transactional
    public Set<IRI> getSearchbarRecommendations(IRI profileId, int recommendations) {
        Profile profile = profileDao.getById(profileId);
        if (profile.getBeveragePreferences() == null) {
            return Collections.emptySet();
        }
        Set<IRI> preferences = profile.getBeveragePreferences()
                        .stream()
                        .filter(BeverageContext::isContextBeveragePreferred)
                        .map(BeverageContext::getBeverage)
                        .collect(Collectors.toSet());

        return roundRobinNeighboursSearch(preferences, recommendations);
    }

    private Set<IRI> roundRobinNeighboursSearch(Set<IRI> currentNeighbours, int neighboursToGather) {
        Set<IRI> gatheredNeighbours = new HashSet<>();
        List<Queue<IRI>> allDirectNeighbours =
                currentNeighbours.stream().map(n -> new LinkedList<>(List.of(n))).collect(Collectors.toList());

        while (neighboursToGather > 0) {
            if(allDirectNeighbours.isEmpty()) {
                return gatheredNeighbours;
            }
            List<Queue<IRI>> emptyListsToRemove = new ArrayList<>();
            for (Queue<IRI> current :
                    allDirectNeighbours) {
                IRI currentIri = current.remove();

                if (gatheredNeighbours.add(currentIri)) {
                    neighboursToGather--;
                } else if (current.isEmpty()){
                    emptyListsToRemove.add(current);
                    continue;
                }

                List<IRI> children = getChildren(currentIri);
                current.addAll(
                        children.stream()
                                .filter(c -> !gatheredNeighbours.contains(c))
                                .collect(Collectors.toList())
                );

                IRI parent = getParent(currentIri);
                if (parent != null && !gatheredNeighbours.contains(parent)) {
                    current.add(parent);
                }

                if (current.isEmpty()) {
                    emptyListsToRemove.add(current);
                }
            }
            allDirectNeighbours.removeAll(emptyListsToRemove);
        }
        return gatheredNeighbours;
    }

    private IRI getParent(IRI current) {
        log.info("Current parent search for: {}", current);
        Optional<Beverage> beverage = beverageDao.getByIdOptional(current);
        if (beverage.isEmpty()) {
            return null;
        }
        return beverage.get().getParent();
    }

    private List<IRI> getChildren(IRI current) {
        log.info("Current children search for: {}", current);
        return beverageDao.listChildren(current)
                .stream()
                .map(Beverage::getBeverageId)
                .collect(Collectors.toList());
    }
}

