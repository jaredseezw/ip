#!/usr/bin/env python3
"""Integration test for Goat's write-on-change task persistence."""

from pathlib import Path
import subprocess
import tempfile
import unittest


REPOSITORY = Path(__file__).parent.parent.resolve()
SOURCES = sorted((REPOSITORY / "src/main/java").glob("*.java"))


class StorageIntegrationTest(unittest.TestCase):
    """Checks the data file produced by a complete console session."""

    def test_latest_task_state_is_saved_after_every_kind_of_change(self) -> None:
        with tempfile.TemporaryDirectory() as temporary_directory:
            working_directory = Path(temporary_directory)
            classes_directory = working_directory / "classes"
            classes_directory.mkdir()

            subprocess.run(
                ["javac", "-d", str(classes_directory), *map(str, SOURCES)],
                check=True,
            )
            subprocess.run(
                ["java", "-cp", str(classes_directory), "Goat"],
                cwd=working_directory,
                input=(
                    "todo read book\n"
                    "deadline return book /by June 6th\n"
                    "event project meeting /from Aug 6th 2pm /to 4pm\n"
                    "mark 1\n"
                    "delete 2\n"
                    "bye\n"
                ),
                text=True,
                capture_output=True,
                check=True,
            )

            saved_data = (working_directory / "data/goat.txt").read_text(encoding="utf-8")
            self.assertEqual(
                saved_data,
                "T | 1 | read book\n"
                "E | 0 | project meeting | Aug 6th 2pm | 4pm\n",
            )


if __name__ == "__main__":
    unittest.main()
