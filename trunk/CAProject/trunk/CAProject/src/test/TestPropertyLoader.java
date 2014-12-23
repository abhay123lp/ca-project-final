package test;

import static org.junit.Assert.*;

import org.junit.Test;

import util.PropertyLoader;

public class TestPropertyLoader {

	@Test
	public void test() {
		String client_upload_folder = "/root/Desktop/clientupload_folder/";
		assertEquals(client_upload_folder,PropertyLoader.loadProperty("client_upload_folder"));
	}

}
