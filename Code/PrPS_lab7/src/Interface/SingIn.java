package Interface;

import java.sql.SQLException;

/**
 * @author m s i
 * @version 1.0
 * @created 25-май-2016 18:51:40
 */
public interface SingIn {

	public void authorization() throws SQLException;

	public String getRole();
	public String getLogin();

	public boolean cheakPassword();

}