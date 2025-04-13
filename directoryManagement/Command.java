package directoryManagement;

public class Command {
	CommandType type;
	String srcPath;
	String destPath;

	public Command(String[] commandStrArr) {
		this.type = CommandType.valueOf(commandStrArr[0]);
		this.srcPath = commandStrArr[1];
		if (commandStrArr.length > 2) {
			this.destPath = commandStrArr[2];
		}
	}

	public String toString() {
		return "type: " + type.toString() + " srcPath: " + srcPath + " destPath: " + destPath;
	}

	public CommandType getType() {
		return type;
	}

	public void setType(CommandType type) {
		this.type = type;
	}

	public String getSrcPath() {
		return srcPath;
	}

	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}

	public String getDestPath() {
		return destPath;
	}

	public void setDestPath(String destPath) {
		this.destPath = destPath;
	}

}
