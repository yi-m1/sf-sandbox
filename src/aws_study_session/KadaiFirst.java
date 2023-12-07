package aws_study_session;

import java.util.ArrayList;
import java.util.List;

/**
 * AWS勉強会活動の<br>
 * 課題1
 */
public class KadaiFirst {

	public static void main(String[] args) {
		KadaiFirst kadaifirst = new KadaiFirst();
		kadaifirst.execute();
	}

	public List<Integer> execute() {
		List<Integer> list = new ArrayList<>();
		int i = 0;

		// 2から100まで繰り返し処理を行い、素数判定を実施
		for (int n = 2; n <= 100; n++) {
			// 自身の数以外(自身より小さい自然数)で割り切れれば素数ではないので、breakでループ終了
			for (i = 2; i < n; i++) {
				if (n % i == 0) {
					break;
				}
			}
			// どの数でも割り切れることなく、自身の数まできたときは素数
			if (n == i) {
				//TODO System.out.println → ログ args4j 外部ライブラリを使用できると良い
				System.out.println("素数: " + n);
				list.add(n);
			}
		}
		return list;
	}

}
