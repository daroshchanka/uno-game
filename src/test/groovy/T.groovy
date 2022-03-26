import groovy.util.logging.Log4j2
import org.testng.annotations.Test

@Log4j2
class T {

    @Test
    void test() {
        log.info("Passed")
    }
}
