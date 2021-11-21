# Query service

This is an HTTP based service implemented using SpringBoot. The service is using [RDF4J API](https://rdf4j.org/documentation/) to model the beverage ontology and communicate 
with the ElasticSearch storage. 
The query backend exposes several rest endpoints, which handle [operations on beverages](https://app.swaggerhub.com/apis-docs/BEM-Project/BEM/1.0.0#/Beverage)
This service easily scales vertically due to the Servlet API built-in multithreading, thus benefiting from the entirety of the host available resources.
Horizontal scaling is achieved through the stateless design of the service. AWS Fargate handles host provisioning when the demand is higher than the available
resources. 
Assuming the service SLA is at most equal to AWS Fargate [SLA](https://aws.amazon.com/about-aws/whats-new/2017/12/amazon-compute-service-level-agreement-extended-to-amazon-ecs-and-aws-fargate/),
the service should provide a 99.99% uptime and availability.
