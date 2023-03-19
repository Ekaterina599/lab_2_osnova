package component.student

import react.FC
import react.Props
import react.dom.html.ReactHTML.h3
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ol
import react.dom.html.ReactHTML.span
import react.router.dom.Link
import react.useState
import ru.altmanea.webapp.common.Item
import ru.altmanea.webapp.common.ItemId
import ru.altmanea.webapp.data.Student

external interface QueryError

external interface StudentListProps : Props {
    var students: Array<Item<Student>>
    var deleteStudent: (ItemId) -> Unit
    var updateStudent: (Item<Student>) -> Unit
}

val CStudentList = FC<StudentListProps>("StudentList") { props ->
    var editedId by useState<String>("")
    h3 { +"Students" }
    ol {
        props.students.forEach { studentItem ->
            li {
                val studentClass = Student(studentItem.elem.firstname, studentItem.elem.surname, studentItem.elem.group)
                if (studentItem.id == editedId)
                    CEditStudent {
                        oldStudent = studentItem.elem
                        saveStudent = {
                            props.updateStudent(Item(it, studentItem.id))
                            editedId = ""
                        }
                    }
                else
                    Link {
                        +studentClass.shortname()
                        to = "/students/${studentItem.id}"
                    }
                +" || "
                    +studentClass.group
                /*
                Link {
                    to = "/groups/${studentClass.group}"
                }

                 */
                span {
                    +" ✂ "
                    onClick = {
                        props.deleteStudent(studentItem.id)
                    }
                }
                span {
                    +" ✎ "
                    onClick = {
                        editedId = studentItem.id
                    }
                }
            }
        }
    }
}
