/*
 * Copyright 2018 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thoughtworks.go.apiv1.artifactstoreconfig;


import com.fasterxml.jackson.databind.JsonNode;
import com.thoughtworks.go.api.ApiController;
import com.thoughtworks.go.api.ApiVersion;
import com.thoughtworks.go.api.CrudController;
import com.thoughtworks.go.api.spring.ApiAuthenticationHelper;
import com.thoughtworks.go.apiv1.artifactstoreconfig.representers.ArtifactStoresRepresenter;
import com.thoughtworks.go.config.ArtifactStore;
import com.thoughtworks.go.config.ArtifactStores;
import com.thoughtworks.go.i18n.Localizer;
import com.thoughtworks.go.server.service.ArtifactStoreService;
import com.thoughtworks.go.spark.Routes;
import spark.Request;
import spark.Response;

import java.io.IOException;

import static spark.Spark.*;

public class ArtifactStoreConfigControllerDelegate extends ApiController implements CrudController<ArtifactStore> {
    private final ApiAuthenticationHelper apiAuthenticationHelper;
    private final ArtifactStoreService artifactStoreService;

    public ArtifactStoreConfigControllerDelegate(ApiAuthenticationHelper apiAuthenticationHelper, ArtifactStoreService artifactStoreService) {
        super(ApiVersion.v1);
        this.apiAuthenticationHelper = apiAuthenticationHelper;
        this.artifactStoreService = artifactStoreService;
    }

    @Override
    public String etagFor(ArtifactStore entityFromServer) {
        return null;
    }

    @Override
    public Localizer getLocalizer() {
        return null;
    }

    @Override
    public ArtifactStore doGetEntityFromConfig(String name) {
        return null;
    }

    @Override
    public ArtifactStore getEntityFromRequestBody(Request req) {
        return null;
    }

    @Override
    public String jsonize(Request req, ArtifactStore configurationProperties) {
        return null;
    }

    @Override
    public JsonNode jsonNode(Request req, ArtifactStore configurationProperties) throws IOException {
        return null;
    }

    @Override
    public String controllerBasePath() {
        return Routes.ArtifactStoreConfig.BASE;
    }

    @Override
    public void setupRoutes() {
        path(controllerBasePath(), () -> {
            before("", this::setContentType);
            before("/*", this::setContentType);

            before("", mimeType, apiAuthenticationHelper::checkAdminUserAnd401);
            before("/*", mimeType, apiAuthenticationHelper::checkAdminUserAnd401);

            get("", this::index);
        });
    }

    public String index(Request request, Response response) throws IOException {
        ArtifactStores artifactStores = artifactStoreService.getPluginProfiles();
        return writerForTopLevelObject(request, response,
                outputWriter -> ArtifactStoresRepresenter.toJSON(outputWriter, artifactStores));
    }
}
