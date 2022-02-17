import java.io.*;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ReadOptions {
    private HashMap<String, String> options = new HashMap<>();

    ReadOptions() {
        File file = new File("src/options");
        Scanner scanner;
        String line;
        String x;
        String y;
        try {
            scanner = new Scanner(file);

            while (scanner.hasNextLine())
            {
                line = scanner.nextLine();
                x = line.substring(0, (line.indexOf('=')));
                y = line.substring(x.length()+1);
                options.put(x, y);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchElementException e)
        {
            System.out.println("No line found!");
        }
    }

    public String getOption(String key)
    {
        return options.get(key);
    }
}
