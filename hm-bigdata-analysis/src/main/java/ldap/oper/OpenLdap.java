package ldap.oper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hm.bigdata.comm.SystemConst;
import com.hm.bigdata.util.PageSelect;
import com.hm.bigdata.util.page.Page;
import com.hm.bigdata.util.page.PageUtil;

import ldap.objectClasses.CommonCompany;
import ldap.objectClasses.CommonLog;
import ldap.objectClasses.CommonModule;
import ldap.objectClasses.CommonOperator;
import ldap.objectClasses.CommonRole;
import ldap.objectClasses.CommonSystem;
import ldap.util.Config;
import ldap.util.IOUtil;
import ldap.util.LDAPSecurityUtils;
import ldap.util.Util;

public class OpenLdap {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(OpenLdap.class);

	private static final String DB_CONFIG_FILE_PATH = "classes/ldap.properties";

    private DirContext ctx = null;
    
    private Properties properties = new Properties();
    
    private String account = "Manager";//操作LDAP的帐户。默认就是Manager。
    
    private String password = "secret";//帐户Manager的密码。
    
    private String root = "dc=chinagps,dc=com"; //LDAP的根节点的DC
    
    private String ldapURL = "ldap://localhost/";
    
    private String ldapURL2 = "ldap://localhost/";
    
    private String opid;

    public OpenLdap() {
    	String configPath = Config.getConfigPath();
		String filePath = configPath + DB_CONFIG_FILE_PATH;
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);
			properties.load(is);
		}catch (IOException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
		}finally{
			IOUtil.closeIS(is);
		}
		account = properties.getProperty("LdapUser");
		password = properties.getProperty("LdapPassword");
		root = properties.getProperty("Root");
		ldapURL = properties.getProperty("LdapURL");
		ldapURL2 = properties.getProperty("LdapURL2");
		init();
		List<CommonOperator> list = searchOperator("one", "(objectclass=commonOperator)");
		if(list.size()>0){
			opid = list.get(0).getOpid();
		}
		close();
    }

    /**
     * 初始化上下文
     */
    private void init() {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapURL);      
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "cn=" + account + "," + root);
        env.put(Context.SECURITY_CREDENTIALS, password);
        try {
            ctx = new InitialDirContext(env);//初始化上下文
            //System.out.println("认证成功");//这里可以改成异常抛出。
        } catch (Exception e) {
    		env.put(Context.PROVIDER_URL, ldapURL2); 
    		try {
				ctx = new InitialDirContext(env);//初始化上下文
			} catch (Exception e1) {
				System.out.println("认证出错"+e1);
			}
        }
    }
    
    /*
     * 关闭上下文
     */
    private void close() {
        if (ctx != null) {
            try {
                ctx.close();
            } catch (NamingException e) {
                System.out.println("NamingException in close():" + e);
            }
        }
    }

    /**
     * LDAP增加对象
     * @param obj
     */
    public void add(Object obj) {
        try {
            BasicAttributes attrs = new BasicAttributes();
            BasicAttribute objclassSet = new BasicAttribute("objectClass");
            objclassSet.add("top");
            String id = Util.getUUID();
            String dn = "";
            if(obj instanceof CommonCompany){
                objclassSet.add("commonCompany");
            	attrs.put(objclassSet);
                CommonCompany company = (CommonCompany)obj;
                if(Util.isNotBlank(company.getCompanyno())){
                	id = company.getCompanyno();
                }
                attrs.put("companyno", id);
                attrs.put("companyname", company.getCompanyname());
                dn = "companyno=" + id + "," + root;
                if(Util.isNotBlank(company.getParentcompanyno())){
                	attrs.put("parentcompanyno", company.getParentcompanyno());
                	String filter = "(&(objectclass=commonCompany)(companyno="+company.getParentcompanyno()+"))";
                	List<SearchResult> companyList = searchInformation("", filter);
                	if(companyList.size()>0){
                		String temp = companyList.get(0).getName();
                		dn = "companyno=" + id + "," + temp + "," + root;
                	}
                }
                if(Util.isNotBlank(company.getOrder())){
                	attrs.put("order", company.getOrder());
                }
                if(Util.isNotBlank(company.getCompanylevel())){
                	attrs.put("companylevel", company.getCompanylevel());
                }
                if(Util.isNotBlank(company.getOpid())){
                	attrs.put("opid", company.getOpid());
                }
                if(Util.isNotBlank(company.getAddress())){
                	attrs.put("address", company.getAddress());
                }
                if(Util.isNotBlank(company.getTime())){
                	attrs.put("time", company.getTime());
                }
                if(Util.isNotBlank(company.getCnfullname())){
                	attrs.put("cnfullname", company.getCnfullname());
                }
                if(Util.isNotBlank(company.getEnfullname())){
                	attrs.put("enfullname", company.getEnfullname());
                }
                if(Util.isNotBlank(company.getCompanycode())){
                	attrs.put("companycode", company.getCompanycode());
                }
                if(Util.isNotBlank(company.getCompanytype())){
                	attrs.put("companytype", company.getCompanytype());
                }
                init();
                ctx.createSubcontext(dn, attrs);
                close();
            }else if(obj instanceof CommonModule){
                objclassSet.add("commonModule");
            	attrs.put(objclassSet);
            	CommonModule module = (CommonModule)obj;
            	if(Util.isNotBlank(module.getModuleid())){
                	id = module.getModuleid();
                }
                attrs.put("moduleid", id);
                attrs.put("modulename", module.getModulename());
                if(Util.isNotBlank(module.getParentmoduleid())){
                	attrs.put("parentmoduleid", module.getParentmoduleid());
                	String filter = "(&(objectclass=commonModule)(moduleid="+module.getParentmoduleid()+"))";
                	List<SearchResult> moduleList = searchInformation("", filter);
                	if(moduleList.size()>0){
                		String temp = moduleList.get(0).getName();
                		dn = "moduleid=" + id + "," + temp + "," + root;
                	}
                }
                if(Util.isNotBlank(module.getIsIpLimited())){
                	attrs.put("isIpLimited", module.getIsIpLimited());
                }
                if(Util.isNotBlank(module.getLimitedIpAddr())){
                	attrs.put("limitedIpAddr", module.getLimitedIpAddr());
                }
                if(Util.isNotBlank(module.getAllowedIpAddr())){
                	attrs.put("allowedIpAddr", module.getAllowedIpAddr());
                }
                if(Util.isNotBlank(module.getSystemid())){
                	attrs.put("systemid", module.getSystemid());
                	if(dn.equals("")){
	                	String filter = "(&(objectclass=commonSystem)(systemid="+module.getSystemid()+"))";
	                	List<SearchResult> systemList = searchInformation("", filter);
                		String temp = systemList.get(0).getName();
                		dn = "moduleid=" + id + "," + temp + "," + root;
                	}
                }
                if(Util.isNotBlank(module.getRemark())){
                	attrs.put("remark", module.getRemark());
                }
                if(Util.isNotBlank(module.getOrder())){
                	attrs.put("order", module.getOrder());
                }
                if(Util.isNotBlank(module.getControl1())){
                	attrs.put("control1", module.getControl1());
                }
                if(Util.isNotBlank(module.getControl2())){
                	attrs.put("control2", module.getControl2());
                }
                if(Util.isNotBlank(module.getModulelevel())){
                	attrs.put("modulelevel", module.getModulelevel());
                }
                init();
                ctx.createSubcontext(dn, attrs);
                close();
            }else if(obj instanceof CommonOperator){
                objclassSet.add("commonOperator");
            	attrs.put(objclassSet);
            	CommonOperator operator = (CommonOperator)obj;
            	if(Util.isNotBlank(operator.getOpid())){
                	id = operator.getOpid();
                }
                attrs.put("opid", id);
                attrs.put("opname", operator.getOpname());
                if(Util.isNotBlank(operator.getRemark())){
                	attrs.put("remark", operator.getRemark());
                }
                if(Util.isNotBlank(operator.getLoginname())){
                	attrs.put("loginname", operator.getLoginname());
                }
                if(Util.isNotBlank(operator.getUserPassword())){
                	attrs.put("userPassword", operator.getUserPassword());
                }
                if(Util.isNotBlank(operator.getIdcard())){
                	attrs.put("idcard", operator.getIdcard());
                }
                if(Util.isNotBlank(operator.getJobnumber())){
                	attrs.put("jobnumber", operator.getJobnumber());
                }
                if(Util.isNotBlank(operator.getPhone())){
                	attrs.put("phone", operator.getPhone());
                }
                if(Util.isNotBlank(operator.getStatus())){
                	attrs.put("status", operator.getStatus());
                }
                if(Util.isNotBlank(operator.getMainUrl())){
                	attrs.put("mainUrl", operator.getMainUrl());
                }
                if(Util.isNotBlank(operator.getMainModuleid())){
                	attrs.put("mainModuleid", operator.getMainModuleid());
                }
                if(Util.isNotBlank(operator.getCompanyname())){
                	attrs.put("companyname", operator.getCompanyname());
                }
                if(Util.isNotBlank(operator.getRolename())){
                	attrs.put("rolename", operator.getRolename());
                }
                if(Util.isNotBlank(operator.getSex())){
                	attrs.put("sex", operator.getSex());
                }
                if(Util.isNotBlank(operator.getFax())){
                	attrs.put("fax", operator.getFax());
                }
                if(Util.isNotBlank(operator.getMail())){
                	attrs.put("mail", operator.getMail());
                }
                if(Util.isNotBlank(operator.getMobile())){
                	attrs.put("mobile", operator.getMobile());
                }
                if(Util.isNotBlank(operator.getPost())){
                	attrs.put("post", operator.getPost());
                }
                if(Util.isNotBlank(operator.getOrder())){
                	attrs.put("order", operator.getOrder());
                }
                if(operator.getCompanynos()!=null&&operator.getCompanynos().size()>0){
                	BasicAttribute companynos = new BasicAttribute("companynos");
                	for(String str:operator.getCompanynos()){
                		companynos.add(str);
                	}
                	attrs.put(companynos);
                }
                if(operator.getRolecompanynos()!=null&&operator.getRolecompanynos().size()>0){
                	BasicAttribute rolecompanynos = new BasicAttribute("rolecompanynos");
                	for(String str:operator.getRolecompanynos()){
                		rolecompanynos.add(str);
                	}
                	attrs.put(rolecompanynos);
                }
                if(operator.getRoleid()!=null&&operator.getRoleid().size()>0){
                	BasicAttribute roleid = new BasicAttribute("roleid");
                	for(String str:operator.getRoleid()){
                		roleid.add(str);
                	}
                	attrs.put(roleid);
                }
                init();
                ctx.createSubcontext("opid=" + id + ",opid=" + opid + "," + root, attrs);
                close();
            }else if(obj instanceof CommonRole){
                objclassSet.add("commonRole");
            	attrs.put(objclassSet);
            	CommonRole role = (CommonRole)obj;
            	if(Util.isNotBlank(role.getRoleid())){
                	id = role.getRoleid();
                }
                attrs.put("roleid", id);
                attrs.put("rolename", role.getRolename());
                if(Util.isNotBlank(role.getSystemid())){
                	attrs.put("systemid", role.getSystemid());
                }
                if(Util.isNotBlank(role.getRemark())){
                	attrs.put("remark", role.getRemark());
                }
                if(Util.isNotBlank(role.getCompanyno())){
                	attrs.put("companyno", role.getCompanyno());
                	String filter = "(&(objectclass=commonCompany)(companyno="+role.getCompanyno()+"))";
                	List<SearchResult> companyList = searchInformation("", filter);
                	if(companyList.size()>0){
                		String temp = companyList.get(0).getName();
                		dn = "roleid=" + id + "," + temp + "," + root;
                	}
                }
                if(Util.isNotBlank(role.getIsadmin())){
                	attrs.put("isadmin", role.getIsadmin());
                }
                if(role.getModuleids()!=null&&role.getModuleids().size()>0){
                	BasicAttribute moduleids = new BasicAttribute("moduleids");
                	for(String str:role.getModuleids()){
                		moduleids.add(str);
                	}
                	attrs.put(moduleids);
                }
                init();
                ctx.createSubcontext(dn, attrs);
                close();
            }else if(obj instanceof CommonSystem){
                objclassSet.add("commonSystem");
            	attrs.put(objclassSet);
            	CommonSystem system = (CommonSystem)obj;
            	if(Util.isNotBlank(system.getSystemid())){
                	id = system.getSystemid();
                }
                attrs.put("systemid", id);
                attrs.put("systemname", system.getSystemname());
                init();
                ctx.createSubcontext("systemid=" + id + "," + root, attrs);
                close();
            }else if(obj instanceof CommonLog){
                objclassSet.add("commonLog");
            	attrs.put(objclassSet);
            	CommonLog log = (CommonLog)obj;
            	if(Util.isNotBlank(log.getLogid())){
                	id = log.getLogid();
                }
                attrs.put("logid", id);
                attrs.put("opname", log.getOpname());
                if(Util.isNotBlank(log.getLogloginname())){
                	attrs.put("logloginname", log.getLogloginname());
                }
                if(Util.isNotBlank(log.getOpertime())){
                	attrs.put("opertime", log.getOpertime());
                }
                if(Util.isNotBlank(log.getOpertype())){
                	attrs.put("opertype", log.getOpertype());
                }
                if(Util.isNotBlank(log.getOperobject())){
                	attrs.put("operobject", log.getOperobject());
                }
                if(Util.isNotBlank(log.getMessage())){
                	attrs.put("message", log.getMessage());
                }
                init();
                ctx.createSubcontext("logid=" + id + ",logid=" + SystemConst.LOGID + "," + root, attrs);
                close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e);
            System.out.println("Exception in add():" + e);
        }
    }

    /**
     * LDAP删除对象
     * @param dn
     */
    public void delete(String dn) {
    	init();
        try {
            ctx.destroySubcontext(dn);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e);
            System.out.println("Exception in delete():" + e);
        }
        close();
    }
    
    /**
     * LDAP修改对象(单个属性)
     * @param dn
     * @param key
     * @param value
     * @return
     */
    public boolean modifyInformation(String dn, String key, Object value) {
        try {
        	init();
            ModificationItem[] mods = new ModificationItem[1];
            Attribute attr0 = null;
            if(key.equals("userPassword")){
            	String pwd = LDAPSecurityUtils.getOpenLDAPSHA((String)value);
            	attr0 = new BasicAttribute(key, pwd);
            }else if(value instanceof List){
            	attr0 = new BasicAttribute(key);
        		List<Object> list = (List<Object>)value;
        		for(Object obj:list){
        			attr0.add(obj);
        		}
        	}else{
        		attr0 = new BasicAttribute(key, value);
        	}
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                    attr0);
            ctx.modifyAttributes(dn, mods);
            close();
            return true;
        } catch (NamingException ne) {
            ne.printStackTrace();
            LOGGER.error(ne.getMessage(), ne);
            System.err.println("Error: " + ne.getMessage());
            return false;
        }

    }
    
    /**
     * LDAP修改对象(多个属性)
     * @param dn
     * @param keys
     * @param values
     * @return
     */
    public boolean modifyInformations(String dn, String[] keys, Object[] values) {
        try {
        	init();
            List<ModificationItem> modlist = new ArrayList<ModificationItem>();
            for(int i=0;i<keys.length;i++){
            	Attribute attr = null;
            	if(values[i] instanceof List){
            		attr = new BasicAttribute(keys[i]);
            		List<Object> list = (List<Object>)values[i];
            		for(Object obj:list){
            			attr.add(obj);
            		}
            	}else if(values[i] instanceof String && StringUtils.isBlank((String)values[i])){
            		continue;
            	}else{
            		attr = new BasicAttribute(keys[i], values[i]);
            	}
            	ModificationItem mod = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                        attr);
            	modlist.add(mod);
            }
            ModificationItem[] mods = new ModificationItem[modlist.size()];
            for(int i=0;i<modlist.size();i++){
            	mods[i] = modlist.get(i);
            }
            ctx.modifyAttributes(dn, mods);
            close();
            return true;
        } catch (NamingException ne) {
            ne.printStackTrace();
            LOGGER.error(ne.getMessage(), ne);
            System.err.println("Error: " + ne.getMessage());
            return false;
        }

    }
    
    /**
     * LDAP删除属性
     * @param dn
     * @param keys
     * @param values
     * @return
     */
    public boolean deleteInformations(String dn, String[] keys, Object[] values) {
        try {
        	init();
            ModificationItem[] mods = new ModificationItem[keys.length];
            for(int i=0;i<keys.length;i++){
            	Attribute attr = null;
            	if(values[i] instanceof List){
            		attr = new BasicAttribute(keys[i]);
            		List<Object> list = (List<Object>)values[i];
            		for(Object obj:list){
            			attr.add(obj);
            		}
            	}else{
            		attr = new BasicAttribute(keys[i], values[i]);
            	}
                mods[i] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE,
                        attr);
            }
            ctx.modifyAttributes(dn, mods);
            close();
            return true;
        } catch (NamingException ne) {
            ne.printStackTrace();
            LOGGER.error(ne.getMessage(), ne);
            System.err.println("Error: " + ne.getMessage());
            return false;
        }

    }
    
    /**
     * @param base ：根节点(在这里是"dc=chinagps,dc=com")
     * @param scope ：搜索范围,分为"base"(本节点),"one"(单层),""(遍历)
     * @param filter ：指定子节点(格式为"(objectclass=*)",*是指全部，你也可以指定某一特定类型的树节点)
     */
    public List<SearchResult> searchInformation(String scope, String filter) {
    	List<SearchResult> list = new ArrayList<SearchResult>();
        SearchControls sc = new SearchControls();
        if (scope.equals("base")) {
            sc.setSearchScope(SearchControls.OBJECT_SCOPE);
        } else if (scope.equals("one")) {
            sc.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        } else {
            sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
        }

        NamingEnumeration ne = null;
        init();
        try {
            ne = ctx.search(root, filter, sc);
            while (ne.hasMore()) {
            	SearchResult sr = (SearchResult) ne.next();
            	list.add(sr);
            }
        } catch (Exception nex) {
            System.err.println("Error: " + nex.getMessage());
            nex.printStackTrace();
            LOGGER.error(nex.getMessage(), nex);
        }
        close();
        return list;
    }
    
    /**
     * @param base ：根节点(在这里是"dc=chinagps,dc=com")
     * @param scope ：搜索范围,分为"base"(本节点),"one"(单层),""(遍历)
     * @param filter ：指定子节点(格式为"(objectclass=*)",*是指全部，你也可以指定某一特定类型的树节点)
     */
    public int count(String scope, String filter) {
    	int num = 0;
        SearchControls sc = new SearchControls();
        if (scope.equals("base")) {
            sc.setSearchScope(SearchControls.OBJECT_SCOPE);
        } else if (scope.equals("one")) {
            sc.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        } else {
            sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
        }

        NamingEnumeration ne = null;
        init();
        try {
            ne = ctx.search(root, filter, sc);
            while (ne.hasMore()) {
            	SearchResult sr = (SearchResult) ne.next();
            	num++;
            }
        } catch (Exception nex) {
            System.err.println("Error: " + nex.getMessage());
            nex.printStackTrace();
            LOGGER.error(nex.getMessage(), nex);
        }
        close();
        return num;
    }
    
    /**
     * LDAP查询操作员
     * @param scope
     * @param filter
     * @return
     */
    public List<CommonOperator> searchOperator(String scope, String filter) {
    	List<CommonOperator> opList = new ArrayList<CommonOperator>();
    	List<SearchResult> list = searchInformation(scope, filter);
    	try {
			for(SearchResult sr:list){
				CommonOperator operator = new CommonOperator();
				operator.setDn(sr.getName() + "," + root);
				Attributes at = sr.getAttributes();
                NamingEnumeration ane = at.getAll();
                while (ane.hasMore()) {
                    Attribute attr = (Attribute) ane.next();
                    String attrType = attr.getID();
                    NamingEnumeration values = attr.getAll();
                    //Vector vals = new Vector();
                    while (values.hasMore()) {
                    	Object oneVal = values.nextElement();
                    	String val = "";
                        if (oneVal instanceof String) {
                            val = (String) oneVal;
                        } else {
                            val = new String((byte[]) oneVal);
                        }
                        if(attrType.equals("opid")){
                    	    operator.setOpid(val);
                        }else if(attrType.equals("opname")){
                    	    operator.setOpname(val);
                        }else if(attrType.equals("remark")){
                    	    operator.setRemark(val);
                        }else if(attrType.equals("loginname")){
                    	    operator.setLoginname(val);
                        }else if(attrType.equals("userPassword")){
                    	    operator.setUserPassword(val);
                        }else if(attrType.equals("idcard")){
                    	    operator.setIdcard(val);
                        }else if(attrType.equals("jobnumber")){
                    	    operator.setJobnumber(val);
                        }else if(attrType.equals("phone")){
                    	    operator.setPhone(val);
                        }else if(attrType.equals("status")){
                    	    operator.setStatus(val);
                        }else if(attrType.equals("mainUrl")){
                    	    operator.setMainUrl(val);
                        }else if(attrType.equals("mainModuleid")){
                    	    operator.setMainModuleid(val);
                        }else if(attrType.equals("companyname")){
                    	    operator.setCompanyname(val);
                        }else if(attrType.equals("rolename")){
                    	    operator.setRolename(val);
                        }else if(attrType.equals("sex")){
                    	    operator.setSex(val);
                        }else if(attrType.equals("facsimileTelephoneNumber")){
                    	    operator.setFax(val);
                        }else if(attrType.equals("mail")){
                    	    operator.setMail(val);
                        }else if(attrType.equals("mobile")){
                    	    operator.setMobile(val);
                        }else if(attrType.equals("post")){
                    	    operator.setPost(val);
                        }else if(attrType.equals("order")){
                    	    operator.setOrder(val);
                        }else if(attrType.equals("companynos")){
                        	List<String> companyList = operator.getCompanynos();
                    	    if(companyList==null){
                    	    	companyList = new ArrayList<String>();
                    	    }
                    	    companyList.add(val);
                    	    operator.setCompanynos(companyList);
                        }else if(attrType.equals("rolecompanynos")){
                        	List<String> rolecompanyList = operator.getRolecompanynos();
                    	    if(rolecompanyList==null){
                    	    	rolecompanyList = new ArrayList<String>();
                    	    }
                    	    rolecompanyList.add(val);
                    	    operator.setRolecompanynos(rolecompanyList);
                        }else if(attrType.equals("roleid")){
                        	List<String> roleList = operator.getRoleid();
                    	    if(roleList==null){
                    	    	roleList = new ArrayList<String>();
                    	    }
                    	    roleList.add(val);
                    	    operator.setRoleid(roleList);
                        }
                    }
                }
                opList.add(operator);
			}
		} catch (NamingException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
		}
    	//排序
		for(int i=0;i<opList.size();i++){
			for(int j=i+1;j<opList.size();j++){
				int order1 = Integer.valueOf(opList.get(i).getOrder());
				int order2 = Integer.valueOf(opList.get(j).getOrder());
				if(order1>order2){
					CommonOperator temp = opList.get(i);
					opList.set(i, opList.get(j));
					opList.set(j, temp);
				}
			}
		}
		//根据公司ID排序
		String companyIds = getCompanyIds();
		for(int i=0;i<opList.size();i++){
			for(int j=i+1;j<opList.size();j++){
				String companyId1 = "";
				String companyId2 = "";
				if(opList.get(i).getCompanynos()!=null&&opList.get(i).getCompanynos().size()>0){
					companyId1 = opList.get(i).getCompanynos().get(0);
				}
				if(opList.get(j).getCompanynos()!=null&&opList.get(j).getCompanynos().size()>0){
					companyId2 = opList.get(j).getCompanynos().get(0);
				}
				int index1 = companyIds.indexOf(companyId1);
				int index2 = companyIds.indexOf(companyId2);
				if(index1>index2){
					CommonOperator temp = opList.get(i);
					opList.set(i, opList.get(j));
					opList.set(j, temp);
				}
			}
		}
    	return opList;
    }
    
    /**
     * LDAP查询角色
     * @param scope
     * @param filter
     * @return
     */
    public List<CommonRole> searchRole(String scope, String filter) {
    	List<CommonRole> roleList = new ArrayList<CommonRole>();
    	List<SearchResult> list = searchInformation(scope, filter);
    	try {
			for(SearchResult sr:list){
				CommonRole role = new CommonRole();
				role.setDn(sr.getName() + "," + root);
				Attributes at = sr.getAttributes();
                NamingEnumeration ane = at.getAll();
                while (ane.hasMore()) {
                    Attribute attr = (Attribute) ane.next();
                    String attrType = attr.getID();
                    NamingEnumeration values = attr.getAll();
                    //Vector vals = new Vector();
                    while (values.hasMore()) {
                    	Object oneVal = values.nextElement();
                    	String val = "";
                        if (oneVal instanceof String) {
                            val = (String) oneVal;
                        } else {
                            val = new String((byte[]) oneVal);
                        }
                        if(attrType.equals("roleid")){
                        	role.setRoleid(val);
                        }else if(attrType.equals("rolename")){
                    	    role.setRolename(val);
                        }else if(attrType.equals("systemid")){
                    	    role.setSystemid(val);
                        }else if(attrType.equals("remark")){
                    	    role.setRemark(val);
                        }else if(attrType.equals("companyno")){
                    	    role.setCompanyno(val);
                        }else if(attrType.equals("isadmin")){
                    	    role.setIsadmin(val);
                        }else if(attrType.equals("moduleids")){
                    	    List<String> moduleList = role.getModuleids();
                    	    if(moduleList==null){
                    	    	moduleList = new ArrayList<String>();
                    	    }
                    	    moduleList.add(val);
                    	    role.setModuleids(moduleList);
                        }
                    }
                }
                roleList.add(role);
			}
		} catch (NamingException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
		}
    	return roleList;
    }
    
    /**
     * LDAP查询子系统
     * @param scope
     * @param filter
     * @return
     */
    public List<CommonSystem> searchSystem(String scope, String filter) {
    	List<CommonSystem> systemList = new ArrayList<CommonSystem>();
    	List<SearchResult> list = searchInformation(scope, filter);
    	try {
			for(SearchResult sr:list){
				CommonSystem system = new CommonSystem();
				system.setDn(sr.getName() + "," + root);
				Attributes at = sr.getAttributes();
                NamingEnumeration ane = at.getAll();
                while (ane.hasMore()) {
                    Attribute attr = (Attribute) ane.next();
                    String attrType = attr.getID();
                    NamingEnumeration values = attr.getAll();
                    //Vector vals = new Vector();
                    while (values.hasMore()) {
                    	Object oneVal = values.nextElement();
                    	String val = "";
                        if (oneVal instanceof String) {
                            val = (String) oneVal;
                        } else {
                            val = new String((byte[]) oneVal);
                        }
                        if(attrType.equals("systemid")){
                        	system.setSystemid(val);
                        }else if(attrType.equals("systemname")){
                    	    system.setSystemname(val);
                        }
                    }
                }
                systemList.add(system);
			}
		} catch (NamingException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
		}
    	return systemList;
    }
    
    /**
     * LDAP查询模块(已排序)
     * @param scope
     * @param filter
     * @return
     */
    public List<CommonModule> searchModule(String scope, String filter) {
    	List<CommonModule> moduleList = new ArrayList<CommonModule>();
    	List<SearchResult> list = searchInformation(scope, filter);
    	try {
			for(SearchResult sr:list){
				CommonModule module = new CommonModule();
				module.setDn(sr.getName() + "," + root);
				Attributes at = sr.getAttributes();
                NamingEnumeration ane = at.getAll();
                while (ane.hasMore()) {
                    Attribute attr = (Attribute) ane.next();
                    String attrType = attr.getID();
                    NamingEnumeration values = attr.getAll();
                    //Vector vals = new Vector();
                    while (values.hasMore()) {
                    	Object oneVal = values.nextElement();
                    	String val = "";
                        if (oneVal instanceof String) {
                            val = (String) oneVal;
                        } else {
                            val = new String((byte[]) oneVal);
                        }
                        if(attrType.equals("moduleid")){
                        	module.setModuleid(val);
                        }else if(attrType.equals("modulename")){
                        	module.setModulename(val);
                        }else if(attrType.equals("parentmoduleid")){
                        	module.setParentmoduleid(val);
                        }else if(attrType.equals("isIpLimited")){
                        	module.setIsIpLimited(val);
                        }else if(attrType.equals("limitedIpAddr")){
                        	module.setLimitedIpAddr(val);
                        }else if(attrType.equals("allowedIpAddr")){
                        	module.setAllowedIpAddr(val);
                        }else if(attrType.equals("systemid")){
                        	module.setSystemid(val);
                        }else if(attrType.equals("remark")){
                        	module.setRemark(val);
                        }else if(attrType.equals("order")){
                        	module.setOrder(val);
                        }else if(attrType.equals("control1")){
                        	module.setControl1(val);
                        }else if(attrType.equals("control2")){
                        	module.setControl2(val);
                        }else if(attrType.equals("modulelevel")){
                        	module.setModulelevel(val);
                        }
                    }
                }
                moduleList.add(module);
			}
		} catch (NamingException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
		}
		//排序
		for(int i=0;i<moduleList.size();i++){
			for(int j=i+1;j<moduleList.size();j++){
				int order1 = Integer.valueOf(moduleList.get(i).getOrder());
				int order2 = Integer.valueOf(moduleList.get(j).getOrder());
				if(order1>order2){
					CommonModule temp = moduleList.get(i);
					moduleList.set(i, moduleList.get(j));
					moduleList.set(j, temp);
				}
			}
		}
		//根据模块节点等级排序
		for(int i=0;i<moduleList.size();i++){
			for(int j=i+1;j<moduleList.size();j++){
				int level1 = Integer.valueOf(moduleList.get(i).getModulelevel());
				int level2 = Integer.valueOf(moduleList.get(j).getModulelevel());
				if(level1>level2){
					CommonModule temp = moduleList.get(i);
					moduleList.set(i, moduleList.get(j));
					moduleList.set(j, temp);
				}
			}
		}
    	return moduleList;
    }
    
    /**
     * LDAP查询公司(已排序)
     * @param scope
     * @param filter
     * @return
     */
    public List<CommonCompany> searchCompany(String scope, String filter) {
    	List<CommonCompany> companyList = new ArrayList<CommonCompany>();
    	List<SearchResult> list = searchInformation(scope, filter);
    	try {
			for(SearchResult sr:list){
				CommonCompany company = new CommonCompany();
				company.setDn(sr.getName() + "," + root);
				Attributes at = sr.getAttributes();
                NamingEnumeration ane = at.getAll();
                while (ane.hasMore()) {
                    Attribute attr = (Attribute) ane.next();
                    String attrType = attr.getID();
                    NamingEnumeration values = attr.getAll();
                    //Vector vals = new Vector();
                    while (values.hasMore()) {
                    	Object oneVal = values.nextElement();
                    	String val = "";
                        if (oneVal instanceof String) {
                            val = (String) oneVal;
                        } else {
                            val = new String((byte[]) oneVal);
                        }
                        if(attrType.equals("companyno")){
                        	company.setCompanyno(val);
                        }else if(attrType.equals("companyname")){
                        	company.setCompanyname(val);
                        }else if(attrType.equals("parentcompanyno")){
                        	company.setParentcompanyno(val);
                        }else if(attrType.equals("order")){
                        	company.setOrder(val);
                        }else if(attrType.equals("companylevel")){
                        	company.setCompanylevel(val);
                        }else if(attrType.equals("opid")){
                        	company.setOpid(val);
                        }else if(attrType.equals("address")){
                        	company.setAddress(val);
                        }else if(attrType.equals("time")){
                        	company.setTime(val);
                        }else if(attrType.equals("cnfullname")){
                        	company.setCnfullname(val);
                        }else if(attrType.equals("enfullname")){
                        	company.setEnfullname(val);
                        }else if(attrType.equals("companycode")){
                        	company.setCompanycode(val);
                        }else if(attrType.equals("companytype")){
                        	company.setCompanytype(val);
                        }
                    }
                }
                companyList.add(company);
			}
		} catch (NamingException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
		}
		//排序
		for(int i=0;i<companyList.size();i++){
			for(int j=i+1;j<companyList.size();j++){
				int order1 = Integer.valueOf(companyList.get(i).getOrder());
				int order2 = Integer.valueOf(companyList.get(j).getOrder());
				if(order1>order2){
					CommonCompany temp = companyList.get(i);
					companyList.set(i, companyList.get(j));
					companyList.set(j, temp);
				}
			}
		}
		//根据公司节点等级排序
		for(int i=0;i<companyList.size();i++){
			for(int j=i+1;j<companyList.size();j++){
				int level1 = Integer.valueOf(companyList.get(i).getCompanylevel());
				int level2 = Integer.valueOf(companyList.get(j).getCompanylevel());
				if(level1>level2){
					CommonCompany temp = companyList.get(i);
					companyList.set(i, companyList.get(j));
					companyList.set(j, temp);
				}
			}
		}
    	return companyList;
    }
    
    /**
     * LDAP查询日志
     * @param scope
     * @param filter
     * @return
     */
    public List<CommonLog> searchLog(String scope, String filter) {
    	List<CommonLog> logList = new ArrayList<CommonLog>();
    	List<SearchResult> list = searchInformation(scope, filter);
    	try {
			for(SearchResult sr:list){
				CommonLog log = new CommonLog();
				log.setDn(sr.getName() + "," + root);
				Attributes at = sr.getAttributes();
                NamingEnumeration ane = at.getAll();
                while (ane.hasMore()) {
                    Attribute attr = (Attribute) ane.next();
                    String attrType = attr.getID();
                    NamingEnumeration values = attr.getAll();
                    //Vector vals = new Vector();
                    while (values.hasMore()) {
                    	Object oneVal = values.nextElement();
                    	String val = "";
                        if (oneVal instanceof String) {
                            val = (String) oneVal;
                        } else {
                            val = new String((byte[]) oneVal);
                        }
                        if(attrType.equals("logid")){
                        	log.setLogid(val);
                        }else if(attrType.equals("opname")){
                    	    log.setOpname(val);
                        }else if(attrType.equals("logloginname")){
                    	    log.setLogloginname(val);
                        }else if(attrType.equals("opertime")){
                    	    log.setOpertime(val);
                        }else if(attrType.equals("opertype")){
                    	    log.setOpertype(val);
                        }else if(attrType.equals("operobject")){
                    	    log.setOperobject(val);
                        }else if(attrType.equals("message")){
                    	    log.setMessage(val);
                        }
                    }
                }
                logList.add(log);
			}
		} catch (NamingException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
		}
    	return logList;
    }
    
    /**
     * LDAP修改对象的DN值
     * @param oldDN
     * @param newDN
     * @return
     */
    public boolean renameEntry(String oldDN, String newDN) {
        try {
        	ctx.rename(oldDN, newDN);
            return true;
        } catch (NamingException ne) {
            System.err.println("Error: " + ne.getMessage());
            return false;
        }
    }
    
    /**
     * 登录
     * @param userName
     * @param password
     * @return
     * 成功 0 
     * 没有该用户:1 密码错误:2 没有权限:3
     */
    public int login(String userName, String password) {
    	List<CommonOperator> list = searchOperator("", 
				"(&(objectclass=commonOperator)(loginname="+userName+")(status=1))");
    	if(list.size()>0){
    		CommonOperator operator = list.get(0);
    		if(operator.getUserPassword().equals(password)){
    			if(operator.getRoleid()!=null&&operator.getRoleid().size()>0){
    				return 0;
    			}else{
    				return 3;
    			}
    		}else{
    			return 2;
    		}
    	}
    	return 1;
    }
    
    public String getCompanyIds() {
    	StringBuffer sb = new StringBuffer();
    	List<CommonCompany> list = searchCompany("", "(objectclass=CommonCompany)");
    	for(CommonCompany company:list){
    		sb.append(company.getCompanyno()).append(",");
    	}
    	return sb.toString();
    }
    
    /**
     * 获取用户资料
     * @param userName
     * @return
     */
    public CommonOperator getOperator(String userName) {
    	List<CommonOperator> list = searchOperator("", "(&(objectclass=commonOperator)(loginname="+userName+"))");
    	if(list.size()>0){
    		return list.get(0);
    	}else{
    		return null;
    	}
    }
    
    /**
     * 获取用户资料
     * @param userId
     * @return
     */
    public CommonOperator getOperatorById(String userId) {
    	List<CommonOperator> list = searchOperator("", "(&(objectclass=commonOperator)(opid="+userId+"))");
    	if(list.size()>0){
    		return list.get(0);
    	}else{
    		return null;
    	}
    }
    
    /**
     * 获取用户所在的公司列表。因为一个用户可能对应多个机构，故为列表
     * @param userId
     * @return
     */
    public List<CommonCompany> getCompanys(String userId) {
    	List<CommonOperator> list = searchOperator("", "(&(objectclass=commonOperator)(opid="+userId+"))");
    	List<CommonCompany> companyList = searchCompany("", "(objectclass=commonCompany)");
    	List<CommonCompany> resultList = new ArrayList<CommonCompany>();
    	if(list.size()>0){
    		List<String> companyIds = list.get(0).getCompanynos();
    		for(int i=0;i<companyList.size();i++){
    			if(companyIds.contains(companyList.get(i).getCompanyno())){
    				resultList.add(companyList.get(i));
    			}
    		}
    		return resultList;
    	}else{
    		return null;
    	}
    }
    
    /**
     * 获取用户在子系统中的角色信息
     * @param userId
     * @param systemId
     * @return
     */
    public List<CommonRole> getRole(String userId, String systemId) {
    	List<CommonOperator> opList = searchOperator("", "(&(objectclass=commonOperator)(opid="+userId+"))");
    	List<String> roleIds = new ArrayList<String>();
    	if(opList.size()>0){
    		roleIds = opList.get(0).getRoleid();
    	}
    	List<CommonRole> roleList = searchRole("", "(&(objectclass=commonRole)(systemid="+systemId+"))");
    	if(roleList.size()==0){
    		return null;
    	}
    	List<CommonRole> roleResultList=new ArrayList<CommonRole>();
    	for(int i=0;i<roleList.size();i++){
    		if(roleIds!=null){
    			if(roleIds.contains(roleList.get(i).getRoleid())){
    				roleResultList.add(roleList.get(i));
    			}
    		}
    	}
    	return roleResultList;
    }
    
    /**
     * 获取用户在子系统中的模块信息列表
     * @param userId
     * @param systemId
     * @return
     */
    public List<CommonModule> getModules(String userId, String systemId) {
    	List<CommonRole> roles = getRole(userId, systemId);
    	List<CommonModule> moduleList = new ArrayList<CommonModule>();
    	List<String> modules =new ArrayList<String>();;
    	if(roles!=null){
    		for (CommonRole role : roles) {
    			modules.addAll(role.getModuleids());
			}
    		String filter = "(&(objectclass=commonModule)(|";
        	for(int i=0;i<modules.size();i++){
        		filter += "(moduleid="+modules.get(i)+")";
        	}
        	filter += "))";
        	moduleList = searchModule("", filter);
    	}else{
    		return moduleList;
    	}
    	
    	return moduleList;
    }
    
    /**
     * 获取数据能被用户在子系统中访问的公司(机构)列表
     * @param userId
     * @param systemId
     * @return
     */
    public List<CommonCompany> getAccessibleCompanys(String userId, String systemId) {
    	List<CommonCompany> companyList = new ArrayList<CommonCompany>();
    	List<CommonOperator> opList = searchOperator("", "(&(objectclass=commonOperator)(opid="+userId+"))");
    	List<String> roleIds = new ArrayList<String>();
    	List<String> roleCompanys = new ArrayList<String>();
    	int index = 0;
    	if(opList.size()>0){
    		roleIds = opList.get(0).getRoleid();
    		roleCompanys = opList.get(0).getRolecompanynos();
    	}
    	List<CommonRole> roleList = searchRole("", "(&(objectclass=commonRole)(systemid="+systemId+"))");
    	if(opList.size()==0 || roleCompanys.size()==0 || roleList.size()==0){
    		return companyList;
    	}
    	for(int i=0;i<roleList.size();i++){
    		if(roleIds.contains(roleList.get(i).getRoleid())){
    			index = i;
    			break;
    		}
    	}
    	String roleCompany = roleCompanys.get(index);
    	String[] companyNos = roleCompany.split(",");
    	String filter = "(&(objectclass=commonCompany)(|";
    	for(int i=1;i<companyNos.length;i++){
    		filter += "(companyno="+companyNos[i]+")";
    	}
    	filter += "))";
    	companyList = searchCompany("", filter);
    	return companyList;
    }
    
    /**
     * 根据机构ID得到它的分公司对象
     * @param orgId
     * @return
     */
    public CommonCompany getCompanyByOrgId(String orgId) {
    	CommonCompany result = null;
    	List<CommonCompany> commonCompanies = searchCompany("", "(&(objectclass=CommonCompany)(companyno="+orgId+"))");
    	if (commonCompanies == null || commonCompanies.isEmpty()) {
    		return null;
    	}
    	CommonCompany company = commonCompanies.get(0);
    	String dn = company.getDn();
    	String filter = "(&(objectclass=CommonCompany)(companyno=";
    	if(dn.indexOf("companyno="+SystemConst.HEADQUARTERS)!=-1){
    		//所属总部机构
    		filter += SystemConst.HEADQUARTERS;
    	}else if(dn.indexOf("companyno="+SystemConst.TOPCOMPANYNO)!=-1){
    		//所属分公司
    		String[] companynos = dn.split("companyno");
    		if(companynos.length>3){
    			String str = companynos[companynos.length-3];
    			String companyno = str.substring(1, str.length()-1);
    			filter += companyno;
    		}else{
    			filter += SystemConst.TOPCOMPANYNO;
    		}
    	}else{
    		return company;
    	}
    	filter += "))";
    	result = searchCompany("", filter).get(0);
    	return result;
    }
    
    /**
     * 获取分公司下属机构(超过第4层则获取分公司)
     * @param companyno
     * @return
     */
    public CommonCompany getCompanyByCompanyno(String companyno) {
    	CommonCompany result = null;
    	List<CommonCompany> commonCompanies = searchCompany("", "(&(objectclass=CommonCompany)(companyno="+companyno+"))");
    	if (commonCompanies == null || commonCompanies.isEmpty()) {
    		return null;
    	}
    	CommonCompany company = commonCompanies.get(0);
    	String dn = company.getDn();
    	String filter = "(&(objectclass=CommonCompany)(companyno=";
    	if(dn.indexOf("companyno="+SystemConst.HEADQUARTERS+",")!=-1){
    		//所属总部机构
    		filter += SystemConst.HEADQUARTERS;
    	}else if(dn.indexOf("companyno="+SystemConst.TOPCOMPANYNO+",")!=-1){
    		
    		Integer level = Integer.valueOf(company.getCompanylevel());
    		if(level > 4){
    			//所属分公司
        		String[] companynos = dn.split("companyno");
        		if(companynos.length>3){
        			String str = companynos[companynos.length-3];
        			String companyId = str.substring(1, str.length()-1);
        			filter += companyId;
        		}else{
        			filter += SystemConst.TOPCOMPANYNO;
        		}
    		}else{
        		return company;
    		}
    	}else{
    		return company;
    	}
    	filter += "))";
    	result = searchCompany("", filter).get(0);
    	return result;
    }
    
    /**
     * 根据用户ID得到它的机构ID
     * @param userId
     * @return
     */
    public String getOrgIdByUserId(String userId) {
    	CommonOperator user = null;
    	List<CommonOperator> list = searchOperator("", "(&(objectclass=commonOperator)(opid="+userId+"))");
    	if(list.size()>0){
    		user = list.get(0);
    	}else{
    		return null;
    	}
    	return user.getCompanynos().get(0);
    }
    
    /**
     * 根据公司ID得到它的下属机构ID列表
     * @param companyId
     * @return
     */
    public List<CommonCompany> getOrgIdsByCompanyId(String companyId) {
    	List<CommonCompany> result = new ArrayList<CommonCompany>();
    	List<CommonCompany> list = searchCompany("", "(objectclass=CommonCompany)");
    	for(CommonCompany company:list){
    		if(company.getCompanyno().equals(companyId)){
    			continue;
    		}
    		String dn = company.getDn();
    		if(dn.indexOf("companyno="+companyId)!=-1){
    			result.add(company);
    		}
    	}
    	return result;
    }
    
    /**
     * 根据机构ID得到它的直属机构ID列表
     * @param companyId
     * @return
     */
    public List<CommonCompany> getChildsByCompanyId(String companyId) {
    	List<CommonCompany> list = searchCompany("", "(&(objectclass=CommonCompany)(parentcompanyno="+companyId+"))");
    	return list;
    }
    
    /**
     * 根据部门ID得到它有哪些销售经理
     * @param orgId
     * @return
     */
    public List<CommonOperator> getManagersByOrgId(String orgId) {
    	List<CommonOperator> list = searchOperator("", "(&(objectclass=CommonOperator)(companynos="+orgId+")(roleid="+SystemConst.ROLEID_SALES_MANAGER+"))");
    	return list;
    }
    
    /**
     * 查看ldap中有没有这个名称的销售经理，且是在某个分公司中
     * @Title:isExistSaleManagers
     * @Description:TODO
     * @param companyno,opname
     * @return
     * @throws
     */
    public boolean isExistSaleManagers(String companyno, String opname) {
    	List<CommonCompany> companynos = getOrgIdsByCompanyId(companyno);
    	String filter = "(&(objectclass=CommonOperator)(opname="+opname+")(roleid="+SystemConst.ROLEID_SALES_MANAGER+")(|";
    	for(CommonCompany company:companynos){
    		filter += "(companynos="+company.getCompanyno()+")";
    	}
    	filter += "))";
    	List<CommonOperator> opList = searchOperator("", filter);
    	if(opList.size()>0){
    		return true;
    	}
    	return false;
    }
    
    /**
     * 根据销售经理名称查询销售经理列表
     * @param opname
     * @return
     */
    public List<CommonOperator> getManagersByName(String opname) {
    	List<CommonOperator> result = new ArrayList<CommonOperator>();
    	List<CommonOperator> list = searchOperator("", "(&(objectclass=CommonOperator)(roleid="+SystemConst.ROLEID_SALES_MANAGER+"))");
    	for(CommonOperator op:list){
    		String name = op.getOpname();
    		if(name.indexOf(opname) != -1){
    			result.add(op);
    		}
    	}
    	return result;
    }
    
    /**
     * 根据员工姓名和组织编号查询员工列表
     * @param opname
     * @param companyno
     * @return
     */
    public List<CommonOperator> getOperators(String opname, String companyno) {
    	List<CommonOperator> result = new ArrayList<CommonOperator>();
    	List<CommonCompany> companynos = getOrgIdsByCompanyId(companyno);
    	String filter = "(&(objectclass=CommonOperator)(|(companynos="+companyno+")";
    	for(CommonCompany company:companynos){
    		filter += "(companynos="+company.getCompanyno()+")";
    	}
    	filter += "))";
    	List<CommonOperator> list = searchOperator("", filter);
    	for(CommonOperator op:list){
    		String name = op.getOpname();
    		if(name.indexOf(opname) != -1){
    			result.add(op);
    		}
    	}
    	return result;
    }
    
    public Page<CommonOperator> getPageOperators(PageSelect<HashMap<String, Object>> pageSelect, String companyno){
    	Map map = pageSelect.getFilter();
    	String opname = (String)map.get("opname");
    	if(opname==null){
    		opname = "";
    	}
    	List<CommonOperator> list = getOperators(opname, companyno);
    	List<CommonOperator> userList = new ArrayList<CommonOperator>();
    	int total = list.size();
		int start = (pageSelect.getPageNo()-1)*pageSelect.getPageSize();
		int end = pageSelect.getPageNo()*pageSelect.getPageSize();
		int num = total<end?total:end;
		for(int i = start; i < num; i++){
			CommonOperator user = list.get(i);
			userList.add(user);
		}
		return PageUtil.getPage(total, pageSelect.getPageNo(), userList, pageSelect.getPageSize());
    }
    
    public Page<CommonOperator> getPageSaleManager(PageSelect<HashMap<String, Object>> pageSelect, String companyno){
    	Map map = pageSelect.getFilter();
    	String opname = (String)map.get("opname");
    	if(opname==null){
    		opname = "";
    	}
    	List<CommonOperator> list = new ArrayList<CommonOperator>();
    	List<CommonCompany> companynos = getOrgIdsByCompanyId(companyno);
    	String filter = "(&(objectclass=CommonOperator)(|";
    	for(CommonCompany company:companynos){
    		filter += "(companynos="+company.getCompanyno()+")";
    	}
    	filter += "))";
    	List<CommonOperator> opList = searchOperator("", filter);
    	for(CommonOperator oper:opList){
			String name = oper.getOpname();
    		if(name.indexOf(opname) != -1){
    			list.add(oper);
    		}
		}
    	List<CommonOperator> userList = new ArrayList<CommonOperator>();
    	int total = list.size();
		int start = (pageSelect.getPageNo()-1)*pageSelect.getPageSize();
		int end = pageSelect.getPageNo()*pageSelect.getPageSize();
		int num = total<end?total:end;
		for(int i = start; i < num; i++){
			CommonOperator user = list.get(i);
			userList.add(user);
		}
		return PageUtil.getPage(total, pageSelect.getPageNo(), userList, pageSelect.getPageSize());
    }
    
    /**
     * 根据部门ID得到它有哪些电工
     * @param orgId
     * @return
     */
    public List<CommonOperator> getElctricianByOrgId(String orgId) {
    	List<CommonOperator> list = searchOperator("", "(&(objectclass=CommonOperator)(companynos="+orgId+")(roleid="+SystemConst.ROLEID_ELCTRICIAN+"))");
    	return list;
    }
    
    /**
     * 根据机构ID得到机构对象
     * @param orgId
     * @return
     */
    public CommonCompany getCompanyById(String orgId) {
    	List<CommonCompany> list = searchCompany("", "(&(objectclass=CommonCompany)(companyno="+orgId+"))");
    	if(list.size()>0){
    		return list.get(0);
    	}
    	return null;
    }
    
    /**
     * 根据用户ID，得到他管理的仓库信息
     * @param userId
     * @return
     */
    public CommonCompany getWarehouseByUserId(String userId) {
    	CommonOperator operator = getOperatorById(userId);
    	List<String> list = operator.getRolecompanynos();
    	if(list != null && list.size()>0){
    		for(int i=0;i<list.size();i++){
    			CommonCompany company = getCompanyById(list.get(i));
    			if("4".equals(company.getCompanytype())){
    				return company;
    			}
    		}
    	}
    	return null;
    }
    
    /**
     * 根据分公司或者营业处id得到下面的所有仓库列表
     * @param orgId
     * @return
     */
    public List<CommonCompany> getWarehouseByOrgId(String orgId) {
    	List<CommonCompany> result = new ArrayList<CommonCompany>();
    	List<CommonCompany> companys = getOrgIdsByCompanyId(orgId);
    	for(CommonCompany company:companys){
    		if("4".equals(company.getCompanytype())){
    			result.add(company);
    		}
    	}
    	return result;
    }
    /**
     * 根据分公司或者营业处id和用户id得到下面的所有仓库列表
     * @param orgId，userId
     * @return
     */
    public List<CommonCompany> getWarehouseByOrgId(String orgId, String userId) {
    	CommonOperator operator = getOperatorById(userId);
    	if(operator==null){
    		return new ArrayList<CommonCompany>();
    	}
    	List<String> rolecompanynos = operator.getRolecompanynos();
    	if(rolecompanynos==null||rolecompanynos.size()==0){
    		return new ArrayList<CommonCompany>();
    	}
    	List<CommonCompany> result = new ArrayList<CommonCompany>();
    	List<CommonCompany> companys = getOrgIdsByCompanyId(orgId);
    	for(CommonCompany company:companys){
    		//如果数据权限包含这个组织并且这个组织是仓库
    		if("4".equals(company.getCompanytype()) && rolecompanynos.contains(company.getCompanyno())){
    			result.add(company);
    		}
    	}
    	return result;
    }
    
    /**
     * 根据分公司id得到下面的所有营业处列表
     * @param orgId
     * @return
     */
    public List<CommonCompany> getSalesOfficeByOrgId(String orgId, String companyname) {
    	if(companyname==null){
    		companyname = "";
    	}
    	List<CommonCompany> result = new ArrayList<CommonCompany>();
    	List<CommonCompany> companys = getOrgIdsByCompanyId(orgId);
    	for(CommonCompany company:companys){
    		if("2".equals(company.getCompanytype()) && company.getCompanyname().indexOf(companyname)!=-1){
    			result.add(company);
    		}
    	}
    	return result;
    }
    
    /**
     * 查询05年前、后的公司，isBefore=true表示是05年前的公司
     * @param isBefore
     * @return
     */
    public List<CommonCompany> getCompamysBeforeOrAfter(boolean isBefore) {
    	String filter = "(&(objectclass=CommonCompany)(parentcompanyno="+SystemConst.TOPCOMPANYNO+"))";
    	List<CommonCompany> list = searchCompany("", filter);
    	List<CommonCompany> beforeList = new ArrayList<CommonCompany>();
    	List<CommonCompany> afterList = new ArrayList<CommonCompany>();
    	for(CommonCompany company:list){
    		String time = company.getTime();
    		if(Util.isBlank(time)){
    			beforeList.add(company);
    		}else{
    			int year = Integer.valueOf(time.substring(0, 4));
    			if(year<2005){
    				beforeList.add(company);
    			}else{
    				afterList.add(company);
    			}
    		}
    	}
    	if(isBefore){
    		return beforeList;
    	}else{
    		return afterList;
    	}
    }
    
    /**
     * @Title:getUserCompanyLevel
     * @Description:判断用户是属于分公司还是总部
     * @param companyno
     * @return 0：总部，1：分公司
     * @throws
     */
    public int getUserCompanyLevel(String companyno){
    	CommonCompany company = getCompanyById(companyno);
		String dn = company.getDn();
		int result = 0;
		if(dn.indexOf("companyno="+SystemConst.HEADQUARTERS+",")!=-1){
    		//所属总部机构
			result = 0;
    	}else if(dn.indexOf("companyno="+SystemConst.TOPCOMPANYNO+",")!=-1){
    		//所属分公司
    		if("2".equals(company.getCompanytype())||"4".equals(company.getCompanytype())){//type=2代表是营业处,type=4代表仓库
    			result=2;
    		}else{
    			result=1;
    		}
    	}
		return result;
    }

}