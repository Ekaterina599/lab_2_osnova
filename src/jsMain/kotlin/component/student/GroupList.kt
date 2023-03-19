package component
import component.student.QueryError
import ru.altmanea.webapp.data.Student
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h3
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ol
import react.router.dom.Link
import ru.altmanea.webapp.common.Item
import ru.altmanea.webapp.config.Config
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText


external interface GroupListProps : Props {
    var groups: Array<Item<Student>>
}

val fcGroupList = FC<GroupListProps>("GroupItem") { props ->
    h3 { +"Группы" }
    ol {
        props.groups.map {
            li {
                    +it.elem.group
            }
        }
    }
}

val fcContainerGroupList = FC<Props>("QueryGroupList") {
    val query = useQuery<String, QueryError, String, QueryKey>(
        queryKey = arrayOf("groupList").unsafeCast<QueryKey>(),
        queryFn = { fetchText(Config.groupsAllPath) }
    )

    if (query.isLoading) ReactHTML.div { +"Loading .." }
    else if (query.isError) ReactHTML.div { +"Error!" }
    else {
        val group = Json.decodeFromString<Array<Item<Student>>>(query.data ?: "")
        fcGroupList { groups = group }
    }
}


