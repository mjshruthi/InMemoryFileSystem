package FileSystem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class FileSystem {
	INode root;
	public FileSystem(){
		root = new INode("/", "/", true, null);
	}
	
	public void createINode(String absPath, INodeType type){
		
		if(absPath.equals("/")){
			System.out.println("Root dir already exists!");
			return;
		}
		
		String[] paths = absPath.split("/");
		String name = paths[paths.length-1];
		String parentFolder = null;
		if (paths.length > 2)
			parentFolder = absPath.substring(0, absPath.length()-name.length()-1);
		else
			parentFolder = "/";
		
		//System.out.println("name: "+name+"Parent: "+parentFolder);
		if(!hasPath(parentFolder)){
			return;
		}
		
		INode node = getNodeAtPath(parentFolder);
		if(node.hasChild(name)){
			System.out.println("Dir/File already exists!");
			return;
		}
		
		INode newINode = null;
		if(type.equals(INodeType.FOLDER))
			newINode = new INode(name, absPath, true, null);
		else if(type.equals(INodeType.FILE))
			newINode = new INode(name, absPath, false, null);
		node.setChildNode(newINode);
	}
	
//	public INode createFile(String name, String content){
//		return new INode(name, false, content);
//	}

	public void listFolder(String absPath) {
		if(hasPath(absPath)){
			INode node = getNodeAtPath(absPath);
			HashSet<INode> childNodes = node.getChildNode();
			for(INode child : childNodes){
				System.out.println(child.getName());
			}
		}
	}
	
	public boolean hasPath(String absPath){
		if(absPath.equals("/"))
			return true;
		
		String[] paths = absPath.split("/");
		
		INode node = root;
		for(int i = 1 ; i<paths.length; i++){
			if(node.hasChild(paths[i])){
				node = node.getChildNode(paths[i]);
			}
			else{
				System.out.println("Path doesn't exist :" + absPath);
				return false;
			}
		}
		return true;
	}
	
	public INode getNodeAtPath(String absPath){
		if (absPath.equals("/"))
			return root;
		
		String[] paths = absPath.split("/");
		INode node = root;
		for(String path : paths){
			if(node.hasChild(path)){
				node = node.getChildNode(path);
			}
		}
		return node;
	}

	public void writeToFile(String fileContent, String absPath) {
		if(hasPath(absPath)){
			INode node = getNodeAtPath(absPath);
			if(!node.isDir()){
				node.setContent(fileContent);
			}
			else{
				System.out.println("Path not a file !");
			}
				
		}
	}

	public void displayFile(String absPath) {
		if(hasPath(absPath)){
			INode node = getNodeAtPath(absPath);
			if(!node.isDir()){
				System.out.println(node.getContent());
			}
		}	
	}

	public void copyFileContents(String fromAbsPath, String toAbsPath) {
		if(!hasPath(fromAbsPath) || !hasPath(toAbsPath)){
			return;
		}
		INode fromNode = getNodeAtPath(fromAbsPath);
		INode toNode = getNodeAtPath(toAbsPath);
		if(fromNode.isDir() || toNode.isDir()){
			System.out.println("Both arguments are supposed to be file !");
			return;
		}
		toNode.setContent(fromNode.getContent());
	}

	public void findINode(String absPath, String fileName) {
		Stack<INode> traverseSet = new Stack<INode>();
		ArrayList<String> foundPaths = new ArrayList<String>();
		//INode node = null;
		if(absPath.equals("/")){
			traverseSet.push(root);
		}
		else if(hasPath(absPath)){
			traverseSet.push(getNodeAtPath(absPath));
		}
		while (!traverseSet.isEmpty()){
			INode node = traverseSet.pop();
			if(!node.isDir() && node.getName().equals(fileName))
				foundPaths.add(node.getAbsPath());
			else if(node.isDir()){
				for(INode childNode : node.getChildNode()){
					traverseSet.add(childNode);
				}
			}
		}
		if(foundPaths.isEmpty()){
			System.out.println("no matching file found");
		}
		for(int i=0; i<foundPaths.size(); i++){
			System.out.println(foundPaths.get(i));
		}
	}
}
