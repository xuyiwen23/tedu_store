import java.security.NoSuchAlgorithmException;

import org.springframework.util.DigestUtils;

public class TestMessageDigest {
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		String str = "a";
		
		String md = DigestUtils.md5DigestAsHex(str.getBytes()).toUpperCase();
		System.out.println(md);
		
//		for (int i = 0; i < 16; i++) {
//			md = DigestUtils.md5DigestAsHex(md.getBytes()).toUpperCase();
//			System.out.println(md);
//		}
		
	}

}




