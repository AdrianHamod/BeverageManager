package ro.uaic.info.querybackendservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.repository.RepositoryResult;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.spring.support.RDF4JTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceService {

    private final RDF4JTemplate rdf4JTemplate;

    @Transactional
    public void loadData() throws FileNotFoundException {
        File dataFile = new File("src/main/resources/data.ttl");
        InputStream data = new FileInputStream(dataFile);
        rdf4JTemplate.consumeConnection(con -> {
            try {
                con.add(data, "", RDFFormat.TURTLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Transactional
    public void getAllData() {
        RepositoryResult<Statement> result = rdf4JTemplate.applyToConnection(con -> con.getStatements(null, null, null));
        for (Statement stms :
                result) {
            log.info("{} {} {}", stms.getSubject(), stms.getPredicate(), stms.getObject());
        }
    }
}
