CREATE TABLE supervisor (
                            id SERIAL PRIMARY KEY,
                            github_id BIGINT UNIQUE NOT NULL,
                            username VARCHAR(255) NOT NULL,
                            mail VARCHAR(255)
);

CREATE TABLE "user" (
                        id SERIAL PRIMARY KEY,
                        github_id BIGINT UNIQUE,
                        username VARCHAR(255) NOT NULL
);

CREATE TABLE topic (
                       uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       title VARCHAR(255) NOT NULL,
                       description TEXT,
                       supervisor_id INTEGER NOT NULL REFERENCES supervisor(id)
);

CREATE TABLE supervisor_tag (
                                id SERIAL PRIMARY KEY,
                                tag VARCHAR(255) NOT NULL,
                                supervisor INTEGER NOT NULL REFERENCES supervisor(id),
                                supervisor_key INTEGER NOT NULL
);
CREATE TABLE topic_tag (
                                id SERIAL PRIMARY KEY,
                                tag VARCHAR(255) NOT NULL,
                                topic UUID NOT NULL REFERENCES topic(uuid),
                                topic_key INTEGER NOT NULL
);
CREATE TABLE topic_requirement (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           topic UUID NOT NULL REFERENCES topic(uuid),
                           topic_key INTEGER NOT NULL
);

CREATE TABLE information_file (
                                  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                  upload_time TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                  file_type VARCHAR(50),
                                  file_path VARCHAR(255),
                                  title VARCHAR(255),
                                  description TEXT,
                                  supervisor_id INTEGER REFERENCES supervisor(id),
                                  topic_id UUID REFERENCES topic(uuid)
);

CREATE TABLE link (
                      id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                      url VARCHAR(500) NOT NULL,
                      text VARCHAR(255),
                      supervisor_id INTEGER REFERENCES supervisor(id),
                      topic_id UUID REFERENCES topic(uuid)
);
