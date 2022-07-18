import com.codeborne.selenide.Selenide;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class ParamsTest {

    @ValueSource(strings = {"Java", "Python"})
    @ParameterizedTest(name = "При поиске по языку программирования {0} в результатах отображаются книги {0}")
    void labyrinthTest(String setLanguage) {
        Selenide.open("https://www.labirint.ru");
        Selenide.$("#search-field").setValue(setLanguage);
        Selenide.$(".b-header-b-search-e-btn").pressEnter();
        Selenide.$$(".product-title").find(text(setLanguage)).shouldBe(visible);
    }

    @CsvSource(value = {
            "Java, Java Persistence API",
            "C++, C++17 STL"
    })
    @ParameterizedTest(name = "При поиске по языку программирования {0} в результатах отображаются книги {1}")
    void labyrinthTestCSV(String setLanguage, String expectedResult) {
        Selenide.open("https://www.labirint.ru");
        Selenide.$("#search-field").setValue(setLanguage);
        Selenide.$(".b-header-b-search-e-btn").pressEnter();
        Selenide.$$(".product-title").find(text(expectedResult)).shouldBe(visible);
    }

    static Stream<Arguments> labyrinthTestComplexData() {
        return Stream.of(
                Arguments.of("Java", "Java Persistence API"),
                Arguments.of("C++", "C++17 STL")
        );
    }

    @MethodSource(value = "labyrinthTestComplexData")
    @ParameterizedTest(name = "При поиске по языку программирования {0} в результатах отображаются книги {1}")
    void labyrinthTestComplex(String setLanguage, String expectedResult) {
        Selenide.open("https://www.labirint.ru");
        Selenide.$("#search-field").setValue(setLanguage);
        Selenide.$(".b-header-b-search-e-btn").pressEnter();
        Selenide.$$(".product-title").find(text(expectedResult)).shouldBe(visible);
    }

    @EnumSource(Language.class)
    @ParameterizedTest
    void enumTest(Language language) {
        Selenide.open("https://www.labirint.ru");
        $("#search-field").setValue(language.desc);
        $(".b-header-b-search-e-btn").click();
        $$(".product-title").findBy(text(language.desc)).shouldBe(visible);
    }

    public String toString() {
        return super.toString();
    }
}