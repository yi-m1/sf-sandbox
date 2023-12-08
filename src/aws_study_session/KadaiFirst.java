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

	/**
	 * 2から100までの中にある素数取得メソッド
	 * @return 素数のリスト
	 */
	public List<Integer> execute() {
		List<Integer> primeNumberList = new ArrayList<>();
		int divideNumber = 0;

		// 2から100まで繰り返し処理を行い、素数判定を実施
		for (int candidatePrimeNumber = 2; candidatePrimeNumber <= 100; candidatePrimeNumber++) {
			// 自身の数以外(自身より小さい自然数)で割り切れれば素数ではないので、breakでループ終了
			for (divideNumber = 2; divideNumber < candidatePrimeNumber; divideNumber++) {
				if (candidatePrimeNumber % divideNumber == 0) {
					break;
				}
			}
			// どの数でも割り切れることなく、自身の数まできたときは素数
			if (candidatePrimeNumber == divideNumber) {
				//TODO System.out.println → ログ args4j 外部ライブラリを使用できると良い
				System.out.println("素数: " + candidatePrimeNumber);
				primeNumberList.add(candidatePrimeNumber);
			}
		}
		return primeNumberList;
	}

}
