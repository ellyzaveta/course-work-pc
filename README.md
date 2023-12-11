# Inverted index

Implementation of a system for building an "Inverted index" data structure, as well as systems for using this data structure.

## To run the project

Running the project using Intelij Idea:

* Clone a repository from GitHub:
     * Open IntelliJ IDEA.
     * Click `File > New > Project from Version Control`.
     * In the URL field, paste the link: https://github.com/ellyzaveta/course-work-pc.git
     * Specify the directory where you want to save the project and click Clone.

     <br />
  
* Opening the project in IntelliJ IDEA:
     * IntelliJ IDEA will automatically detect that this is a Maven project and offer to import dependencies.
     * Click Enable Auto-Import to automatically update Maven dependencies when the pom.xml file changes.
     
     <br />
  
* Project configuration:
    * Make sure you have JDK and Maven installed. In IntelliJ IDEA, go to `File > Project Structure` and select the required JDK version and Maven settings.
    * In IntelliJ IDEA, go to `View > Tool Windows > Maven` and select Reimport All Maven Projects to ensure that all dependencies have been imported correctly.
  
    <br />
  
* Project launch:

    <br />
  
    * To run the project in Client-Server mode:
      
        <br />

        * Go to the `server > src > main > resources > application.properties` and specify the path of the directory with the input files under the `directory.path` property and the server port under the `server.port` property.
        * Go to the `client > src > main > resources > application.properties` and specify the host under the `server.host` property and the server port under the `server.port` property (the server port in the application.properties files for the server and client must match).
        * Go to `server > src > main > java > com.kpi.server > ServerApplication` and run the module by clicking the green triangle.
        * Go to `client > src > main > java > com.kpi.client > ClientApplication` and run the module by clicking the green triangle.

    <br />
  
    * To run project in Performance Comparison mode:

        <br />

        * Go to `performancecomparison > src > main >resources > application.properties` and specify the list of input file paths of different lengths under the `performance.testdata.paths` property and the server port under the `server.port` property.
        * Go to `performancecomparison > src > main > java > com.kpi.performancecomparison > PerformanceComparisonApplication` and run the module by clicking the green triangle.
        * After launching the application, open a browser and go to the address "http://localhost:{port}" (port is the server.port you defined in application.properties).

