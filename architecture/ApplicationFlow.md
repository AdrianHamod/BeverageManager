# Application flow

## Authentication and authorization
BEM is a stateful service vending access to a non-alcoholic beverage RDF data representation.
In order to access the service a client must register one time and then authenticate using the registered email address and password. 
AWS API Gateway is front facing the service and handles communication with a 3rd party JWT authorizer. 
Once authenticated all requests carry a JWT header used to for authorizing access to the secured service endpoints.
Access to the credential storage is managed by an Auth service which exposes public endpoints. This service handles registration, sign in, email verification.

## Request handling
The BEM backend is divided into multiple HTTP web services. Each service is deployed in different containers using AWS Fargate for orchestration and deployment.
Requests are routed to each service using API Gateway and routed to each respective container using a load balancer.
Based on the API accessed the services handles:
- providing static/dynamic assets such as html, css, js and request forwarding (Frontend service)
- RDF queries upon the beverage onthology (Query backend service)
- authentication (Auth service)

## Data storage
User credentials are stored using their sha256 digest in Amazon RDS which is managed serverless relation database solution. 
ElasticSearch is a solid solution for representing RDFs. Benefits include fast retrievals in multi threaded manner, low cost storage since onthology size is 
more or less predictable, options to migrate from client managed hosts to a cloud provider solution if scaling and availability become a concern. 
This storage is queried by the backend service.
