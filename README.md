# Code Quality Analysis Using Network Science

This tool calculates code quality metrics by analyzing the network of dependencies between source code elements.  
Structural quality grade of the source code is evaluated with the combination of metrics based on complex network properties.  

The implementation is done as a Java 1.8 console application.

Currently, only Java source code is supported.  

Features:
- Generating a quality analysis report in HTML
- Exporting dependency networks in CSV, DOT, GEXF or GML
- Highly configurable analysis

More information about how this tool works can be found in the [introduction](introduction.md).

# Setup

The application is distributed as a JAR binary, so there are no installation steps apart from copying the project files or compiling the source.

### Option 1 (simple): Download the release

Download and extract the release archive from project releases on this repository.  

Project archive contains the following:
- Application executable:  `/code-quality-analysis-v1.1.jar`  
- Configuration and resource files directory: `/resources`  
- Sample source code for analysis: `/DemoExample`  

### Option 2 (advanced): Build and run the source code
Follow the [build instructions](build_instructions.md).

# Usage
## Starting the analysis
The analysis is started with the following command in the release archive:  
`java -jar code-quality-analysis-v1.1.jar`

Command line arguments are specified as follows:  
 `-f,--graph-format <arg>`   Graph export format [csv, dot, gexf, gml]  
 `-h,--help`                 Display help  
 `-n,--project_name <arg>`   Project name (required)  
 `-o,--output <arg>`         Path to output directory (required)  
 `-s,--source <arg>`         Path to source code (required)  
 `-v,--verbose`              Enable verbose output  

After a successful analysis, the output directory will contain:
- The analysis report: `/<project_name>/report.html`
- A directory with graph files: `/<project_name>/graphs`

## Configuring the analysis parameters
The evaluated quality grade is highly impacted by the analysis parameters given by the configuration files.  

For more details, read the [configuration instructions](configuration_instructions.md).

# Acknowlegdements
This project was supported by [School of Electrical Engineering, University of Belgrade](https://www.etf.bg.ac.rs/en).

## Libraries
The following libraries were used in this project:
- JUnit 5.5.2
- Apache Maven 3.3.9
- Spoon 7.2.0
- JGraphT 1.3.0
- Gephi Toolkit 0.9.2
- JSON 20190722
- Gagawa 1.0.1
- Commons CLI 1.4


# License
The MIT License (MIT)

Copyright 2020 University of Belgrade

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
