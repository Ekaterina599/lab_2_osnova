package ru.altmanea.webapp.repo

import ru.altmanea.webapp.data.Student

val studentsRepo = ListRepo<Student>()


fun createTestData() {
    listOf(
        Student("Sheldon", "Cooper", "20a"),
        Student("Leonard", "Hofstadter", "20b"),
        Student("Howard", "Wolowitz", "20c"),
        Student("Penny", "Hofstadter", "20d"),
    ).apply {
        map {
            studentsRepo.create(it)
        }
    }
}
