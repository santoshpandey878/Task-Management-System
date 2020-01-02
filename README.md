# Task-Management-System


# Goal

Implement a simple Task Management System (only backend). Incoming tasks are pushed to a queue for further processing. In addition, tasks can be postponed with a "remind me at" date for later.

The application should implement the following features:

A scheduler should create new tasks at a random interval. Tasks should be persisted in the database and should have, at least, the following fields:

id (uuid)

createdAt

updatedAt

dueDate

resolvedAt

title

description

priority

status

REST Endpoints for communication with the Frontend.

For communication with the frontend, DTOs should be used.

Push the tasks from the backend to frontend order by their dueDate and the priority (but ordering must be done on backend ).

If new tasks come in, they should be automatically added to the list of tasks without the need to manually refresh the page (yes, this is pretty much another backend requirement)



# Implementation, Functionality & My Assumptions

Application is implemented using Spring boot and run at port 9082 (http://localhost:9082/taskmanagementsystem). When backend application started, it will create a QUEUE and Consumer Service to process queue as soon as task available in queue.

**1.** Implemented a task scheduler "TaskTimer" to produce task asynchronously on every 10 seconds with fixed delay of 3 seconds.

**2.** Created "TaskQueueProducerService" to create task and put it into Queue.

**3.** Created "TaskQueueConsumerService" to consume task. This class is responsible to handle task operations once task is put into queue. It will get the task and can perform required operations. As I don't have detail to what task I have to perfom, I have made following assumptions:

   **Assumptions:**
   
   a. Get the front task from queue.
   
   b. If task is not postponed, mark status of task to be executing and sleep for 20 seconds. (Here, we can perform further           operations.)
   
   c. Mark status of the task to resolved and update it.
   
   d. If task is postponed and dueDate is greater than current date, put it into queue again. (This steps happen until task         dueDate not passed.)
   
   e. If dueDate passed, mark status of task to be executing and sleep for 20 seconds. (Here, we can perform further               operations. Then mark status of the task to resolved and update it.

   **Note:** I have also put the detail assumption in comment section of method.

**4.** Implemented APIs to fetch, create, update, delete and postpone a task.

**5.** Using websocket to send data to frontend if any new task created.

**6.** Tasks sent to frontend is order by dueDate and priority.

**7.** Using spring AOP for application logging and to notify to send data to frontend when task created.

**8.** Using DTO to interact with client.

**9.** Handle most of the exceptions occured during operations.

**10.** Using static name with priority to assign title for a Task.

**11.** Priority is generated using random number between 0 to 10(exclusive).

**12.** Blocking queue is used to handle multiple thread operations on queue.

**13.** Using spring based declarative transaction management.

**14.** Applied different validations for fields.
 
 
 
 # Technologies used
 
 Java 8
 
 Spring Boot
 
 RDBMS(PostgreSQL)
 
 Spring Data JPA - (Hibernate)
 
 Websocket
 
 Spring AOP
 



# API Documentation

TaskDto is used for the interaction with client. It has these fields:

BaseURL:  http://localhost:9082/taskmanagementsystem/rest

Structure

{
    "id": <number>,
    "title": <string>,
    "description": <string>,
    "priority": <number - between 0 to 9>,
    "createdAt": <Date>,
    "updatedAt": <Date>,
    "dueDate": <Date>,
    "resolvedAt": <Date>,
    "remindmeAt": <Date>,
    "status": <string - one from "IN_PROCESS_QUEUE", "EXECUTING", "RESOLVED", "FAILED", "POSTPONED">
}

**Create Task**

The following API create Task. (Although, this API does not used in our application because scheduler is responsible to create Task on every 10 seconds.)

POST /tasks

{
    "title": "task1",
    "description": "description for task 1",
    "priority": 1,
    "dueDate": "2019-12-09 11:30:10"
}

response: HTTP 200 OK 


**List all Tasks**

The following API gets all the tasks those exist in the system

GET /tasks

response:

HTTP 200 OK

[
  {
    "id": 17,
    "createdAt": "2019-12-11 03:23:39",
    "dueDate": "2019-12-11 03:23:39",
    "resolvedAt": "2019-12-11 03:26:40",
    "title": "Task with priority-0",
    "description": "Task with priority-{0}",
    "priority": 0,
    "status": "RESOLVED"
  },
  {
    "id": 24,
    "createdAt": "2019-12-11 03:24:49",
    "dueDate": "2019-12-11 03:24:49",
    "resolvedAt": "2019-12-11 03:29:20",
    "title": "Task with priority-0",
    "description": "Task with priority-{0}",
    "priority": 0,
    "status": "RESOLVED"
  }
]


**Get concrete Task details**

The following API gets the particular task if it exists in the system

GET /tasks/{id}

Example response:

HTTP 200 OK

{
    "id": 17,
    "createdAt": "2019-12-11 03:23:39",
    "dueDate": "2019-12-11 03:23:39",
    "resolvedAt": "2019-12-11 03:26:40",
    "title": "Task with priority-0",
    "description": "Task with priority-{0}",
    "priority": 0,
    "status": "RESOLVED"
 }
 

**Update Task details**

The following API update the details of the particular task if it exists in the system.

PUT /tasks/{id}

{
  "title": "Task with priority-1",
  "description": "Description for task",
  "priority": 2,
  "dueDate": "2019-12-07 12:30:24"
}

Example response:

HTTP 200 OK


**Postpone Task**

The following API postpone task.

PUT /tasks/postpone/{id}

{
  "title": "Task with priority-1",
  "status": "POSTPONED",
  "remindmeAt": "2019-12-07 12:28:2d",
  "dueDate": "2019-12-07 12:30:24"
}

Example response:

HTTP 200 OK
