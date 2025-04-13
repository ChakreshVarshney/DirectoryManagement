package directoryManagement;

import java.util.ArrayList;
import java.util.List;

public class DirectoryManager {
	


	private static final String OK_MSSG = "OK";
	private static final String INVALID_COMMAND = "Invalid Command";



	public void performOperationsAndGenerateOp() {
		InputOutput io = new InputOutput();
		Folder rootDir = io.getRootDirectory();
		List<Command> commands = io.getCommands();

		List<String> output = new ArrayList<>();
		

		if (rootDir == null || commands == null) {
			output.add(INVALID_COMMAND);
			io.generateOutput(output);
			return;
		}
		for(Command c:commands) {
			if(c.getType() == CommandType.CountDesendents) {
				output.add(countDesendent(rootDir, c.srcPath));
			} else if (c.getType() == CommandType.CopyPaste) {
				output.add(copyPaste(rootDir, c));
			} else if (c.getType() == CommandType.CutPaste) {
				output.add(cutPaste(rootDir, c));
			}else {
				output.add(INVALID_COMMAND);
			}
		}
		io.generateOutput(output);
		return;
		
	}

	private String cutPaste(Folder rootDir, Command c) {
		Folder srcFolder = getFolder(rootDir, c.getSrcPath());
		Folder destFolder = getFolder(rootDir, c.getDestPath());
		Folder targetFolder = srcFolder.getParentFolder().deleteANestedFolder(srcFolder);
		if (targetFolder != null && validateDir(srcFolder, destFolder)) {
			destFolder.addFolder(new Folder(targetFolder));
			return OK_MSSG;
		} else {
			return INVALID_COMMAND;
		}

	}

	private String copyPaste(Folder rootDir, Command c) {
		Folder srcFolder = getFolder(rootDir, c.getSrcPath());
		Folder destFolder = getFolder(rootDir, c.getDestPath());

		if (srcFolder != null && validateDir(srcFolder, destFolder)) {
			destFolder.addFolder(new Folder(srcFolder));
			return OK_MSSG;
		} else {
			return INVALID_COMMAND;
		}

	}

	private boolean validateDir(Folder srcFolder, Folder destFolder) {
		boolean isValid = true;
		isValid = isValid && (srcFolder != destFolder);
		isValid = isValid && srcFolder.contains(destFolder);
		isValid = isValid && destFolder.hasFolderWithSameName(srcFolder);

		return isValid;
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

	private String countDesendent(Folder rootDir, String srcPath) {
		Folder currFolder = getFolder(rootDir, srcPath);
		if (currFolder == null) {
			return INVALID_COMMAND;
		}

		return currFolder.getNestedFolders().size() + "";
	}


	
}
