public interface MainPage {

    @Selector(xpath = ".//*[@test-attr='input_search']")
    String textInputSearch() throws NoSuchMethodException;

    @Selector(xpath = ".//*[@test-attr='button_search']")
    String buttonSearch() throws NoSuchMethodException;
}
