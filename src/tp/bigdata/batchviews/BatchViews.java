package tp.bigdata.batchviews;

import cascading.flow.FlowProcess;
import cascading.operation.FunctionCall;
import cascading.tuple.Fields;
import cascading.tuple.Tuple;
import cascalog.CascalogFunction;
import com.backtype.cascading.tap.PailTap;
import com.twitter.maple.hbase.HBaseScheme;
import com.twitter.maple.hbase.HBaseTap;
import com.twitter.maple.tap.StdoutTap;

import jcascalog.Api;
import jcascalog.Option;
import jcascalog.Subquery;
import jcascalog.op.Sum;
import jcascalog.op.Multiply;
import tp.bigdata.schema.Data;
import tp.bigdata.schema.FactsEdge;

import java.io.IOException;

import static tp.bigdata.batchlayer.BatchWorkflow.MASTER_ROOT;
import static tp.bigdata.batchlayer.BatchWorkflow.splitDataTap;

public class BatchViews {
	public static void batchViews() throws IOException {
		PailTap src = splitDataTap(MASTER_ROOT);
		
		Subquery reduced = new Subquery("?username", "?quantity_purchased", "?total")
				.predicate(src, "_", "?data")
				.predicate(new ExtractFactsFields(), "?data").out("?username", "?url", "?barcode", "?quantity", "?price", "?date", "?timestamp")
				.predicate(Option.DISTINCT, "?username").out("?username")
				.predicate(new Sum(), "?quantity").out("?quantity_purchased")
				.predicate(new Multiply(), "?quantity", "?price").out("?total")
				.predicate(Option.SORT, "?username");
		
		String tableName = "factsEdge";
//		String familyName = "purchase";
		Fields keyFields = new Fields("?username");
		Fields valueFields = new Fields("?quantity_purchased", "?total");
		
		HBaseScheme hBaseScheme = new HBaseScheme(keyFields, valueFields);
		HBaseTap hBaseTap = new HBaseTap(tableName, hBaseScheme);
		
		// para mostrar la consulta en pantalla
		Api.execute(new StdoutTap(), reduced);
		
		Api.execute(hBaseTap, reduced);
		
	}
	
	@SuppressWarnings("serial")
	public static class ExtractFactsFields extends CascalogFunction {
        
		@SuppressWarnings("rawtypes")
		public void operate(FlowProcess process, FunctionCall call) {
            Data data = (Data) call.getArguments().getObject(0);
            FactsEdge factsEdge = data.get_dataUnit()
                    .get_factsEdge();

            System.out.println("username: "+factsEdge.get_user().get_username());
            System.out.println("page url: "+ factsEdge.get_page().get_url());
            System.out.println("product barcode: "+ factsEdge.get_product().get_barcode());
            System.out.println("quantity: "+ factsEdge.get_quantity());
            System.out.println("price: " + factsEdge.get_price());
            System.out.println("date: " + factsEdge.get_date());
            call.getOutputCollector().add(new Tuple(
                    factsEdge.get_user().get_username(),
                    factsEdge.get_page().get_url(),
                    factsEdge.get_product().get_barcode(),
                    factsEdge.get_quantity(),
                    factsEdge.get_price(),
                    factsEdge.get_date(),
                    data.get_pedigree().get_trueAsOfSecs()
            ));
        }
	}
}
