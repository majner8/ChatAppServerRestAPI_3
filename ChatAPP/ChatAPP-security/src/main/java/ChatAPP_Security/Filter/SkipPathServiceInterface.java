package ChatAPP_Security.Filter;

public interface SkipPathServiceInterface {

	public boolean canSkipDeviceIDFilter(String path);
	
	public boolean canSkipUserAuthorizationFilterPath(String path);
}
