/*
 * Copyright 2020 Cerner Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.spinnaker.echo.microsoftteams;

import groovy.transform.Canonical;
import retrofit.client.Response;

// Remove these after testing
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;

@Canonical
public class MicrosoftTeamsService {
  MicrosoftTeamsClient microsoftTeamsClient;

  public MicrosoftTeamsService(MicrosoftTeamsClient microsoftTeamsClient) {
    this.microsoftTeamsClient = microsoftTeamsClient;
  }

  public Response sendMessage(String webhookUrl, MicrosoftTeamsMessage message) {
    log.info("Microsoft Teams Message: " + serializeObject(message));
    return microsoftTeamsClient.sendMessage(webhookUrl, message);
  }

  private String serializeObject(Object obj) {
    try {
      ObjectMapper mapper = new ObjectMapper(); 
      mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
      String jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);

      return jsonResult;
    } catch (Exception e) {
      log.error("Error occurred. " + e);
      throw e;
    }
  }
}
