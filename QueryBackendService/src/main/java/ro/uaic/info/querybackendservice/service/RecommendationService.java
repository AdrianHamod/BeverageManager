package ro.uaic.info.querybackendservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.rdf4j.model.IRI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.uaic.info.querybackendservice.dao.BeverageContextDao;
import ro.uaic.info.querybackendservice.dao.BeverageDao;
import ro.uaic.info.querybackendservice.dao.ProfileDao;
import ro.uaic.info.querybackendservice.model.Profile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
        Map<Boolean, Set<IRI>> preferences = profile.getBeveragePreferences();
        Set<IRI> positivePreferences = preferences.get(true);

        return roundRobinNeighboursSearch(positivePreferences, recommendations);
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
                if (gatheredNeighbours.add(current.remove())) {
                    neighboursToGather--;
                }
                if (current.isEmpty()) {
                    // may produce infinite loop?
                    emptyListsToRemove.add(current);
                    continue;
                }

                List<IRI> children = getChildren(current.peek());
                current.addAll(children);

                IRI parent = getParent(current.peek());
                if (parent != null) {
                    current.add(parent);
                }
            }
            allDirectNeighbours.removeAll(emptyListsToRemove);
        }
        return gatheredNeighbours;
    }

    private IRI getParent(IRI current) {
        return null;
    }

    private List<IRI> getChildren(IRI current) {
        return new ArrayList<>();
    }


}
