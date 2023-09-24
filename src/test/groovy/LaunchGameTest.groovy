import groovy.util.logging.Log4j2
import org.testng.annotations.AfterClass
import org.testng.annotations.Test

@Log4j2
class LaunchGameTest {

  Process process

  @AfterClass(alwaysRun = true)
  void tearDown() {
    try {
      process.waitForOrKill(1)
    } catch (Throwable ignored) {
    }
  }

  @Test
  void test() {
    File input = new File('target/user-input.txt').tap { createNewFile() }.tap { text = 'Uno Tester' }
    File output = new File('target/process-output.log')
    process = new ProcessBuilder('java', '-jar', 'uno-1.0-SNAPSHOT-jar-with-dependencies.jar').tap {
      directory(new File('target'))
      redirectOutput(output)
      redirectErrorStream(true)
      redirectInput(input)
    }.start().tap { waitForOrKill(1500) }
    output.text.toString().with {
      assert contains('ENTER YOUR NAME')
      assert contains('ENTER PLAYERS COUNT.. [2..10]')
      assert contains('Please, enter some number between 2 and 10')
      log.info(it)
    }
  }

}
