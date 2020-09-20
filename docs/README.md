# Duke User Guide

* [1. Introduction](#1-introduction)
* [2. Quick Start](#2-quick-start)
* [3. Command Format](#3-command-format)
* [4. Features](#4-features)
    + [4.1. Add tasks](#41-add-a-task)
        + [4.1.1 Todos](#411-todo---adds-a-todo-task)
        + [4.1.2 Events](#412-event---adds-an-event-task)
        + [4.1.3 Deadlines](#413-deadline---adds-a-deadline-task)
    + [4.2. Mark task as done](#42-done---mark-task-as-done)
    + [4.3. Display all tasks](#43-list---displays-all-tasks-in-list)
    + [4.4. Find task by keyword](#44-find---find-tasks-that-match-keyword)
    + [4.5. Delete tasks](#45-delete---deletes-task-from-list)
    + [4.6. Exiting Duke](#46-bye---exits-the-program)
- [5. Command Summary](#5-command-summary)   



## 1. Introduction
Duke is a CLI-based application that 
keeps track of your tasks. This application is
for people who prefer using a Command-Line Interface.


## 2. Quick Start

Ensure that you have Java 11 installed in your Computer.

1.Download the latest Duke_IP.jar from [here](https://github.com/BenardoTang/ip/releases).

2.Copy the file to the folder you want to use as the home folder for your Duke program.

3.Open a command window in that folder.

4.Run the command `java -jar Duke_IP.jar`.

5.You should see something like this below:
   ```
 ____        _        
|  _ \ _   _| | _____ 
| | | | | | | |/ / _ \
| |_| | |_| |   <  __/
|____/ \__,_|_|\_\___|
___________________________________________________
Hello! I'm Duke d(^u^)
What can I do for you?
___________________________________________________

   ```    

## 3. Command Format
Words enclosed within angle brackets `<>` are the parameters to be supplied by the user. Other keywords stated are compulsory and should be included.

For example, in `event <Task Description> /at <Venue>`, `event` is the command keyword that adds an Event task. `<Task Description>` is the description of the event to be supplied by you. `<Venue>` represents additional information about the event to be added, like the date and time.

A valid input would be `event Meeting /at Mon 4pm`.



## 4. Features

### 4.1 Add a task
Duke allows you to add 3 different types of tasks: todo, deadline and event. Refer to the subheadings below for more information on each type.
 
 
#### 4.1.1 `todo` - Adds a todo task
 
This command adds a todo task to the current task list. ToDos contain a description only.
 
Format: `todo <Description>`
 
Example of usage:`todo watch movie` - Adds a todo task with description "watch movie" to the list
 
 
Expected outcome:
 
```
______________________________________________________
todo watch movie
Got it. I've added this task: [T][('n')]watch movie.
Now you have 3 tasks in the list.
______________________________________________________
```
 
&nbsp;

#### 4.1.2 `event` - Adds an event task
 
This command adds an event task to the current task list. Events contain a description and a remark (usually the event date and time).
 
Format:
 `event <Description> /at <Venue>`
 
- Adds an event and its event time and/or place to the list
 
- Text that follows **"/at"** will be placed in the venue section of the event.
 
- Valid `<Venue>` formats include:
    + Any String value (e.g. **Mon 4pm**) 

Example of usage:
`event Tom's birthday /at Tom's house 3pm`
 
Expected outcome:
```
______________________________________________________
event Tom's birthday /at Tom's house 3pm
Got it. I've added this task: [E][('n')]Tom's birthday (at Tom's house 3pm).
Now you have 4 tasks in the list.
______________________________________________________
``` 

&nbsp;
 
#### 4.1.3 `deadline` - Adds a deadline task
 
This command adds a deadline task to the current task list. Deadlines contain a description and a date.
 
Format:
 `deadline <Description> /by <time>`
 
- Adds a deadline and its time and/or place to the list
 
- Text that follows **"/by"** will be placed in the time section of the deadline.
 
- Valid `<time>` formats include:
    + Any String value (e.g. **31 Dec 12pm**) 
 
Example of usage:
`deadline CS2113 iP submission /by 31 Dec 12pm`
 
Expected outcome:
 
```
______________________________________________________
deadline CS2113 iP submission /by 31 Dec 12pm
Got it. I've added this task: [D][('n')]CS2113 iP submission (by 31 Dec 12pm).
Now you have 5 tasks in the list.
______________________________________________________
```
 
&nbsp;
 
### 4.2 `done` - Mark task as done

This command marks a task as completed. When executed, the task completion box of the specified task changes `[('n')]` (not completed) to `[/]` (completed).
 
Format:
 `done <Index>`
 
 - Marks the task as done at the specified index.
  
 - `<Index>` refers to the index number of a specific task in the displayed task list.
  
 - The index must be a positive integer: 1, 2, 3, etc.

Example of usage:
`done 1`

Assuming this is our existing list:

```
______________________________________________________
Here are the tasks in your list: 
1.[T][('n')]watch movie
2.[E][('n')]Tom's birthday (at Tom's house 3pm)
3.[D][('n')]CS2113 iP submission (by 31 Dec 12pm)
______________________________________________________
```
 
Expected outcome:
 
```
______________________________________________________
Nice, I've marked this task as done: 1.[(^u^)] watch movie
______________________________________________________
```
 
&nbsp;
 
 
### 4.3 `list` - Displays all tasks in list

This command displays all existing tasks in an easy-to-read format, including the task type notation `[T/E/D]`, completion status and task details.
 
Format:
 `list`
 
Example of usage:
`list`
 
Expected outcome (if task list empty):
```
______________________________________________________
list
(O_o) OOPS!The task list is empty.
```

Expected outcome (if task list non-empty):
 
```
______________________________________________________
Here are the tasks in your list: 
1.[T][(^u^)]watch movie
2.[E][('n')]Tom's birthday (at Tom's house 3pm)
3.[D][('n')]CS2113 iP submission (by 31 Dec 12pm)
______________________________________________________
```
 
&nbsp;
 
### 4.4 `find` - Find tasks that match keyword

This command displays the list of all Tasks containing your search keyword.

Format:
 `find <Keyword>`
 
- `<Keyword>` is the term that will be searched across task fields.
- Tasks with fields that contain the `<Keyword>` are displayed. 
 
 
Example of usage:
`find movie`

Assuming this is our existing list:

```
______________________________________________________
Here are the tasks in your list: 
1.[T][(^u^)]watch movie
2.[E][('n')]Tom's birthday (at Tom's house 3pm)
3.[D][('n')]CS2113 iP submission (by 31 Dec 12pm)
______________________________________________________
```
 
Expected outcome:
 
```
find movie
______________________________________________________
Here are the matching tasks in your list: 
1.[T][(^u^)]watch movie
______________________________________________________
```

Expected outcome (if no matching results):

```
______________________________________________________
find 123
(O_o) OOPS!No tasks matching the keyword were found
______________________________________________________
```
 
&nbsp;
 
### 4.5 `delete` - Deletes task from list
This command removes a specified task from the list of existing tasks.
 
Format:
 `delete <Index>`
 
- Deletes the task at the specified index.
 
 - `<Index>` refers to the index number of a specific task in the displayed task list.
  
 - The index must be a positive integer 1, 2, 3, etc.
 
Example of usage:
`delete 1`

Assuming this is our existing list:

```
______________________________________________________
Here are the tasks in your list: 
1.[T][(^u^)]watch movie
2.[E][('n')]Tom's birthday (at Tom's house 3pm)
3.[D][('n')]CS2113 iP submission (by 31 Dec 12pm)
______________________________________________________
```
 
Expected outcome:
 
```
______________________________________________________
delete 1
Got it. I've removed this task: 
[T][(^u^)]watch movie. Now you have 2 tasks in the list.
______________________________________________________
```

The resulting list:

```
______________________________________________________
Here are the tasks in your list: 
1.[E][('n')]Tom's birthday (at Tom's house 3pm)
2.[D][('n')]CS2113 iP submission (by 31 Dec 12pm)
______________________________________________________
```
 
&nbsp;
 
### 4.6 `bye` - Exits the program
Saves task list to a local storage file and exits program.
 
 
Format:
 `bye`
 
Example of usage:
`bye`
 
Expected outcome:
 
```
bye
______________________________________________________
Bye, hope to see you soon! 
______________________________________________________
``` 
&nbsp;

## 5. Command Summary 
  
 * *Todo* : `todo <Description>` \
 e.g. `todo read book`
 
 * *Event* : `event <Description> /<Remark>` \
 e.g. `event Tom's birthday /at Tom house Sat 8pm`
  
  
 * *Deadline* : `deadline <Description> /<Remark>` \
 e.g. `deadline CS2113 iP submission /31 Dec 12pm`
  
 * *Mark as Done* : `done <Index>` \
 e.g. `done 1`
  
 * *List* : `list` 
  
 * *Delete* : `delete <Index>` \
 e.g. `delete 1`
  
 * *Help* : `help`
  
 * *Exit* : `bye`
 