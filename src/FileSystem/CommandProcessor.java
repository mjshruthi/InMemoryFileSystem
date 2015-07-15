package FileSystem;

import java.util.Scanner;

public class CommandProcessor {
	public static void main(String[] args){
		Scanner reader = new Scanner(System.in);
		String helpText = 	"Commands supported:\n"+
							"mkdir <absoluteDirPath>\n"+  							// Create new folder"
							"create <absoluteFilePath>\n"+							// Create new file"+
							"write <someText> <absoluteFilePath>\n"+ 					// add content to file"+
							"cp <absoluteFromFilePath> <absoluteToFilePath>\n"+ 		// copy file"+
							"cp <absoluteFromFolderPath> <absoluteToFolderPath>\n"+ 	// optional-copy folder"+
							"cat <absoluteFilePath>\n"+ 								// display file contents"+
							"ls <absoluteFolderPath>\n"+  							// lists folder contents"+
							"find [folderPath] <fileName>\n"+						// searches for a file name"
							"exit";													// exits the program
		
		System.out.println(helpText);

		FileSystem fs = new FileSystem();
		while(true){
				String inputString = reader.nextLine();
				if(inputString == "" || inputString == null){
					continue;
				}
				String[] commandLine = inputString.split(" ");
				if(commandLine.length < 2){
					System.out.println("Insufficient arguments ! : "+ inputString);
					continue;
				}
				String command = commandLine[0];
				switch (command){
					case "mkdir":
						fs.createINode(commandLine[1],INodeType.FOLDER);
						break;
					case "create":
						fs.createINode(commandLine[1], INodeType.FILE);
						break;
					case "write":
						//Special parsing required to extract text
						String[] splitByQuote = inputString.split("\"");
						if(splitByQuote.length != 3){
							System.out.println("Incorrect arguments ! : "+ inputString);
							break;
						}
						String text = splitByQuote[1];
						String absPath = splitByQuote[2].trim();
						if(commandLine.length < 3){
							System.out.println("Insufficient arguments ! :"+ inputString);
							break;
						}
						fs.writeToFile(text, absPath);
						break;
					case "cp":
						if(commandLine.length < 3){
							System.out.println("Insufficient arguments !" + inputString);
							break;
						}
						fs.copyFileContents(commandLine[1], commandLine[2]);
						break;
					case "cat":
						fs.displayFile(commandLine[1]);
						break;
					case "ls":
						fs.listFolder(commandLine[1]);
						break;
					case "find":
						if(commandLine.length == 2)
							fs.findINode("/", commandLine[1]);
						else if(commandLine.length == 3)
							fs.findINode(commandLine[1], commandLine[2]);
						break;
					case "exit": 
						System.exit(0);
					default:
						System.out.println(helpText);
						break;
				}
		}
	}
}
