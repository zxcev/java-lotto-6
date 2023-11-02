package lotto.domain.lotto;

import java.util.List;

/**
 * 사용자가 입력한 금액으로 구매한 로또 용지를 나타내는 클래스입니다. 6자리의 로또 번호를 가지고 있습니다.
 */
public final class Lotto {

    /**
     * 각 로또 용지가 갖는 로또 번호의 개수입니다. 요구 사항에 따라 6자리 고정 크기로 설정 되었습니다.
     */
    private static final int LOTTO_NUMBERS_LENGTH = 6;

    /**
     * `Lotto` 객체의 유일한 필드이자 6자리의 로또 번호를 나타냅니다.
     */
    private final List<LottoNumber> numbers;

    /**
     * @apiNote Lotto 생성 시, <h3 color="#bf0f4d">numbers의 요소는 반드시 6개여야 합니다.</h3>
     */
    public Lotto(final List<Integer> numbers) {
        validateLotto(numbers);
        this.numbers = intoLottoNumbers(numbers);
    }

    /**
     * 편의를 위해 Lotto 생성 시, 로또 번호를 List<Integer> 타입으로 받습니다. 이 메소드 내에서 List<Integer>가 List<LottoNumber>로 변환되며, LottoNumber에
     * 대한 검증은 각 자리가 LottoNumber로 변환되는 과정에서 자동으로 이루어집니다.
     */
    private static List<LottoNumber> intoLottoNumbers(final List<Integer> numbers) {
        return numbers.stream()
                .map(LottoNumber::new)
                .toList();
    }

    /**
     * 입력된 로또 번호가 6자리인지 검증합니다. 6자리가 아닐 경우 예외가 발생합니다.
     */
    private void validateLotto(final List<Integer> numbers) {
        if (numbers.size() != LOTTO_NUMBERS_LENGTH) {
            throw new IllegalArgumentException();
        }
    }

}