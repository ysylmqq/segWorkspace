package bhz.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement
public class User //implements Serializable
{

    @NotNull
    private String id;

    @JsonProperty("name")	//{name:"张三"}
    //@XmlElement(name = "name") //<name>张三</name>
    @NotNull
    @Size(min = 6, max = 50)
    private String name;
    
	public User() {
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
