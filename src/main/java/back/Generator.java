package back;

public class Generator {
    private static Generator generator;

    private Generator(){

    }

    public static Generator getInstance() {
        if(generator == null)
            generator = new Generator();
        return generator;
    }
}
