package com.filocha;

import java.util.List;

public interface Branch {
	List<Branch> getChildBranches();

	List<Leaf> getChildLeafs();

}
