import org.junit.*;
import org.fluentlenium.adapter.FluentTest;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import org.junit.ClassRule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
      return webDriver;
  }
  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void categoryIsCreatedTest() {
    goTo("http://localhost:4567/");
    click("a",withText("Add or view a category"));
    fill("#name").with("Household chores");
    submit(".categorySubmit");
    assertThat(pageSource()).contains("Household chores");
  }

  @Test
  public void taskIsCreatedTest() {
    goTo("http://localhost:4567/");
    click("a",withText("Add or view a task"));
    fill("#description").with("Change the sheets");
    submit("#addTask");
    assertThat(pageSource()).contains("Change the sheets");
  }

  @Test
  public void taskIsMarkedComplete() {
    Task testTask = new Task("Vacuum");
    testTask.save();
    goTo("http://localhost:4567/tasks");
    String test = "#" + testTask.getId();
    click(test);
    assertThat(pageSource()).contains("Vacuum");
  }
  //
  // @Test
  // public void taskIsCreated() {
  //   Category myCategory = new Category("Household chores");
  //   myCategory.save();
  //   String categoryPath = String.format("http://localhost:4567/categories/%d", myCategory.getId());
  //   Task newTask = new Task("Laundry", myCategory.getId());
  //   newTask.save();
  //   goTo(categoryPath);
  //   assertThat(pageSource()).contains("Laundry");
  // }
}
