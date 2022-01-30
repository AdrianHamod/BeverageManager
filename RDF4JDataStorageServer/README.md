### Starting the RDF4J server
Download and unzip [Tomcat 9](https://tomcat.apache.org/download-90.cgi) (Under Core, zip)  
Download the [RDF4J 4.0.0-M1 SDK (zip)](https://rdf4j.org/download/).  
From the RDF4J sdk, take the `rdf4j-server.war` and place it under `$CATALINA_HOME/webapps` where `$CATALINA_HOME`
is the directory unzipped from the Tomcat archive.  
The default port the server will be running on is 8080.   
If you need to change the port, you can do so by updating `Connector port` in `$CATALINA_HOME/bin/server.xml`  
Start the server by executing `$CATALINA_HOME/bin/catalina.sh run`

### Creating the remote repository  
Add the `config.ttl` file from this directory to `[RDF4J_DATA]/Server/repositories/[REPOSITORY_ID]`  
On mac, the default path for `[RDF4J_DATA]` is `/Users/{user}/Library/Application Support/RDF4J`  
__If this path does not match you local setup__, one easy way to find out what the directory is in a running instance of
the RDF4J Server, is to go to http://localhost:PORT/rdf4j-server/home/overview.view in your browser and click on 
‘System’ in the navigation menu on the left. The data directory will be listed as one of the configuration settings of
the current server.

`[REPOSITORY_ID]` has to be `native_lucene_repo`


Restart the RDF4J server. (CMD + C, /bin/catalina.sh run)
You now have a RDF4J server with a native repository up and running.
You can now start the QueryBackendService.
