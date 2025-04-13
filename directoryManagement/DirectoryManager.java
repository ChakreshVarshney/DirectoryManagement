package directoryManagement;

import java.util.ArrayList;
import java.util.List;

public class DirectoryManager {
	


	private static final String OK_MSSG = "OK";
	private static final String INVALID_COMMAND = "Invalid command";



	public void performOperationsAndGenerateOp() {
		InputOutputUtility io = new InputOutputUtility();
		Folder rootDir = io.getRootDirectory();
		List<Command> commands = io.getCommands();

		List<String> output = new ArrayList<>();
		

		if (rootDir == null || commands == null) {
			output.add(INVALID_COMMAND);
			io.generateOutput(output);
			return;
		}
		for(Command c:commands) {
			if (c.getType() == CommandType.countDescendants) {
				output.add(countDesendent(rootDir, c.srcPath));
			} else if (c.getType() == CommandType.copyPaste) {
				output.add(copyPaste(rootDir, c));
			} else if (c.getType() == CommandType.cutPaste) {
				output.add(cutPaste(rootDir, c));
			}else {
				output.add(INVALID_COMMAND);
			}
		}
		io.generateOutput(output);
		return;
		
	}

	private String cutPaste(Folder rootDir, Command c) {
		Folder srcFolder = InputOutputUtility.getFolder(rootDir, c.getSrcPath());
		Folder destFolder = InputOutputUtility.getFolder(rootDir, c.getDestPath());
		Folder targetFolder = null;
		if (srcFolder != null && destFolder != null) {
			targetFolder = srcFolder.getParentFolder().deleteANestedFolder(srcFolder);

		}
		if (targetFolder != null && validateDir(srcFolder, destFolder)) {
			destFolder.addFolder(new Folder(targetFolder));
			return OK_MSSG;
		} else {
			return INVALID_COMMAND;
		}

	}

	private String copyPaste(Folder rootDir, Command c) {
		Folder srcFolder = InputOutputUtility.getFolder(rootDir, c.getSrcPath());
		Folder destFolder = InputOutputUtility.getFolder(rootDir, c.getDestPath());

		if (srcFolder != null && validateDir(srcFolder, destFolder)) {
			if (getDescendandCount(rootDir) + getDescendandCount(srcFolder) > 1000000) {
				return INVALID_COMMAND;
			}
			destFolder.addFolder(new Folder(srcFolder));
			return OK_MSSG;
		} else {
			return INVALID_COMMAND;
		}

	}

	private boolean validateDir(Folder srcFolder, Folder destFolder) {
		if (srcFolder == null || destFolder == null) {
			return false;

		}
		boolean isValid = true;
		isValid = isValid && (srcFolder != destFolder);
		isValid = isValid && !srcFolder.contains(destFolder);
		isValid = isValid && !destFolder.hasFolderWithSameName(srcFolder);

		return isValid;
	}



	private String countDesendent(Folder rootDir, String srcPath) {
		Folder currFolder = InputOutputUtility.getFolder(rootDir, srcPath);
		if (currFolder == null) {
			return INVALID_COMMAND;
		}
		int count = getDescendandCount(currFolder) - 1;
		return count + "";
	}

	private int getDescendandCount(Folder folder) {
		if (folder == null) {
			return 0;
		}
		int count = 1;
		for (Folder f : folder.getNestedFolders()) {
			count += getDescendandCount(f);
		}
		return count;
	}


	
}
