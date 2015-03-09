import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

public class Material {
	static String ABAP_AS_WITH_POOL = "ABAP_AS_WITH_POOL";
	
	
	static void simpleCall() throws JCoException {
		JCoDestination destination = JCoDestinationManager.getDestination(ABAP_AS_WITH_POOL);
		JCoFunction function = destination.getRepository().getFunction("Z_RFC_MATERIAL_GETITEMS");
		if (function == null)
			throw new RuntimeException("Z_RFC_MATERIAL_GETITEMS not found in SAP.");
		
		function.getImportParameterList().setValue("CHANGE_BEGIN_DATE", "20090330");
		function.getImportParameterList().setValue("CHANGE_END_DATE", "20090330");
		function.getImportParameterList().setValue("PLANT", "2006");
		
		try {
			function.execute(destination);
		} catch (AbapException e) {
			System.out.println(e.toString());
			return;
		}
		
		JCoTable materials = function.getTableParameterList().getTable("MATERIAL_DATA");
		for (int i = 0; i < materials.getNumRows(); i++) {
			materials.setRow(i);
			System.out.println("Material: " + materials.getString("MATERIAL"));
			System.out.println("Plant: " + materials.getString("PLANT"));
			System.out.println("Description: " + materials.getString("MATL_DESC"));
			System.out.println();
		}
		System.out.println("Z_RFC_MATERIAL_GETITEMS finished.");		
	}
	
	public static void main(String[] args) throws JCoException  {
		simpleCall();
	}
}

