package domain;

import java.io.File;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

/*
 * Layer which converts VL records to DB 
 */

/*
 * Neo4J Based implementation
 */
@XmlRootElement
@XmlSeeAlso(VirtualLink.class)
public class VirtualLinkDb {
	GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
	GraphDatabaseService dbService = dbFactory.newEmbeddedDatabaseBuilder(new File("data/neo4j.db")).newGraphDatabase();
	
	public void add(VirtualLink vl) {
		try (Transaction tx = dbService.beginTx())	//create Neo4j transaction
		{
			Node node1 = dbService.createNode(Label.label("vldb"));	//create node in database
			node1.setProperty("Id", vl.getId());	// Sets Id
			node1.setProperty("ip_profile", vl.getIp_profile());
			node1.setProperty("SubnetAssigned", vl.getSubnet_assigned());
			tx.success();	//commit transaction
		}
	}
	
	public void remove(VirtualLink vl) {
		try (Transaction tx = dbService.beginTx()) {
			Node node = dbService.findNode(Label.label("vldb"), "Id", vl.getId());
			node.delete();
			tx.success();
		}
		
	}

	public VirtualLink find(int id) {
		VirtualLink vl = new VirtualLink();
		try (Transaction tx = dbService.beginTx()) {
			Node node = dbService.findNode(Label.label("vldb"), "Id", id);
			vl.setId((int)node.getProperty("Id")) ;
			vl.setIp_profile((String)node.getProperty("ip_profile"));
			vl.setSubnet_assigned((String)node.getProperty("SubnetAssigned"));
			tx.success();
		}
		return vl;
	}
}
