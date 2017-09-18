package domain;

/*
 * CRUD API for Network Connection Manager
 */

public class NetConnectionManager {
	private static NetConnectionDb ncdb = new NetConnectionDb();
	static {// Add some dummy netconnection for testing
		ncdb.init();
		ncdb.add(new NetConnection("nc1", "vnfc1", "vnfc2", "cp1", "cp2", "vl1"));
		ncdb.close();
	}
	
	/*
	 * Get one specified NC
	 */
	public static NetConnection find(String name){
		ncdb.init();
		NetConnection nc = ncdb.find(name);
		ncdb.close();
		return((nc == null)?null:nc);
	}
	
	/*
	 * POST - Add a NC
	 */
	public static void add(NetConnection nc) {
		ncdb.init();
		ncdb.add(nc);
		ncdb.close();
	}

	/*
	 * @DELETE - Delete a NC 
	 */
	public static void delete(String name) {
		ncdb.init();
		NetConnection nc = ncdb.find(name);
		ncdb.remove(nc);
		ncdb.close();
	}
	
	/*
	 * @PUT - Update a NC
	 */
	public static NetConnection update(NetConnection nc) {
		ncdb.init();
		NetConnection nc_origin = ncdb.find(nc.getName());
		if (nc_origin == null) {
			ncdb.close();
			return null;
		}
		/*
		 * Delete the old NC
		 */
		delete(nc_origin.getName());
		add(nc);
		ncdb.close();
		return nc;
	}
	
	/*
	 * GET VL from vnfc
	 */
	public static VirtualLink find_vl_from_vnfc(String vnfc_name) {
		ncdb.init();
		VirtualLink vl = ncdb.find_vl_from_vnfc(vnfc_name);
		ncdb.close();
		return vl;
	
	}

}
