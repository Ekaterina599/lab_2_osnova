package ru.altmanea.webapp.rest

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*
import ru.altmanea.webapp.config.Config
import ru.altmanea.webapp.data.Student
import ru.altmanea.webapp.repo.studentsRepo

fun Route.groupRoutes() {
    route(Config.groupsAllPath) {
        get {
            val students = studentsRepo.read()
            if (students.isEmpty()) {
                call.respondText("No students found", status = HttpStatusCode.NotFound)
            } else {
                call.respond(students)
            }
        }
    }
}
