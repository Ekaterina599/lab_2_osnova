
import component.fcContainerGroupList
import component.student.studentContainer
import component.student.fcContainerStudentProfile
import csstype.*
import emotion.react.css
import react.FC
import react.Props
import react.create
import react.dom.client.createRoot
import react.router.Route
import react.router.Routes
import react.router.dom.HashRouter
import react.router.dom.Link
import tanstack.query.core.QueryClient
import tanstack.react.query.QueryClientProvider
import web.dom.document

fun main() {
    val container = document.getElementById("root")!!
    createRoot(container).render(app.create())
}

val app = FC<Props> ("App") {
    HashRouter {
        QueryClientProvider {
            client = QueryClient()
            Link {
                css{
                    background = NamedColor.lightpink
                    padding = Padding(vertical = 10.px, horizontal = 10.px)
                    border = Border(width = 2.px, style = LineStyle.solid)
                }
                +"Cтуденты"
                to = "/students"
            }
            Link {
                css{
                    background = NamedColor.aqua
                    padding = Padding(vertical = 10.px, horizontal = 10.px)
                    border = Border(width = 2.px, style = LineStyle.solid)
                }
                +"Группы"
                to = "/groupsAll"
            }
            Routes {
                Route {
                    path = "/groupsAll"
                    element = fcContainerGroupList.create()

                }
                Route {
                    path = "/students"
                    element = studentContainer.create()

                }
                Route {
                    path = "/students/:id"
                    element = fcContainerStudentProfile.create()
                }
            }
        }
    }
}