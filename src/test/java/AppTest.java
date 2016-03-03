import org.junit.*;
import static org.junit.Assert.*;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("recipe maker and searching");
  }

  @Test
  public void ingredientsPageDisplaysNothingAtFirst() {
    goTo("http://localhost:4567/ingredients");
    assertThat(pageSource()).contains("Looks like you don't have any ingredients yet!");
  }

  @Test
  public void ingredientsPageListsIngredients() {
    Ingredient newGreedy = new Ingredient("Apple");
    newGreedy.save();
    goTo("http://localhost:4567/ingredients");
    assertThat(pageSource()).contains("Apple");
  }

  @Test
  public void addedIngredientsAppearOnPage() {
    goTo("http://localhost:4567/ingredients");
    fill("#name").with("Cherry");
    submit(".btn-success");
    assertThat(pageSource()).contains("Cherry");
  }

}
