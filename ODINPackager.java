import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

class ODINPackager {
    public static void main(String args[]) {
        // puts text into console
        System.out.print("ODIN Package Tool By AndroidFire \n");
        // constructing scanner to get instance
        Scanner sc = new Scanner(System.in);
        // puts text into console
        System.out.println("Enter the file path you want to make odin flash-able \n");
        // getting text from scanner
        String input = sc.next();
        // it just show it does not did any work just for show up
        System.out.println("Processing the file ...");
        System.out.println("It may take 10 second ...");
        System.out.println("...");
        System.out.println("......");
        System.out.println("............");
        // constructing file
        File file = new File(input);
        // closing scanner
        sc.close();
        // check if file exist
        if (file.exists()) {
            // did some work
            System.out.println("Congratulation the file exist\n");
            if (file.getName().toLowerCase().endsWith(".img")) {
                System.out.println("Congratulation the file's extension is img");

                String without_ex = stripExtension(input);
                //tar -H ustar -c recovery.img > recovery.tar
                String aa = "tar -H ustar -c ";
                String bb = "md5sum -t ";
                String cc = "mv ";
                try {

                    String aaa = aa + input + " > " + without_ex + ".tar";

                    String bbb = bb + without_ex + ".tar" + " >> " + without_ex + ".tar";
                    String ccc = cc + without_ex + ".tar" + " " + without_ex + ".tar.md5";
                    commandIt(aaa);
                    commandIt(bbb);
                    commandIt(ccc);


                    System.out.println("Operation Done");
                } catch (              //md5sum -t recovery.tar >> recovery.tar

                        Exception e) {
                    System.out.println("Could not make do the task error are below \n");
                    e.printStackTrace();


                }


            } else {
                System.out.println("Sorry your file's extension is not matched to img");
            }
        } else if (!file.exists()) {

            System.out.println("Sorry file not exist\n");

            System.out.println("Please remove '' from start and ending if have \n ");

        }
    }
    static void commandIt(String cmdline) {
        ArrayList<String> output = command(cmdline, ".");
        if (null == output)
            System.out.println("\n\n\t\tCOMMAND FAILED: " + cmdline);
        else
            for (String line : output)
                System.out.println(line);

    }
     static ArrayList<String> command(final String cmdline,
                                            final String directory) {
        try {
            Process process =
                    new ProcessBuilder("bash", "-c", cmdline)
                            .redirectErrorStream(true)
                            .directory(new File(directory))
                            .start();

            ArrayList<String> output = new ArrayList<>();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            while ( (line = br.readLine()) != null )
                output.add(line);

            //There should really be a timeout here.
            if (0 != process.waitFor())
                return null;

            return output;

        } catch (Exception e) {
            //Warning: doing this is no good in high quality applications.
            //Instead, present appropriate error messages to the user.
            //But it's perfectly fine for prototyping.

            return null;
        }
    }
    static String stripExtension (String str) {
        // Handle null case specially.

        if (str == null) return null;

        // Get position of last '.'.

        int pos = str.lastIndexOf(".");

        // If there wasn't any '.' just return the string as is.

        if (pos == -1) return str;

        // Otherwise return the string, up to the dot.

        return str.substring(0, pos);
    }




}
