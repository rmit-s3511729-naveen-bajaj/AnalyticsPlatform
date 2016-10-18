import com.mysema.query.sql.*;
import com.mysema.query.types.Expression;

public class testQuilderTest {
	
	public static void main(String[] args) {
		
		SQLTemplates  temp = new HSQLDBTemplates();
		SQLQuery q = new SQLQuery(temp);
		
		q.from();
		
		
	}

}
