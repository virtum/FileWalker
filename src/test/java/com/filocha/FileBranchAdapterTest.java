package com.filocha;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Test;

import com.filocha.dirWatcher.Branch;
import com.filocha.dirWatcher.FileBranchAdapter;
import com.filocha.dirWatcher.Leaf;

public class FileBranchAdapterTest {

	@Test
	public void shouldFindSubBranches() throws IOException {

		String home = System.getProperty("user.home");
		String randomName = UUID.randomUUID().toString().substring(28);
		String root = home + "\\" + randomName + "\\";

		File rootFile = new File(root);
		rootFile.mkdirs();

		String subBranch1 = UUID.randomUUID().toString().substring(28);
		new File(root + subBranch1).mkdirs();

		String subBranch2 = UUID.randomUUID().toString().substring(28);
		new File(root + subBranch2).mkdirs();

		new File(root + "1.txt").createNewFile();
		new File(root + "2.txt").createNewFile();
		new File(root + "3.txt").createNewFile();

		FileBranchAdapter adapter = new FileBranchAdapter(rootFile);
		List<Branch> branches = adapter.getChildBranches();
		List<Leaf> leafs = adapter.getChildLeafs();

		assertThat(leafs.get(0).getName(), equalTo("1.txt"));
		assertThat(branches.size(), equalTo(2));
		assertThat(leafs.size(), equalTo(5));

		FileUtils.deleteDirectory(rootFile);
	}

}
