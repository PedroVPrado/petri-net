import logic.Petrinet;
 
public class PetriGenerator {
 
    public static void main(String[] args) {
		if (args.length != 2) {
            System.out.println("Favor inserir argumentos <filename> <cost>");
            return;
        }

        String filename = args[0];
		int loops = Integer.parseInt(args[1]);

		System.out.println("Filename: " + filename);
        System.out.println("N: " + loops);

        new Petrinet(filename, loops);
    }
    
}