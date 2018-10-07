package tp.bigdata.tap;

import com.backtype.cascading.tap.PailTap;
//import com.backtype.cascading.tap.PailTap.PailTapOptions;
import com.backtype.hadoop.pail.PailSpec;
import com.backtype.hadoop.pail.PailStructure;
import java.util.ArrayList;
import java.util.List;
import tp.bigdata.schema.DataUnit;
import org.apache.thrift.TFieldIdEnum;

@SuppressWarnings({"serial","rawtypes", "unchecked"})
public class SplitDataPailTap extends PailTap {
	public static class SplitDataPailTapOptions {
        public PailSpec spec = null;
        public String fieldName = "data";

        public SplitDataPailTapOptions() {

        }

        public SplitDataPailTapOptions(PailSpec spec, String fieldName) {
            this.spec = spec;
            this.fieldName = fieldName;
        }
    }

    public SplitDataPailTap(String root, SplitDataPailTapOptions options, DataUnit._Fields[] attrs) {
        super(root, new PailTapOptions(PailTap.makeSpec(options.spec, getSpecificStructure()), options.fieldName, toAttrs(attrs), null));
    }

    public SplitDataPailTap(String root, SplitDataPailTapOptions options, int[] attrs) {
        super(root, new PailTapOptions(PailTap.makeSpec(options.spec, getSpecificStructure()), options.fieldName, toAttrs(attrs), null));
    }

    public SplitDataPailTap(String root, SplitDataPailTapOptions options, List<TFieldIdEnum>[] attrs) {
        super(root, new PailTapOptions(PailTap.makeSpec(options.spec, getSpecificStructure()), options.fieldName, toAttrs(attrs), null));
    }

    public SplitDataPailTap(String root, SplitDataPailTapOptions options) {
        this(root, options, (DataUnit._Fields[]) null);
    }

    public SplitDataPailTap(String root) {
        this(root, new SplitDataPailTapOptions());
    }

    public SplitDataPailTap(String root, DataUnit._Fields[] attrs) {
        this(root, new SplitDataPailTapOptions(), attrs);
    }

    public SplitDataPailTap(String root, int[] attrs) {
        this(root, new SplitDataPailTapOptions(), attrs);
    }

    public SplitDataPailTap(String root, List<TFieldIdEnum>[] attrs) {
        this(root, new SplitDataPailTapOptions(), attrs);
    }

	protected static PailStructure getSpecificStructure() {
        return new SplitDataPailStructure();
    }

    protected static List<String>[] toAttrs(DataUnit._Fields[] spec) {
        if(spec==null) return null;
		List<String>[] ret = new List[spec.length];
        for(int i=0; i<spec.length; i++) {
            List<String> a = new ArrayList<String>();
            a.add("" + spec[i].getThriftFieldId());
            ret[i] = a;
        }
        return ret;
    }

    protected static List<String>[] toAttrs(int[] spec) {
        if(spec==null) return null;
        List<String>[] ret = new List[spec.length];
        for(int i=0; i<spec.length; i++) {
            List<String> a = new ArrayList<String>();
            a.add("" + spec[i]);
            ret[i] = a;
        }
        return ret;
    }

    protected static List<String>[] toAttrs(List<TFieldIdEnum>[] spec) {
        if(spec==null) return null;
        List<String>[] ret = new List[spec.length];
        for(int i=0; i<spec.length; i++) {
            List<String> a = new ArrayList<String>();
            List<TFieldIdEnum> conv = spec[i];
            for(TFieldIdEnum j: conv) {
                a.add("" + j.getThriftFieldId());
            }
            ret[i] = a;
        }
        return ret;
    }
}
