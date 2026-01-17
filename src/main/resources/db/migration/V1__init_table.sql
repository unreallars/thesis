CREATE TABLE supervisor (
                            id SERIAL PRIMARY KEY,
                            fach_id uuid default gen_random_uuid() NOT NULL,
                            github_id BIGINT UNIQUE NOT NULL,
                            username VARCHAR(255) NOT NULL,
                            mail VARCHAR(255),
                            constraint unique_fach_id_sup unique (fach_id)
);

CREATE TABLE "user" (
                        id SERIAL PRIMARY KEY,
                        fach_id uuid default gen_random_uuid() NOT NULL,
                        github_id BIGINT UNIQUE,
                        username VARCHAR(255) NOT NULL,
                        constraint unique_fach_id_user unique (fach_id)
);

CREATE TABLE topic (
                       id SERIAL PRIMARY KEY,
                       fach_id uuid default gen_random_uuid() NOT NULL,
                       title VARCHAR(255) NOT NULL,
                       description TEXT,
                       supervisor_id UUID NOT NULL REFERENCES supervisor(fach_id),
                       constraint unique_fach_id_topic unique (fach_id)
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
                                topic INTEGER NOT NULL REFERENCES topic(id),
                                topic_key INTEGER NOT NULL
);
CREATE TABLE topic_requirement (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           topic INTEGER NOT NULL REFERENCES topic(id),
                           topic_key INTEGER NOT NULL
);

CREATE TABLE information_file (
                                  id SERIAL PRIMARY KEY,
                                  fach_id uuid default gen_random_uuid() NOT NULL,
                                  upload_time TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                  file_type VARCHAR(50),
                                  file_path VARCHAR(255),
                                  title VARCHAR(255),
                                  description TEXT,
                                  supervisor_id UUID REFERENCES supervisor(fach_id),
                                  topic_id UUID REFERENCES topic(fach_id),
                                  constraint unique_fach_id_if unique (fach_id)
);

CREATE TABLE link (
                      id SERIAL PRIMARY KEY,
                      fach_id uuid default gen_random_uuid() NOT NULL,
                      url VARCHAR(500) NOT NULL,
                      text VARCHAR(255),
                      supervisor_id UUID REFERENCES supervisor(fach_id),
                      topic_id UUID REFERENCES topic(fach_id),
                      constraint unique_fach_id_link unique (fach_id)
);
