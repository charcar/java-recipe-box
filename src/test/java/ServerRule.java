import org.junit.rules.ExternalResource;
import spark.Spark;
import java.util.List;

public class ServerRule extends ExternalResource {

  protected void before() {
      String[] args = {};
      App.main(args);
   }

  protected void after() {
      Spark.stop();
  }
}
