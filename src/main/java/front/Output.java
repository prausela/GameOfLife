package front;

public class Output {
    private static Output output;

    private Output(){

    }

    public static Output getInstance() {
        if(output == null)
            output = new Output();
        return output;
    }
    
    void outputToConsole()
    {
    	System.out.println("Implement me!");
    }
    
    void outputToFile(String file)
    {
    	
    }
}
