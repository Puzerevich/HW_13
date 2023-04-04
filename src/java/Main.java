import java.io.File;
import java.io.IOException;
public class Main {

    public static void main(String[] args) {
        FileNavigator fileNavigator = new FileNavigator();

        fileNavigator.add("Storage\\Files\\0\\F_1.txt");
        fileNavigator.add("Storage\\Files\\1\\text2.txt");

        System.out.println(fileNavigator.find("Storage\\Files"));
        System.out.println(fileNavigator.filterBySize(14340));
        System.out.println(fileNavigator.sortBySize());
        fileNavigator.remove("Storage\\Files");
        System.out.println(fileNavigator.find("Storage\\Files"));
    }
}
