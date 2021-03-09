import java.util.Scanner;
import java.util.ArrayList;

public class BigNumberCalculator 
{
    
    private static String cmd, firstWord;
    private static String [] splitted;
    private static Scanner input;
    
	public static void main(String[] args) 
	{
		
	    input = new Scanner(System.in);
		getCommand();

		ArrayList<BigNumber> numbers;
		BigNumber result;

		while (!firstWord.equals("Q")) 
		{
			if (firstWord.equals("Q")) continue;
			if (firstWord.equals("min") || firstWord.equals("max") || firstWord.equals("add") || firstWord.equals("sub") || firstWord.equals("mul") || firstWord.equals("div") || firstWord.equals("mod") || firstWord.equals("help"))
			{
                    if (!firstWord.equals("mod") && !firstWord.equals("help") && !firstWord.equals("div")) 
                    {
                        if (splitted.length < 3) 
                        {
                            System.out.println("Not enough argument!");
                            getCommand();
                            continue;                        
                        }
                        numbers = new ArrayList<BigNumber>();
                        result = new BigNumber(splitted[1]);

                        switch(firstWord) 
                        {
                            case "min":
                                for (int i = 2; i < splitted.length; i++) 
					                result = result.Minimum(new BigNumber(splitted[i]));
                                break;
                            case "max":
                                for (int i = 2; i < splitted.length; i++)
                                    result = result.Maximum(new BigNumber(splitted[i]));
                                break;
                            case "add":
                                for (int i = 2; i < splitted.length; i++)
                                    result = result.Add(new BigNumber(splitted[i]));
                                break;
                            case "sub":
                                for (int i = 2; i < splitted.length; i++)
					                result = result.Subtract(new BigNumber(splitted[i]));
					            break;
					        case "mul":
					            for (int i = 2; i < splitted.length; i++)
					                result = result.Mul(new BigNumber(splitted[i]));
					            break;
                        } 
                        System.out.println(result);
                    }
                    else 
                    { 
                        if (!firstWord.equals("help") && splitted.length != 3) 
                        {
                            System.out.println("Number of arguments is wrong!");
                            
                        }
                        
                        else
                        {
					        if (firstWord.equals("div") && splitted.length == 3)
					        {
					            BigNumber numX = new BigNumber(splitted[1]);
					            BigNumber numY = new BigNumber(splitted[2]);
						        BigNumber[] resultList = numX.Div(numY);
						        System.out.println("Result: " + resultList[0] + " remainder: " + resultList[1]);
					        }
					        
					        else if (firstWord.equals("mod"))
					        {
					            BigNumber numX = new BigNumber(splitted[1]);
					            BigNumber numY = new BigNumber(splitted[2]);
						        System.out.println(numX.Mod(numY));
					        }
					        
					        else if (firstWord.equals("help") && splitted.length == 1)
					        {
					            printHelp();    
					        }
					        else
					        {
					            System.out.println("Invalid input format! Possible commands are as follows:");
					            printHelp();
					        }
					    }
					}
			}
			else
			{
				System.out.println("Unknown command, please enter a correct command.\nPossible commands are as follows:");
				printHelp();
			}
			getCommand();
		}
		input.close();
	}
	
	public static void getCommand() 
	{
	    System.out.print("Enter a command: ");
	    cmd = input.nextLine();
	    splitted = cmd.split(" ");
	    firstWord = splitted[0];
	}
	
    public static void printHelp() 
    {
        String text = "min num1 num2 num3 num4 ... : prints the minimum of the given numbers\n";
        text += "max num1 num2 num3 num4 ... : prints the maximum of the given numbers\n";
        text += "add num1 num2 num3 num4 ... : prints the summation of the given numbers\n";
        text += "sub num1 num2 num3 num4 ... : prints the result of the operation num1 - num2 - num3 - num4 - ...\n";
        text += "mul num1 num2 num3 num4 ... : prints the product of the given numbers\n";
        text += "div num1 num2 : prints the result of the operation num1/num2 along with the remainder\n";
        text += "mod num1 num2 : prints the result of the operation num1 % num2\n";
        text += "help: prints this output\n";
        text += "Q: quits the program";
        System.out.println(text);
    }	

}
