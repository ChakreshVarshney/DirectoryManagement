package directoryManagement;

import java.util.LinkedList;
import java.util.List;


public class Folder {

	private String name;
	private Folder parentFolder;
	private List<Folder> nestedFolders;

	public Folder(Folder folder) {
		this.name = folder.name;
		this.nestedFolders = folder.nestedFolders.stream().map(Folder::new).toList();
	}


	public Folder(String folderName, Folder parentFolder) {
		this.name = folderName;
		this.parentFolder = parentFolder;
		this.nestedFolders = new LinkedList<Folder>();
	}

	public void addFolder(Folder folder) {
		nestedFolders.add(folder);
		folder.parentFolder = this;
	}

	public Folder deleteANestedFolder(Folder folder) {

		for (int i = 0; i < nestedFolders.size(); i++) {
			if (nestedFolders.get(i) == folder) {
				return nestedFolders.remove(i);
			}
		}
		return null;
	}

	public Folder getFolderByName(String folderName) {
		for (Folder f : nestedFolders) {
			if (f.getName().equals(folderName)) {
				return f;
			}
		}
		return null;
	}


	public boolean contains(Folder folder) {

		for (Folder f : this.getNestedFolders()) {
			if (f == folder || f.contains(folder)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasFolderWithSameName(Folder folder) {
		for (Folder f : this.getNestedFolders()) {
			if (f.getName().equals(folder.getName()) || f.hasFolderWithSameName(folder)) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		return this.name + " : " + this.nestedFolders.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public List<Folder> getNestedFolders() {
		return nestedFolders;
	}
	public void setNestedFolders(List<Folder> nestedFolders) {
		this.nestedFolders = nestedFolders;
	}

	public Folder getParentFolder() {
		return parentFolder;
	}

	public void setParentFolder(Folder parentFolder) {
		this.parentFolder = parentFolder;
	}

}
