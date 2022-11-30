CREATE TABLE  IF NOT EXISTS team
(
    teamid      BIGINT AUTO_INCREMENT NOT NULL,
    league      VARCHAR(255),
    city        VARCHAR(255),
    country     VARCHAR(255),
    capitan     VARCHAR(255),
    coach       VARCHAR(255),
    CONSTRAINT pk_team PRIMARY KEY (teamid)
    );

CREATE TABLE IF NOT EXISTS referee
(
    refereeid  BIGINT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(255),
    surname    VARCHAR(255),
    league     VARCHAR(255),
    country    VARCHAR(255),
    CONSTRAINT pk_referee PRIMARY KEY (refereeid)
    );

CREATE TABLE  IF NOT EXISTS match
(
    matchid       BIGINT AUTO_INCREMENT NOT NULL,
    match_date    date,
    match_stadium VARCHAR(255),
    referee_id    BIGINT,
    team_id       BIGINT,
    CONSTRAINT pk_match PRIMARY KEY (matchid)
    );

ALTER TABLE match
    ADD CONSTRAINT FK_MATCH_ON_REFEREE FOREIGN KEY (referee_id) REFERENCES referee (refereeid) ON DELETE CASCADE;

ALTER TABLE match
    ADD CONSTRAINT FK_MATCH_ON_TEAM FOREIGN KEY (team_id) REFERENCES team (teamid) ON DELETE CASCADE;