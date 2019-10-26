--
-- JBoss, Home of Professional Open Source
-- Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
-- contributors by the @authors tag. See the copyright.txt in the
-- distribution for a full listing of individual contributors.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- http://www.apache.org/licenses/LICENSE-2.0
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

-- You can use this file to load seed data into the database using SQL statements
insert into Member (id, name, email, phone_number) values (0, 'John Smith', 'john.smith@mailinator.com', '2125551212')

insert into Person (id, name, email, university, birthDate) values (0, 'John Smith', 'john.smith@mailinator.com', 'Baylor University', TO_DATE('29/10/1994', 'DD/MM/YYYY'))

insert into Team (id, name, rank, state) values (0, 'TeamA', 1, 'Accepted')
insert into Team (id, name, rank, state) values (1, 'TeamB', 5, 'Pending')
insert into Team (id, name, rank, state) values (2, 'TeamC', 3, 'Canceled')
insert into Team (id, name, rank, state) values (3, 'TeamD', 4, 'Accepted')
insert into Team (id, name, rank, state) values (4, 'TeamD', 2, 'Pending')
insert into Team (id, name, rank, state) values (5, 'TeamE', 1, 'Canceled')
insert into Team (id, name, rank, state) values (6, 'TeamF', 6, 'Accepted')

insert into Contest (id, name, capacity, registrationAllowed, registrationFrom, registrationTo) values (0, 'ContestA', 5, true, '01/02/2019', '01/12/2019')
insert into Contest (id, name, capacity, registrationAllowed, registrationFrom, registrationTo) values (1, 'ContestB', 8, true, '01/02/2019', '01/12/2019')
insert into Contest (id, name, capacity, registrationAllowed, registrationFrom, registrationTo) values (2, 'ContestC', 3, true, '01/02/2019', '01/12/2019')