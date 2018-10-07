namespace php tp.bigdata.schema
namespace java tp.bigdata.schema

# Nodes
union User {
	1: string username;
}

union Page {
	1: string url;
}

union Product {
	1: string barcode;
}

# Edge
struct FactsEdge {
	1: required User user;
	2: required Page page;
	3: required Product product;
	4: i32 quantity;
	5: string date;
}

# Properties
# Page properties
union PagePropertyValue {
	1: string urlType;
}

struct PageProperty {
	1: required Page url;
	2: required PagePropertyValue property;
}

# User properties
union UserPropertyValue {
	1: string name;
	2: string email;
	3: string phone;
	4: string birdthday;
}

struct UserProperty {
	1: required User username;
	2: required UserPropertyValue property;
}

# Product properties
union ProductPropertyValue {
	1: string description;
	2: double stock;
	3: double salePrice;
}

struct ProductProperty {
	1: required Product product;
	2: required ProductPropertyValue property;
}


# Tying everything together
union DataUnit {
	1: UserProperty userProperty;
	2: PageProperty pageProperty;
	3: ProductProperty productProperty;
	4: FactsEdge factsEdge;
}

struct Pedigree {
	1: required i64 trueAsOfSecs;
}

struct Data {
	1: required Pedigree pedigree;
	2: required DataUnit dataUnit;
}