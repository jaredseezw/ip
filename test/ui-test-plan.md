# UI Test Plan

This plan is the source of truth for fail-fast console UI testing. Each test case runs in a fresh application process, and its expected output is compared exactly with stdout.

## Configuration

- Source directory: `src/main/java`
- Classes directory: `out/production/ip`
- Main class: `Goat`

## Test case: Exit immediately

### Aim

Verify that the application displays its greeting and exits cleanly when the user enters `bye`.

### Commands

```text
bye
```

### Expected output

```text
____________________________________________________________
  ____             _
 / ___| ___   __ _| |_
| |  _ / _ \ / _` | __|
| |_| | (_) | (_| | |_
 \____|\___/ \__,_|\__|
Hello! I'm Goat.
What can I do for you?
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case: Manage todos, deadlines, and events

### Aim

Verify all three task types, string-based date and time values, polymorphic listing, and inherited completion status.

### Commands

```text
todo borrow book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
deadline do homework /by no idea :-p
mark 2
list
unmark 2
list
bye
```

### Expected output

```text
____________________________________________________________
  ____             _
 / ___| ___   __ _| |_
| |  _ / _ \ / _` | __|
| |_| | (_) | (_| | |_
 \____|\___/ \__,_|\__|
Hello! I'm Goat.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] borrow book
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [D][ ] return book (by: Sunday)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [D][ ] do homework (by: no idea :-p)
Now you have 4 tasks in the list.
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [D][X] return book (by: Sunday)
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] borrow book
2.[D][X] return book (by: Sunday)
3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
4.[D][ ] do homework (by: no idea :-p)
____________________________________________________________
____________________________________________________________
OK, I've marked this task as not done yet:
  [D][ ] return book (by: Sunday)
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] borrow book
2.[D][ ] return book (by: Sunday)
3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
4.[D][ ] do homework (by: no idea :-p)
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case: Recover from incorrect inputs

### Aim

Verify that empty and unknown commands, incomplete task details, and invalid task numbers produce specific guidance without terminating the application or corrupting its task list.

### Commands

```text

mark 1
todo
todo read book
deadline submit assignment
deadline /by Friday
deadline submit assignment /by
deadline submit assignment /by Friday
event meeting
event /from Monday /to Tuesday
event meeting /from /to Tuesday
event meeting /from Monday /to
event meeting /from Monday /to Tuesday
mark
mark two
mark 0
mark 4
mark 1
unmark 1
blah
list
bye
```

### Expected output

```text
____________________________________________________________
  ____             _
 / ___| ___   __ _| |_
| |  _ / _ \ / _` | __|
| |_| | (_) | (_| | |_
 \____|\___/ \__,_|\__|
Hello! I'm Goat.
What can I do for you?
____________________________________________________________
____________________________________________________________
OOPS!!! Please enter a command.
____________________________________________________________
____________________________________________________________
OOPS!!! There are no tasks in the list yet.
____________________________________________________________
____________________________________________________________
OOPS!!! A todo needs a description. Try: todo <description>
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
OOPS!!! Use this format: deadline <description> /by <date or time>
____________________________________________________________
____________________________________________________________
OOPS!!! A deadline needs a description before /by.
____________________________________________________________
____________________________________________________________
OOPS!!! A deadline needs a date or time after /by.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [D][ ] submit assignment (by: Friday)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
OOPS!!! Use this format: event <description> /from <start> /to <end>
____________________________________________________________
____________________________________________________________
OOPS!!! An event needs a description before /from.
____________________________________________________________
____________________________________________________________
OOPS!!! An event needs a start time after /from.
____________________________________________________________
____________________________________________________________
OOPS!!! An event needs an end time after /to.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [E][ ] meeting (from: Monday to: Tuesday)
Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
OOPS!!! Please specify a task number. Try: mark <task number>
____________________________________________________________
____________________________________________________________
OOPS!!! The task number must be a positive whole number.
____________________________________________________________
____________________________________________________________
OOPS!!! Task 0 does not exist. Choose a number from 1 to 3.
____________________________________________________________
____________________________________________________________
OOPS!!! Task 4 does not exist. Choose a number from 1 to 3.
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [T][X] read book
____________________________________________________________
____________________________________________________________
OK, I've marked this task as not done yet:
  [T][ ] read book
____________________________________________________________
____________________________________________________________
OOPS!!! I don't recognise that command. Try todo, deadline, event, list, mark, unmark, or bye.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read book
2.[D][ ] submit assignment (by: Friday)
3.[E][ ] meeting (from: Monday to: Tuesday)
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```
