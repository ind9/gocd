--
-- Copyright 2017 ThoughtWorks, Inc.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--    http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

CREATE TABLE PipelineStates (
    id                      BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    pipelineName            TEXT COLLATE UTF8_GENERAL_CI NOT NULL,
    locked                  BOOLEAN,
    lockedByPipelineId              BIGINT
);

ALTER TABLE PipelineStates ADD CONSTRAINT unique_pipeline_state UNIQUE (pipelineName(255));

INSERT INTO PipelineStates (pipelineName, locked, lockedByPipelineId) (select name, locked, id from pipelines where id in (select max(id) from pipelines where locked = true group by name));

DROP VIEW _stages;

CREATE VIEW _stages AS
    SELECT s.*,
      p.name pipelineName, p.buildCauseType, p.buildCauseBy, p.label pipelineLabel, p.buildCauseMessage, p.counter pipelineCounter, ps.locked, p.naturalOrder
    FROM stages s
    INNER JOIN pipelines p ON p.id = s.pipelineId
    LEFT OUTER JOIN PipelineStates ps on ps.lockedByPipelineId = s.pipelineId;


ALTER TABLE pipelines drop column locked;

--//@UNDO

