[![Apache Sling](https://sling.apache.org/res/logos/sling.png)](https://sling.apache.org)

&#32;[![Build Status](https://ci-builds.apache.org/job/Sling/job/modules/job/sling-org-apache-sling-validation-examples/job/master/badge/icon)](https://ci-builds.apache.org/job/Sling/job/modules/job/sling-org-apache-sling-validation-examples/job/master/)&#32;[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=apache_sling-org-apache-sling-validation-examples&metric=coverage)](https://sonarcloud.io/dashboard?id=apache_sling-org-apache-sling-validation-examples)&#32;[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=apache_sling-org-apache-sling-validation-examples&metric=alert_status)](https://sonarcloud.io/dashboard?id=apache_sling-org-apache-sling-validation-examples)&#32;[![validation](https://sling.apache.org/badges/group-validation.svg)](https://github.com/apache/sling-aggregator/blob/master/docs/group/validation.md) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

# Apache Sling Validation Framework Examples

This module is part of the [Apache Sling](https://sling.apache.org) project.

## How To Run the Examples
1. Start a Sling Starter (or some other distribution)
2. Install the `org.apache.sling.validation.api` and `org.apache.sling.validation.core` bundles
3. Build and install the `org.apache.sling.validation.examples` bundle
    
    ```bash
    mvn clean package sling:install
    ```

## Invalid POST request
```bash
curl -u admin:admin -Fsling:resourceType=/apps/validationdemo/components/user -Fusername=johnsmith -FfirstName=John204 -FlastName=Smith http://127.0.0.1:8080/content/validationdemo/users/johnsmith.modify.html
```

Check that the resource has not been modified at <http://127.0.0.1:8080/content/validationdemo/users/johnsmith.html>.

## Valid POST request
```bash
curl -u admin:admin -Fsling:resourceType=/apps/validationdemo/components/user -Fusername=johnsmith -FfirstName=Johnny -FlastName=Bravo http://127.0.0.1:8080/content/validationdemo/users/johnsmith.modify.html
```

Check that the resource has been modified at <http://127.0.0.1:8080/content/validationdemo/users/johnsmith.html>.
