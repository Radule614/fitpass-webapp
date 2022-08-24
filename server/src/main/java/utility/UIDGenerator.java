package utility;

import java.util.UUID;

public class UIDGenerator {

	public static String generate() {
		UUID randomUUID = UUID.randomUUID();
		return randomUUID.toString().replaceAll("-", "");
	}
}
