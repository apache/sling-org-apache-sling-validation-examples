/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.sling.validation.examples.servlets;

import java.io.IOException;
import java.util.Locale;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestDispatcherOptions;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.apache.sling.validation.ValidationFailure;
import org.apache.sling.validation.ValidationResult;
import org.apache.sling.validation.ValidationService;
import org.apache.sling.validation.model.ValidationModel;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@SlingServletResourceTypes(
        resourceTypes = "/apps/validationdemo/components/user",
        selectors = {"modify"},
        methods = "POST"
)
@Component(service = Servlet.class)
public class ModifyUserServlet extends SlingAllMethodsServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Reference
    private ValidationService validationService;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        ValueMap requestParameters = request.adaptTo(ValueMap.class);
        String[] resourceTypeValues = requestParameters.get("sling:resourceType", String[].class);
        String resourceType = null;
        if (resourceTypeValues != null && resourceTypeValues.length > 0) {
            resourceType = resourceTypeValues[0];
        }
        if (resourceType != null && !"".equals(resourceType)) {
            String resourcePath = request.getRequestPathInfo().getResourcePath();
            ValidationModel vm = validationService.getValidationModel(resourceType, resourcePath, false);
            if (vm != null) {
                ValidationResult vr = validationService.validate(requestParameters, vm);
                if (vr.isValid()) {
                    RequestDispatcherOptions options = new RequestDispatcherOptions();
                    options.setReplaceSelectors(" ");
                    request.getRequestDispatcher(request.getResource(), options).forward(request, response);
                } else {
                    response.setContentType("application/json");
                    JsonGenerator generator = Json.createGenerator(response.getWriter());
                    
                    generator.writeStartObject();
                    generator.write("success", false);
                    generator.writeStartArray("failures");
                    for (ValidationFailure failure : vr.getFailures()) {
                        generator.writeStartObject();
                        generator.write("message", failure.getMessage(request.getResourceBundle(Locale.US)));
                        generator.write("location", failure.getLocation());
                        generator.writeEnd();
                    }
                    generator.writeEnd();
                    generator.writeEnd();
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
        }
    }
}
