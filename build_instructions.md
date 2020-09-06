# Build instructions

Maven build system is used in this project, with the configuration specified in `/pom.xml`.  

The repository contains two IntelliJ IDEA modules:  
- Productive code: `/src/app/java`
- Test cases: `/src/app/test`  

Several configurations are available in the project:
- `Launcher-Demo` - runs the application with the demo example
- `All in code-quality-analysis` - runs all unit tests
- `Clean` - cleans the build output files
- `Release` - creates a relase archive

When using the predefined build configurations, the following directories are used:
- Build output: `/build` 
- Analysis output: `/exported`  
- Release directory: `/release`

Unit tests are available in `*/Test` directories.  
