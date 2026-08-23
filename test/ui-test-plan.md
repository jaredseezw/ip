# UI Test Plan

This plan is the source of truth for fail-fast console UI testing. Each test case runs in a fresh application process, and its expected output is compared exactly with stdout.

## Configuration

- Source directory: `src/main/java`
- Classes directory: `out/production/ip`
- Main class: `Goat`

## Test case: Greet and exit

### Aim

Verify that Level 1 displays the Goat greeting and exits cleanly when the user enters `bye`.

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

## Test case: Echo input until exit

### Aim

Verify that Level 2 repeats each line entered by the user and continues until `bye`.

### Commands

```text
hello Goat
this is another line
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
hello Goat
____________________________________________________________
____________________________________________________________
this is another line
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case: Add and list task types

### Aim

Verify that Level 3 adds todos, deadlines, and events and lists them in insertion order.

### Commands

```text
todo borrow book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
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
Here are the tasks in your list:
1.[T][ ] borrow book
2.[D][ ] return book (by: Sunday)
3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case: Mark and unmark tasks

### Aim

Verify that Level 4 changes task completion state and displays it consistently in the list.

### Commands

```text
todo borrow book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
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
Nice! I've marked this task as done:
  [D][X] return book (by: Sunday)
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] borrow book
2.[D][X] return book (by: Sunday)
3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
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
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```
