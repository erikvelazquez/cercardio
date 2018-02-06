import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the Medic_Information entity.
 */
class Medic_InformationGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://localhost:8080"""

    val httpConf = http
        .baseURL(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connectionHeader("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")

    val headers_http = Map(
        "Accept" -> """application/json"""
    )

    val headers_http_authentication = Map(
        "Content-Type" -> """application/json""",
        "Accept" -> """application/json"""
    )

    val headers_http_authenticated = Map(
        "Accept" -> """application/json""",
        "Authorization" -> "${access_token}"
    )

    val scn = scenario("Test the Medic_Information entity")
        .exec(http("First unauthenticated request")
        .get("/api/account")
        .headers(headers_http)
        .check(status.is(401))).exitHereIfFailed
        .pause(10)
        .exec(http("Authentication")
        .post("/api/authenticate")
        .headers(headers_http_authentication)
        .body(StringBody("""{"username":"admin", "password":"admin"}""")).asJSON
        .check(header.get("Authorization").saveAs("access_token"))).exitHereIfFailed
        .pause(1)
        .exec(http("Authenticated request")
        .get("/api/account")
        .headers(headers_http_authenticated)
        .check(status.is(200)))
        .pause(10)
        .repeat(2) {
            exec(http("Get all medic_Informations")
            .get("/api/medic-informations")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new medic_Information")
            .post("/api/medic-informations")
            .headers(headers_http_authenticated)
            .body(StringBody("""{"id":null, "cp":"0", "street":"SAMPLE_TEXT", "extnumber":"SAMPLE_TEXT", "intnumber":"SAMPLE_TEXT", "phone1":"SAMPLE_TEXT", "phone2":"SAMPLE_TEXT", "email1":"SAMPLE_TEXT", "email2":"SAMPLE_TEXT", "facebook":"SAMPLE_TEXT", "twitter":"SAMPLE_TEXT", "instagram":"SAMPLE_TEXT", "snapchat":"SAMPLE_TEXT", "linkedin":"SAMPLE_TEXT", "vine":"SAMPLE_TEXT", "recomended":"SAMPLE_TEXT", "cvuconacyt":"SAMPLE_TEXT", "cedula":"SAMPLE_TEXT", "cedulaesp":"SAMPLE_TEXT", "curp":"SAMPLE_TEXT", "rfc":"SAMPLE_TEXT", "birthday":"2020-01-01T00:00:00.000Z", "externalhospitals":"SAMPLE_TEXT", "subid":"0", "highid":"0", "medicalinsurance":"0", "socialinsurance":"0", "responsibilityinsurance":"SAMPLE_TEXT", "sINconacyt":"SAMPLE_TEXT", "medicalinnumber":"SAMPLE_TEXT", "socialinnumber":"SAMPLE_TEXT", "responsibilityinnumber":"SAMPLE_TEXT"}""")).asJSON
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_medic_Information_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created medic_Information")
                .get("${new_medic_Information_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created medic_Information")
            .delete("${new_medic_Information_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(Integer.getInteger("users", 100)) over (Integer.getInteger("ramp", 1) minutes))
    ).protocols(httpConf)
}
