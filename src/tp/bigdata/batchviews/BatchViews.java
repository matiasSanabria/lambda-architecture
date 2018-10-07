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
				.predicate(new ExtractFactsFields(), "?data").out("?username", "?url", "?barcode", "?quantity", "?salePrice", "?date", "?timestamp")
				.predicate(Option.DISTINCT, "?username").out("?username")
				.predicate(new Sum(), "?quantity").out("?quantity_purchased")
				.predicate(new Multiply(), "?quantity", "?salePrice").out("?total")
				.predicate(Option.SORT, "?username");
		
		String tableName = "product_purchase";
		String familyName = "purchase";
		Fields keyFields = new Fields("?username");
		Fields valueFields = new Fields("?username", "?quantity");
		
		HBaseScheme hBaseScheme = new HBaseScheme(keyFields, familyName, valueFields);
		HBaseTap hBaseTap = new HBaseTap(tableName, hBaseScheme);
		
		Api.execute(hBaseTap, reduced);
		
		// para mostrar la consulta en pantalla
		Api.execute(new StdoutTap(), reduced);
		
	}
	
	@SuppressWarnings("serial")
	public static class ExtractFactsFields extends CascalogFunction {
        
		@SuppressWarnings("rawtypes")
		public void operate(FlowProcess process, FunctionCall call) {
            Data data = (Data) call.getArguments().getObject(0);
            FactsEdge factsEdge = data.get_dataUnit()
                    .get_factsEdge();

            call.getOutputCollector().add(new Tuple(
                    factsEdge.get_user().get_username(),
                    factsEdge.get_page().get_url(),
                    factsEdge.get_product().get_barcode(),
                    factsEdge.get_quantity(),
                    factsEdge.get_product().get_price(),
                    factsEdge.get_date(),
                    data.get_pedigree().get_trueAsOfSecs()
            ));
        }
	}
}
