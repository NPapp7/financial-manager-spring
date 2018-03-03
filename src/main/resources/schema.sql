create table if not exists app_users (id number auto_increment, name varchar);

create table if not exists expenses (id number auto_increment, product varchar, category varchar, cost number, description varchar, dateOfTransaction varchar, typeOfTransaction varchar, relatedTaskId number);

create table if not exists tasks (id number auto_increment, taskName varchar, deadline varchar, description varchar);
