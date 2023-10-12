package aws_study_session_test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import aws_study_session.KadaiFirst;

/**
 * AWS勉強会活動 課題1の素数を確認するテストクラス
 */
public class KadaiFirstPrimeNumberCheckTest {

	KadaiFirst kaf;

	@Before
	public void setUp() {
		kaf = new KadaiFirst();
	}

	@After
	public void fin() {
		// 何もしない
	}

	@Test
	public void primeNumberAccurateTest() {
		List<Integer> resultlist = new ArrayList<>();
		List<Integer> expectlist =
				Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97);

		resultlist = kaf.execute();
		assertThat(resultlist, is(expectlist));
	}

}
