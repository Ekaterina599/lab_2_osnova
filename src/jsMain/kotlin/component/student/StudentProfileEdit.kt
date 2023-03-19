package component.student

import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.span
import react.useRef
import ru.altmanea.webapp.data.Student
import web.html.HTMLInputElement

external interface EditStudentProfileProps : Props {
    var oldStudent: Student
    var saveStudent: (Student) -> Unit
}

val CEditStudentProfile = FC<EditStudentProfileProps>("Edit profile student") { props ->
    val groupRef = useRef<HTMLInputElement>()
    span {
        input {
            defaultValue = props.oldStudent.group
            ref = groupRef
        }
    }
    ReactHTML.button {
        +"✓"
        onClick = {
            groupRef.current?.value?.let { group ->
                props.saveStudent(Student(props.oldStudent.firstname, props.oldStudent.surname, group))
            }
        }
    }
}