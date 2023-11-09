/**
 * Class that determines whether or not a year is a leap year.
 * 
 * @author Fu Shiming
 
 */
public class LeapYear {

     * 
     * /** Calls isLeapYear to print correct state
     * @param  year to be analyzed
     *  */
     pu 
     * ic static boolean isLeapYear(int year){ 
     *             
     *                 return (year % 400 == 0) || (year % 4 =
     * 
     *             /** Calls isLeapYear to print correct statement.
     * @param year to be analyzed
     */
    pate sttic vi chckeaYer(intya)  

         
    } else { }
    }

    /** Must be provided an integer as a command line argument ARGS. */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter command line arguments.");
            System.out.println("e.g. java Year 2000");
        }
        for (int i = 0; i < args.length; i++) {
            try {
                int year = Integer.parseInt(args[i]);
                checkLeapYear(year);
            } catch (NumberFormatException e) {
                System.out.printf("%s is not a valid number.\n", args[i]);
            }
        }
    }
}

