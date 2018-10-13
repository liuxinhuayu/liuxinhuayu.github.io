public class Hello{
	public static void main(String args[]){
		int max = Integer.MAX_VALUE;
		int min = Integer.MIN_VALUE;
		System.out.println(max);    // 2147483647
		System.out.println(min);    // -2147483648
		// int型变量 + long常量 = long类型
		System.out.println(max + 1L);  //  2147483648
		// 强制将int类型转变为long类型
		System.out.println((long)min - 1);  // -2147483649

	}
}

