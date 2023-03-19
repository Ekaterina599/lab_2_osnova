package component.student

import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.span
import react.useRef
import ru.altmanea.webapp.data.Student
import web.html.HTMLInputElement

external interface EditStudentProps : Props {
    var oldStudent: Student
    var saveStudent: (Student) -> Unit
}

val CEditStudent = FC<EditStudentProps>("Edit student") { props ->
    val firstnameRef = useRef<HTMLInputElement>()
    val surnameRef = useRef<HTMLInputElement>()
    val groupRef = useRef<HTMLInputElement>()
    span {
        input {
            defaultValue = props.oldStudent.firstname
            ref = firstnameRef
        }
        input {
            defaultValue = props.oldStudent.surname
            ref = surnameRef
        }
        input {
            defaultValue = props.oldStudent.group
            ref = groupRef
        }
    }
    ReactHTML.button {
        +"✓"
        onClick = {
            firstnameRef.current?.value?.let { firstname ->
                surnameRef.current?.value?.let { surname ->
                    groupRef.current?.value?.let { group ->
                        props.saveStudent(Student(firstname, surname, group))
                    }
                }
            }
        }
    }
}
