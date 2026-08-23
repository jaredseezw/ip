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
