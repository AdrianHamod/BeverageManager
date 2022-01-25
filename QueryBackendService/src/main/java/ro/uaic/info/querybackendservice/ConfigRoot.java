package ro.uaic.info.querybackendservice;

import org.eclipse.rdf4j.spring.RDF4JConfig;
import org.eclipse.rdf4j.spring.support.connectionfactory.RepositoryConnectionFactory;
import org.eclipse.rdf4j.spring.tx.RDF4JRepositoryTransactionManager;
import org.eclipse.rdf4j.spring.tx.TransactionalRepositoryConnectionFactory;
import org.eclipse.rdf4j.spring.tx.TxProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

@Configuration
@Import(RDF4JConfig.class)
public class ConfigRoot {

    @Bean
    RDF4JRepositoryTransactionManager getTxManager(
            @Autowired RepositoryConnectionFactory txConnectionFactory) {
        RDF4JRepositoryTransactionManager txManager = new RDF4JRepositoryTransactionManager(
                (TransactionalRepositoryConnectionFactory) txConnectionFactory);
        txManager.setTransactionSynchronization(AbstractPlatformTransactionManager.SYNCHRONIZATION_NEVER);
        return txManager;
    }

    @Bean
    TxProperties txProperties() {
        TxProperties txProperties = new TxProperties();
        txProperties.setEnabled(true);
        return txProperties;
    }
}
