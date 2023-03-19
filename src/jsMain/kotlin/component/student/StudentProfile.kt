package component.student

import js.core.get
import js.core.jso
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import react.*
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.h3
import react.dom.html.ReactHTML.span
import react.router.Params
import react.router.useParams
import ru.altmanea.webapp.common.Item
import ru.altmanea.webapp.config.Config
import ru.altmanea.webapp.data.Student
import tanstack.query.core.QueryKey
import tanstack.react.query.useMutation
import tanstack.react.query.useQuery
import tanstack.react.query.useQueryClient
import tools.HTTPResult
import tools.fetch
import tools.fetchText
import kotlin.js.json

external interface StudentProfileProps : Props {
    var student: Item<Student>
    var updateStudent: (Item<Student>) -> Unit
}

val fcStudent = FC<StudentProfileProps>("Profile student") { props ->
    var editedId by useState<String>("")
    if (props.student.id == editedId)
        CEditStudentProfile {
            oldStudent = props.student.elem
            saveStudent = {
                props.updateStudent(Item(it, props.student.id))
                editedId = ""
            }
        }
    else
        CStudentInList {
            student = props.student.elem
        }
    span {
        +" ✎ "
        onClick = {
            editedId = props.student.id
        }
    }
}

val fcContainerStudentProfile = FC<Props>("QueryStudentProfile") {
    val ProfileStudentParams: Params = useParams()
    val StudentId = ProfileStudentParams["id"]
    val queryClient = useQueryClient()
    val studentListQueryKey = arrayOf("studentProfileList").unsafeCast<QueryKey>()


    val query = useQuery<String, QueryError, String, QueryKey>(
        queryKey = studentListQueryKey,
        queryFn = { fetchText("${Config.studentsPath}${StudentId}" ) }
    )

    val updateStudentMutation = useMutation<HTTPResult, Any, Item<Student>, Any>(
        mutationFn = { studentItem: Item<Student> ->
            fetch(
                "${Config.studentsPath}${studentItem.id}",
                jso {
                    method = "PUT"
                    headers = json("Content-Type" to "application/json")
                    body = Json.encodeToString(studentItem.elem)
                }
            )
        },
        options = jso {
            onSuccess = { _: Any, _: Any, _: Any? ->
                queryClient.invalidateQueries<Any>(studentListQueryKey)
            }
        }
    )

    if (query.isLoading) ReactHTML.div { +"Loading .." }
    else if (query.isError) ReactHTML.div { +"Error!" }
    else {
        val studentProfile = Json.decodeFromString<Item<Student>>(query.data ?: "")
        fcStudent {
            student = studentProfile
            updateStudent = {
                updateStudentMutation.mutateAsync(it, null)
            }
        }
    }
}



