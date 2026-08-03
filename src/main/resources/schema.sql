CREATE TABLE IF NOT EXISTS login (
	id BIGSERIAL PRIMARY KEY,
	username VARCHAR(100) NOT NULL UNIQUE,
	password VARCHAR(255) NOT NULL,
	created_at TIMESTAMP NOT NULL DEFAULT now(),
	updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS tasks (
	id          BIGSERIAL PRIMARY KEY,
	username    VARCHAR(100) NOT NULL REFERENCES login(username),
	title       VARCHAR(255) NOT NULL,
	content     TEXT,
	start_date  DATE,
	end_date    DATE,
	created_at  TIMESTAMP NOT NULL DEFAULT now(),
	updated_at  TIMESTAMP NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_tasks_username ON tasks (username);