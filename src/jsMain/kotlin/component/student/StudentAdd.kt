package component.student

import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h4
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.useRef
import ru.altmanea.webapp.data.Student
import web.html.HTMLInputElement

external interface AddStudentProps: Props {
    var addStudent: (Student) -> Unit
}

val CAddStudent = FC<AddStudentProps>("AddStudent") { props ->
    val firstnameRef = useRef<HTMLInputElement>()
    val surnameRef = useRef<HTMLInputElement>()
    val groupRef = useRef<HTMLInputElement>()
    h4 { +"Add student:" }
    div {
        div {
            label { +"firstname " }
            input { ref = firstnameRef }
        }
        div {
            label { +"surname " }
            input { ref = surnameRef }
        }
        div {
            label { +"group " }
            input { ref = groupRef }
        }
        button {
            +"Add"
            onClick = {
                firstnameRef.current?.value?.let { firstname ->
                    surnameRef.current?.value?.let { surname ->
                        groupRef.current?.value?.let { group ->
                            props.addStudent(Student(firstname, surname, group))
                        }
                    }
                }
            }
        }
    }
}