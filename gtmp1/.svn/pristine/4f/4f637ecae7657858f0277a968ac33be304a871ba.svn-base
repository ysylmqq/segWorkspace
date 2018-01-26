package com.chinaGPS.gtmp.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package:com.chinaGPS.gtmp.entity
 * @ClassName:TreeNode
 * @Description:
 * @author:zfy
 * @param <T>
 * @date:2013-4-16 下午03:04:36
 */
public class TreeNode<T> {
    private String id;          //要显示的子节点的ID  
    private String text;        //要显示的子节点的 Text  
    private String iconCls;     //节点的图标  
    private String parentId;    //父节点的ID 
    private boolean checked;
    private T attributes;    //父节点的ID  
    private List<TreeNode>  children;   //孩子节点的List  
  
    public TreeNode(){}
    public TreeNode(String id, String text, String iconCls, String parentId,  
	    
	           List<TreeNode>children) {  
	  
	       super();  
	       this.id= id;  
	       this.text= text;  
	       this.iconCls= iconCls;  
	       this.parentId= parentId;  
	       this.children= children;  
	    }  
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }  
  //添加孩子的方法  
    
    public void addChild(TreeNode node){  
       if(this.children == null){  
           children= new ArrayList<TreeNode>();  
           children.add(node);  
       }else{  
           children.add(node);  
       }  
            
    }
    public T getAttributes() {
        return attributes;
    }
    public void setAttributes(T attributes) {
        this.attributes = attributes;
    }
    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

