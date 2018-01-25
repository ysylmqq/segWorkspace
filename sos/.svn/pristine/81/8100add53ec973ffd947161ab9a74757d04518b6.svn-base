package ldap.oper;

public class OpenLdapManager {
	
	public OpenLdapManager() {
	}

	private static OpenLdap ldap = null;
	
	public static synchronized OpenLdap getInstance(){
		if(ldap == null){
			ldap = new OpenLdap();
		}
		return ldap;
	}
    
}