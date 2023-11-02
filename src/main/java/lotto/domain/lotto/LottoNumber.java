package lotto.domain.lotto;

import lotto.domain.lotto.exception.InvalidLottoNumberException;

/**
 * 6자리의 로또 번호 중, 각 자리를 나타내는 LottoNumber는 하나의 `int` 필드를 갖는 Wrapper class 입니다. `int`로 생성 가능하며, 생성 시 검증하여 유효성 보장이 가능합니다.
 */
public record LottoNumber(int value) {

    /**
     * 최소 로또 번호는 1입니다.
     */
    private static final int MIN_LOTTO_NUMBER = 1;
    /**
     * 최대 로또 번호는 45입니다.
     */
    private static final int MAX_LOTTO_NUMBER = 45;

    /**
     * @apiNote LottoNumber 생성 시, <h3 color="#bf0f4d">value는 반드시 1 이상 45 이하의 범위에 속해야 합니다.</h3>
     */
    public LottoNumber {
        validateLottoNumber(value);
    }

    /**
     * 각 자리의 로또 번호를 생성할 때, 1~45 사이의 숫자인지 검증합니다. 범위를 벗어난다면 `InvalidLottoNumberException` 예외가 발생합니다.
     */
    private static void validateLottoNumber(final int lottoNumber) {
        if (lottoNumber < MIN_LOTTO_NUMBER || lottoNumber > MAX_LOTTO_NUMBER) {
            throw new InvalidLottoNumberException();
        }
    }
}