package aws_study_session;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * AWS勉強会活動の<br>
 * 課題2
 */
public class KadaiSecond {

	public static void main(String[] args) {
		KadaiSecond kadaisecond = new KadaiSecond();
		Scanner sc = new Scanner(System.in);
		System.out.print("求める素数の数を入力してください > ");
		int inputTarget = sc.nextInt();
		List<Integer> primeNumbers = kadaisecond.getPrimeNumber(inputTarget);
		System.out.println("素数: " + primeNumbers);
		//TODO System.out.println → ログ log4j 外部ライブラリを使用できると良い
		//TODO 出力は別だしがよい(5大装置それぞれでメソッドにしたほうが)
		sc.close();
	}

	//TODO 後で、List を Set(順序修正してくれるクラスを使用) にするかな

	/**
	 * {@link #inputTarget} で指定された数の素数を求めて返却する
	 * @param inputTarget 求める素数の数
	 * @return 素数のリスト
	 */
	public List<Integer> getPrimeNumber(int inputTarget) {
		List<Integer> primeNumberList = new ArrayList<>();
		int candidatePrimeNumber = 2;
		int divideNumber = 0;
		int calcCount = 0; //TODO 計算量出力用 一時的

		// 2から標準入力から受け取った求める素数の数までを繰り返し処理
		while (primeNumberList.size() != inputTarget) {
			//TODO 実際の計算処理部分も別メソッドにしても
			//TODO 素数判定部分 3から開始で+2でも or 偶数は飛ばす
			// 素数判定
			for (divideNumber = 2; divideNumber < candidatePrimeNumber; divideNumber++) {
				if (candidatePrimeNumber % divideNumber == 0) {
					// 自身の数以外(自身より小さい自然数)で割り切れると素数ではないので、ループ終了
					break;
				}
				calcCount++;
			}
			if (candidatePrimeNumber == divideNumber) {
				// どの数でも割り切れることなく、自身の数まできたときは素数
				primeNumberList.add(candidatePrimeNumber);
			}
			candidatePrimeNumber++;
		}
		System.out.println("計算量: " + calcCount + " 回"); //TODO 一時的
		return primeNumberList;
	}

}
