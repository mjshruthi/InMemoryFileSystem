package FileSystem;

import java.util.HashSet;
import java.util.Hashtable;

public class INode {
	private String name;
	private boolean isDir;
	private Hashtable<String, INode> childNodeLookup;
	private String content;
	private String absPath;
	
	public INode(String name, String absPath, boolean isDir, String content){
		setName(name);
		setDir(isDir);
		setContent(content);
		setAbsPath(absPath);
		if(isDir){
			childNodeLookup = new Hashtable<String, INode>();
		}
	}
	
	public boolean isDir() {
		return isDir;
	}

	public void setDir(boolean isDir) {
		this.isDir = isDir;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		if(!this.isDir()){
			this.content = content;
		}
	}

	public HashSet<INode> getChildNode(){
		if(childNodeLookup != null && this.isDir){
			return new HashSet<INode>(childNodeLookup.values());
		}
		return null;
	}
	
	public INode getChildNode(String childNodeName){
		if(childNodeLookup != null && childNodeLookup.containsKey(childNodeName)){
			return childNodeLookup.get(childNodeName);
		}
		else
			return null;
	}
	
	public void setChildNode(INode node){	
		childNodeLookup.put(node.getName(), node);
	}
	
	public boolean hasChild(String path){
		if(childNodeLookup == null)
			return false;
		else
			return childNodeLookup.containsKey(path);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbsPath() {
		return absPath;
	}

	public void setAbsPath(String absPath) {
		this.absPath = absPath;
	}
}
