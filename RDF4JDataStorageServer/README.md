###Starting the RDF4J server
Download and unzip [Tomcat 9](https://tomcat.apache.org/download-90.cgi) (Under Core, zip)  
Download the [RDF4J 4.0.0-M1 SDK (zip)](https://rdf4j.org/download/).  
From the RDF4J sdk, take the `rdf4j-server.war` and place it under `$CATALINA_HOME/webapps` where `$CATALINA_HOME` 
is the directory unzipped from the Tomcat archive.  
The default port the server will be running on is 8080.   
If you need to change the port, you can do so by updating `Connector port` in `$CATALINA_HOME/bin/server.xml`  
Start the server by executing `$CATALINA_HOME/bin/catalina.sh run`

###Creating the remote repository
Following instructions from https://rdf4j.org/documentation/tools/console/:
- in the RDF4J sdk directory previously downloaded, start the RDF4J console by running `bin/console.sh`
- connect `http://localhost:<PORT>/rdf4j-server` (default port is 8080)
- run `create native`:
    - for repository id, input `native_repo`
    - for repository title choose any title you like
    - for the rest choose the defaults by pressing enter

Restart the RDF4J server. (CMD + C, /bin/catalina.sh run)
You now have a RDF4J server with a native repository up and running.
You can now start the QueryBackendService.
