// akka specific
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
// akka http specific
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives.*
// spray specific (JSON marshalling)
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport.*
import spray.json.DefaultJsonProtocol.*
// cors
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.*

final case class User (id: Long, name: String, email: String)

@main def userSerice: Unit = 

  implicit val actorSystem = ActorSystem(Behaviors.empty, "akka-http")
  implicit val userMarshaller: spray.json.RootJsonFormat[User] = jsonFormat3(User.apply)

  val getUser = get {
      concat(
        path("hello") {
          complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Hello world from scala akka http server!"))
        },
        path("user" / LongNumber) {
          userid => {
            println("get user by id")
              userid match {
                case 1 => complete(User(userid, "Testuser", "test@test.com"))
                case _ => complete(StatusCodes.NotFound)
              }
          }
        }
      )
  }
  
  val createUser = post {
    path("user") {
      entity(as[User]) {
        user => {
          println("save user")
          complete(User(user.id, "Testuser", "test@test.com"))
        }
      }
    }
  }

  val updateUser = put {
    path("user") {
      entity (as[User]) {
        user => {
          println("update user")
          complete(User(user.id, "Testuser", "test@test.com"))
        }
      }
    }
  }

  val deleteUser = delete {
    path ("user" / LongNumber) {
      userid => {
        println(s"user ${userid}")
        complete(User(userid, "Testuser", "test@test.com"))
      }
    }
  }

  val route = cors() {
    concat(getUser, createUser, updateUser, deleteUser)
  }

  val bindFuture = Http().newServerAt("127.0.0.1", 8080).bind(route)

