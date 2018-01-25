package com.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.entity.User;

@Path("/userService")
//指定postUser()接收JSON格式的数据。REST框架会自动将JSON数据反序列化为User对象
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
//@Produces({"application/json", "text/xml"})  //返回user对象时，把user对象序列化成json字符串。 框架会自动将User对象序列化为JSON数据
//@Produces({MediaType.TEXT_XML,MediaType.APPLICATION_JSON,ContentType.TEXT_XML_UTF_8,ContentType.APPLICATION_JSON_UTF_8})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public interface UserService {
	
	@GET
    @Path("/testget")
	public void testget(@Context HttpServletRequest request);
	
	@GET
	@Path("/getUser")
	public User getUser(@Context HttpServletRequest request);
	
	@GET
	@Path("/get/{id : \\d+}")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON, ContentType.TEXT_XML_UTF_8,ContentType.APPLICATION_JSON_UTF_8})
	public User getUser(@PathParam(value = "id") Integer id);
	
	@GET
	@Path("/get/{id : \\d+}/{name : [a-zA-Z][0-9]}")
	public User getUser(@PathParam(value = "id") Integer id, @PathParam(value = "name") String name);
    
	@POST
    @Path("/testpost")
	public void testpost();
   
	@POST
	@Path("/postUser")
	public User postUser(User user);
	
	@POST
	@Path("/post/{id}")
	public User postUser(@PathParam(value = "id") String id);

}
