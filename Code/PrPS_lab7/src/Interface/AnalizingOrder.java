package Interface;

import java.sql.SQLException;

/**
 * @author m s i
 * @version 1.0
 * @created 25-май-2016 18:58:23
 */
public interface AnalizingOrder {

	public int getChangeOfPrise();

	public void getMostPopular() throws SQLException;

	public void getStreetList() throws SQLException;

}