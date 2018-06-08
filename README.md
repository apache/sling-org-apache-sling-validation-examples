[<img src="http://sling.apache.org/res/logos/sling.png"/>](http://sling.apache.org)

 [![Build Status](https://builds.apache.org/buildStatus/icon?job=sling-org-apache-sling-validation-examples-1.8)](https://builds.apache.org/view/S-Z/view/Sling/job/sling-org-apache-sling-validation-examples-1.8) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0) [![validation](https://sling.apache.org/badges/group-validation.svg)](https://github.com/apache/sling-aggregator/blob/master/docs/groups/validation.md)

# Apache Sling Validation Framework Examples

This module is part of the [Apache Sling](https://sling.apache.org) project.

## How To Run the Examples
1. Start a Sling launchpad
2. Install the `org.apache.sling.validation.api` and `org.apache.sling.validation.core` bundles:
    
    ```bash
    cd ../api
    mvn clean package sling:install
    cd ../core
    mvn clean package sling:install
    cd ../examples
    mvn clean package sling:install
    ```

## Invalid POST request
```bash
curl -u admin:admin -Fsling:resourceType=/apps/validationdemo/components/user -Fusername=johnsmith -FfirstName=John204 -FlastName=Smith http://127.0.0.1:8080/content/validationdemo/users/johnsmith.modify.html
```

Check that the resource has not been modified at http://127.0.0.1:8080/content/validationdemo/users/johnsmith.html.

## Valid POST request
```bash
curl -u admin:admin -Fsling:resourceType=/apps/validationdemo/components/user -Fusername=johnsmith -FfirstName=Johnny -FlastName=Bravo http://127.0.0.1:8080/content/validationdemo/users/johnsmith.modify.html
```

Check that the resource has been modified at http://127.0.0.1:8080/content/validationdemo/users/johnsmith.html.
