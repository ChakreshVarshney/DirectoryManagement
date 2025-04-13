package directoryManagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class InputOutput {

	private int n = 0, q = 0;
	private int dirSize = 0;

	private Folder rootDirectory;
	private List<Command> commands;



	public InputOutput() {
		readDataFromUser();
	}

	public void readDataFromUser() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		List<String> directoryStr = new ArrayList<>();
		List<String> commandsStr = new ArrayList<>();
		try {
			String temp = br.readLine();
			n = Integer.parseInt(temp.split(" ")[0]);
			q = Integer.parseInt(temp.split(" ")[1]);
			for (int i = 0; i < n; i++) {

				directoryStr.add(br.readLine());
			}
			this.rootDirectory = getDirectoryFromIp(directoryStr);
			for (int i = 0; i < q; i++) {
				commandsStr.add(br.readLine());
			}
			this.commands = getCommandsFromIp(commandsStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Folder getDirectoryFromIp(List<String> directoryStr) {
		Folder rootDir = null;
		for (int i = 0; i < directoryStr.size(); i++) {

			String[] folderMappingStr = directoryStr.get(i).split(" ");
			if (i == 0) {
				dirSize++;
				rootDir = new Folder(folderMappingStr[0], null);
			}
			Folder folder = getFolder(rootDir, folderMappingStr[0]);
			for (int j = 1; j < folderMappingStr.length; j++) {
				folder.addFolder(new Folder(folderMappingStr[j], folder));
				dirSize++;
			}

		}
		System.out.println(rootDir.toString());
		return rootDir;
	}

	private Folder getFolder(Folder rootDir, String srcPath) {
		String[] pathArr = srcPath.split("//");
		Folder tempFolder = null;

		for (int i = 0; i < pathArr.length; i++) {
			if ((i == 0 && pathArr[i].equals(rootDir.getName()))) {
				tempFolder = rootDir;
			} else if ((i == 0 && !pathArr[i].equals(rootDir.getName()))) {
				break;
			} else {
				tempFolder = tempFolder.getFolderByName(pathArr[i]);
			}

		}
		return tempFolder;
	}

	public List<Command> getCommandsFromIp(List<String> commandsStr) {
		List<Command> commands = new ArrayList<>();
		for (String command : commandsStr) {
			String[] commandStrArr = command.split(" ");
			Command c = new Command(commandStrArr);
			commands.add(c);
		}
		return commands;
	}

	public void generateOutput(List<String> output) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < output.size(); i++) {
			sb.append(output.get(i));
			if (i < output.size() - 1) {
				sb.append("\n");
			}
		}
		System.out.print(sb);
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getQ() {
		return q;
	}

	public void setQ(int q) {
		this.q = q;
	}

	public int getDirSize() {
		return dirSize;
	}

	public void setDirSize(int dirSize) {
		this.dirSize = dirSize;
	}

	public Folder getRootDirectory() {
		return rootDirectory;
	}

	public void setRootDirectory(Folder rootDirectory) {
		this.rootDirectory = rootDirectory;
	}

	public List<Command> getCommands() {
		return commands;
	}

	public void setCommands(List<Command> commands) {
		this.commands = commands;
	}
}
