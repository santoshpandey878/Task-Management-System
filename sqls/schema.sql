create database taskmanagementsystem;

CREATE TYPE status_type as ENUM('IN_PROCESS_QUEUE', 'EXECUTING', 'RESOLVED', 'FAILED', 'POSTPONED');

create table task_detail(
	id bigserial PRIMARY KEY,
	title varchar(100),
	description text,
	priority integer,
	create_date timestamp,
	update_date timestamp,
	due_date timestamp,
	resolve_date timestamp,
    remindme_date timestamp,
    status status_type
);