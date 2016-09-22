package com.filocha;

import java.util.List;

public interface Branch {
	//zmien na iterator i operuj na kopii listy
	List<Branch> getChildBranches();

	List<Leaf> getChildLeafs();

}
