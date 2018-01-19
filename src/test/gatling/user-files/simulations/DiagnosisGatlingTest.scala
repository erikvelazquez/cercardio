import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the Diagnosis entity.
 */
class DiagnosisGatlingTest extends Simulation {

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

    val scn = scenario("Test the Diagnosis entity")
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
            exec(http("Get all diagnoses")
            .get("/api/diagnoses")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new diagnosis")
            .post("/api/diagnoses")
            .headers(headers_http_authenticated)
            .body(StringBody("""{"id":null, "letra":"SAMPLE_TEXT", "catalogkey":"SAMPLE_TEXT", "asterisco":"SAMPLE_TEXT", "nombre":"SAMPLE_TEXT", "lsex":"SAMPLE_TEXT", "linf":"SAMPLE_TEXT", "lsup":"SAMPLE_TEXT", "trivial":"SAMPLE_TEXT", "erradicado":"SAMPLE_TEXT", "ninter":"SAMPLE_TEXT", "nin":"SAMPLE_TEXT", "ninmtobs":"SAMPLE_TEXT", "nocbd":"SAMPLE_TEXT", "noaph":"SAMPLE_TEXT", "fetal":"SAMPLE_TEXT", "capitulo":"0", "lista1":"0", "grupo1":"0", "lista5":"0", "actualizacionescie10":"SAMPLE_TEXT", "yearmodifi":"SAMPLE_TEXT", "yearaplicacion":"SAMPLE_TEXT", "valid":"SAMPLE_TEXT", "prinmorta":"0", "prinmorbi":"0", "lmmorbi":"SAMPLE_TEXT", "lmmorta":"SAMPLE_TEXT", "lgbd165":"SAMPLE_TEXT", "iomsbeck":"0", "lgbd190":"SAMPLE_TEXT", "notdiaria":"SAMPLE_TEXT", "notsemanal":"SAMPLE_TEXT", "sistemaespecial":"SAMPLE_TEXT", "birmm":"SAMPLE_TEXT", "causatype":"0", "epimorta":"SAMPLE_TEXT", "edasiras5Anios":"SAMPLE_TEXT", "csvematernasseedepid":"SAMPLE_TEXT", "epimortam5":"SAMPLE_TEXT", "epimorbi":"SAMPLE_TEXT", "defmaternas":"0", "escauses":"SAMPLE_TEXT", "numcauses":"SAMPLE_TEXT", "essuivemorta":"SAMPLE_TEXT", "essuivemorb":"SAMPLE_TEXT", "epiclave":"SAMPLE_TEXT", "epiclavedesc":"SAMPLE_TEXT", "essuivenotin":"SAMPLE_TEXT", "essuiveestepi":"SAMPLE_TEXT", "essuiveestbrote":"SAMPLE_TEXT", "sinac":"SAMPLE_TEXT", "daga":"SAMPLE_TEXT", "manifestaenfer":"SAMPLE_TEXT"}""")).asJSON
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_diagnosis_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created diagnosis")
                .get("${new_diagnosis_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created diagnosis")
            .delete("${new_diagnosis_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(Integer.getInteger("users", 100)) over (Integer.getInteger("ramp", 1) minutes))
    ).protocols(httpConf)
}
